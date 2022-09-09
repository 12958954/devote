package com.example.cs398project

class Folder(val id: Int, var name: String) {
    val notes = mutableSetOf<Note>()
}
