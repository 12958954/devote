package com.example.cs398project

import javafx.scene.control.TextField

class TitleView: Observer, TextField() {
    var model: Model? = null

    override fun update() {
        if (model != null) {
            text = model?.noteCurrent?.title
        }
    }
}
