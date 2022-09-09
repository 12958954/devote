package com.example.cs398project

class Note(val id: Int, private val _folder: Int = 1) {
    var title = "Untitled_$id"
    var content = ""
    var folder = _folder
}
