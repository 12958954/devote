package com.example.cs398project

import javafx.scene.control.TreeView

class DirectoryView: Observer, TreeView<String>() {
    private val rootFolder = DirectoryItemView(1, "root", true)
    var model: Model? = null

    init {
        root = rootFolder
        isShowRoot = false
    }

    override fun update() {
        if (model != null) {
            if (model!!.searchText.isEmpty()) {
                root.children.clear()
                val folders = model!!.folders
                val itemViews = HashMap<Int, DirectoryItemView>()

                for (folder in folders.values) {
                    val folderItem = DirectoryItemView(folder.id, folder.name, true)
                    itemViews[folder.id] = folderItem
                    for (note in folder.notes) {
                        val noteItem = DirectoryItemView(note.id, note.title, false)
                        folderItem.children.add(noteItem)
                    }
                }

                for (i in itemViews.values) {
                    if (i.id == 1) {
                        for (n in i.children) {
                            root.children.add(n)
                        }
                    } else {
                        root.children.add(i)
                    }
                }

                var select = DirectoryItemView(1, "placeholder", false)

                for (folder in itemViews.values) {
                    for (child in folder.children) {
                        val childDView = child as DirectoryItemView
                        if (childDView.id == model!!.noteCurrent.id) {
                            select = childDView
                        }
                    }
                }

                selectionModel.select(select)
            } else {
                root.children.clear()
                for (note in model!!.notes) {
                    val searchText = model!!.searchText.lowercase()
                    if (!(
                                searchText in note.title.lowercase()
                                        || searchText in note.content.lowercase()
                                )) {
                        continue
                    }

                    val itemView = DirectoryItemView(note.id, note.title, false)

                    root.children.add(itemView)
                }
            }
        }
    }
}
