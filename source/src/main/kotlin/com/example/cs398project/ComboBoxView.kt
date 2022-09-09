package com.example.cs398project

import javafx.application.Platform
import javafx.scene.control.ComboBox

class ComboBoxView: Observer, ComboBox<ComboBoxItem>() {
    var model: Model? = null

    override fun update() {
        promptText = "Move to..."
        Platform.runLater(Runnable {
            updateFunction()
        })
    }

    private fun updateFunction() {
        items.clear()
        if (model != null) {
            for (folder in model!!.folders.values) {
                if (model!!.noteCurrent.folder != folder.id) {
                    items.add(ComboBoxItem(folder, folder.name))
                }
            }
        }
    }
}