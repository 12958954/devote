package com.example.cs398project

import javafx.scene.control.TreeItem

class DirectoryItemView(val id: Int, value: String, val _isFolder: Boolean): TreeItem<String>(value) {
}

