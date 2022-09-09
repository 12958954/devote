package com.example.cs398project

class Model: Subject {
    override val observers = mutableListOf<Observer>()
    private val database = Mysql()

    val notes = mutableListOf<Note>()
    lateinit var noteCurrent: Note
    val folders = HashMap<Int, Folder>()

    var searchText = ""
        set(value) {
            field = value
            notifyObservers()
        }

    init {
        init()
    }
    fun init() {
        notes.clear()
        folders.clear()

        // Initialize folders table with root folder if empty
        val foldersData = database.query("SELECT id, name FROM folders")

        while (foldersData.next()) {
            val id = foldersData.getInt(1)
            val name = foldersData.getString(2)
            val folderNew = Folder(id,name)
            folders[id] = (folderNew)
        }

        val notesData = database.query("SELECT id, title, content, folder FROM notes")
        while (notesData.next()) {
            val noteNew = Note(notesData.getInt(1))
            noteNew.title = notesData.getString(2)
            noteNew.content = notesData.getString(3)
            noteNew.folder = notesData.getInt(4)

            notes.add(noteNew)
            folders[noteNew.folder]?.notes?.add(noteNew)
        }
        noteCurrent = Note(1,1)
        if (folders.isEmpty()) {
            println("Should not happen")
        } else {
            for (f in folders.values) {
                if (f.notes.size > 0) {
                    noteCurrent = f.notes.last()
                }
            }
        }
        notifyObservers()
    }

    fun createNewNote(folder: Int = 1) {
        val candidateId = database.query(
                "SELECT n1.id + 1 " +
                        "FROM notes n1 LEFT JOIN notes n2 ON n2.id = n1.id + 1 " +
                        "WHERE n2.id IS NULL " +
                        "ORDER BY n1.id " +
                        "LIMIT 1"
        )

        val lowestId = database.query("SELECT MIN(id) FROM notes")

        var id = 1
        if (candidateId.next()) {
            lowestId.next()
            if (lowestId.getInt(1) == 1) {
                id = candidateId.getInt(1)
            }
        }
        if (folder == 1) {
            noteCurrent = Note(id, folder)
        } else {
            noteCurrent = Note(id, noteCurrent.folder)
        }
        folders[noteCurrent.folder]?.notes?.add(noteCurrent)
        notes.add(noteCurrent)

        saveNote()
    }

    fun renameFolder(id: Int, name: String) {
        folders[id]?.name = name
        database.stmt("UPDATE folders SET name = '$name' WHERE id = $id")
        notifyObservers()
    }

    fun createNewFolder() {
        val candidateId = database.query(
                "SELECT n1.id + 1 " +
                        "FROM folders n1 LEFT JOIN folders n2 ON n2.id = n1.id + 1 " +
                        "WHERE n2.id IS NULL " +
                        "ORDER BY n1.id " +
                        "LIMIT 1"
        )

        val lowestId = database.query("SELECT MIN(id) FROM folders")

        var id = 1
        if (candidateId.next()) {
            lowestId.next()
            if (lowestId.getInt(1) == 1) {
                id = candidateId.getInt(1)
            }
        }

        val name = "New_Folder_$id"
        folders[id] = Folder(id, name)
        database.stmt("INSERT INTO folders Values($id, '$name')")

        notifyObservers()
    }

    fun saveNote() {
        val title = noteCurrent.title
        val content = noteCurrent.content
        val noteId = noteCurrent.id
        val folderId = noteCurrent.folder

        val noteQuery = database.query("SELECT id FROM notes WHERE id = $noteId")

        if (noteQuery.next()){
            database.stmt("UPDATE notes SET title = '$title', content = '$content' WHERE id = $noteId")
        } else {
            database.stmt("INSERT INTO notes VALUES($noteId, '$title', null, null, '$content', 'root', $folderId )")
        }
        notifyObservers()
    }

    fun deleteNote() {
        val noteId = noteCurrent.id
        database.stmt("DELETE FROM notes WHERE id = $noteId")

        notes.removeIf { note -> note.id == noteId }

        for (f in folders.values) {
            f.notes.removeIf { note -> note.id == noteId }
        }
        if (notes.isEmpty()) {
            createNewNote()
        }
        noteCurrent = notes.last { n -> n.id != 0 }

        notifyObservers()
    }

    fun deleteFolder(folderID: Int) {
        if (folderID == 1) return
        database.stmt("DELETE FROM folders WHERE id = $folderID")
        init()

        if (notes.isEmpty()) {
            createNewNote()
        }
        noteCurrent = notes.last { n -> n.id != 0 }

        notifyObservers()
    }

    fun selectNote(noteId: Int) {
        noteCurrent = notes.first { note -> note.id == noteId }
        notifyObservers()
    }

    fun moveCurrentToFolder(newFolder: Int) {
        database.stmt("UPDATE notes SET folder = $newFolder WHERE id = ${noteCurrent.id}")

        folders[newFolder]!!.notes.add(noteCurrent)
        folders[noteCurrent.folder]!!.notes.remove(noteCurrent)
        noteCurrent.folder = newFolder

        notifyObservers()
    }
}
