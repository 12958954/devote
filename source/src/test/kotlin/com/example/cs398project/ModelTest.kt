package com.example.cs398project

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ModelTest {
    private val model = Model()

    @Test
    fun createNewNote() {
        val before = model.notes.size
        model.createNewNote()
        model.createNewNote()
        model.createNewNote()
        val after = model.notes.size

        assertEquals(before + 3, after)
    }

    @Test
    fun deleteNote() {
        val before = model.notes.size
        model.createNewNote()
        model.deleteNote()
        val after = model.notes.size

        assertEquals(before, after)
    }

    @Test
    fun deleteSpecificNote() {
        val idsBefore = HashSet<Int>()
        for (nodes in model.notes) {
            idsBefore.add(nodes.id)
        }
        //Add node
        model.createNewNote()
        val newNodeId = model.noteCurrent.id

        //Add delete just added node
        model.selectNote(newNodeId)
        model.deleteNote()

        assertFalse(idsBefore.contains(newNodeId))
    }

    @Test
    fun saveNote() {
        model.createNewNote()
        model.noteCurrent.content = "TEST"
        model.saveNote()
        var after = ""
        for (note in model.notes) {
            if (note.id == model.noteCurrent.id) after = note.content
        }
        assertEquals("TEST", after)
    }

    @Test
    fun createNewFolder() {
        val before = model.folders.size
        model.createNewFolder()
        val after = model.folders.size

        assertEquals(before + 1, after)
    }

    @Test
    fun deleteFolder() {
        val before = model.folders.size
        model.deleteFolder(model.folders.maxOf { m -> m.key })
        val after = model.folders.size

        assertEquals(before - 1, after)
    }

    @Test
    fun addNoteToFolder() {
        val folder1 = 1
        val folder2 = model.folders.maxOf { m -> m.key }
        model.moveCurrentToFolder(folder1)

        val before = model.folders[folder2]!!.notes.size
        model.moveCurrentToFolder(folder2)
        val after = model.folders[folder2]!!.notes.size

        assertFalse(folder1 == folder2)
        assertEquals(before + 1, after)
    }

    @Test
    fun deleteNoteInFolder() {
        val folderId = model.noteCurrent.folder
        model.createNewNote(folderId)
        val before = model.folders[folderId]!!.notes.size
        model.deleteNote()
        val after = model.folders[folderId]!!.notes.size

        assertEquals(before - 1, after)
    }
    @Test
    fun renameFolder() {
        val folderId = model.folders.maxOf { m -> m.key }

        model.renameFolder(folderId, "TEST_FOR_RENAMING_BEFORE")
        val before = model.folders[folderId]!!.name
        model.renameFolder(folderId, "TEST_FOR_RENAMING_AFTER")
        val after = model.folders[folderId]!!.name

        assertFalse(before == after)
        assertEquals("TEST_FOR_RENAMING_AFTER", after)
    }


}
