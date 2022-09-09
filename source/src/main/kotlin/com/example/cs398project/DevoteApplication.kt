package com.example.cs398project

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class DevoteApplication : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(DevoteApplication::class.java.getResource("devote-view.fxml"))
        val database = Mysql()
        val app = database.query("SELECT prefHeight, prefWidth, xPos, yPos FROM app")
        var prefHeight = 320.0
        var prefWidth = 640.0
        var xPos = 0.0
        var yPos = 0.0
        while (app.next()) {
            prefHeight = app.getDouble(1)
            prefWidth = app.getDouble(2)
            xPos = app.getDouble(3)
            yPos = app.getDouble(4)
        }
        val scene = Scene(fxmlLoader.load(), prefWidth, prefHeight)
        stage.x = xPos
        stage.y = yPos
        stage.title = "Devote"
        stage.scene = scene
        stage.setOnCloseRequest {
            database.stmt("UPDATE app SET prefHeight = '${stage.height}', prefWidth = '${stage.width}', " +
                    "xPos = '${stage.x}', yPos = '${stage.y}'")
        }
        stage.show()
    }
}

fun main() {
    Application.launch(DevoteApplication::class.java)
}
