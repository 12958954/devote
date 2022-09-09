package com.example.cs398project

import javafx.application.Platform
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.control.ToolBar
import javafx.scene.web.WebView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.text.Text
import javafx.stage.Stage

class Controller {

    private val model = Model()

    @FXML
    private lateinit var noteView: NoteView
    @FXML
    private lateinit var titleView: TitleView
    @FXML
    private lateinit var directoryView: DirectoryView
    @FXML
    private lateinit var searchBar: TextField
    @FXML
    private lateinit var toolBar: ToolBar
    @FXML
    lateinit var moveFolder: ComboBoxView

    private var lastFolderID = 1

    fun initialize() {
        noteView.model = model
        titleView.model = model
        directoryView.model = model
        moveFolder.model = model

        model.attach(noteView)
        model.attach(titleView)
        model.attach(directoryView)
        model.attach(moveFolder)

        noteView.setupToolbar(toolBar)

        noteView.checklist.onAction = EventHandler {
            val check = "<input type=\"checkbox\">"
            noteView.htmlText = noteView.htmlText + check
        }

        // code formatting
        noteView.codeFormat.onAction = EventHandler {
            val selection = WebView().engine.executeScript("window.getSelection().toString()");
            val codeBG = """<p><code style="background-color: #38302F; border-radius: 5px;padding: 10px; color: #F6AE29;">&nbsp;</code></p></body></html>"""
            val regex = Regex("</body></html>")
            noteView.htmlText = regex.replace(noteView.htmlText, codeBG)
            println(noteView.htmlText)
        }
""""""
        model.notifyObservers()
    }


    fun createNewFolder() {
        saveNote()
        model.createNewFolder()
    }



    fun createNewNote() {
        if (directoryView.selectionModel.selectedItem != null) {
            val directoryItemView = directoryView.selectionModel.selectedItem as DirectoryItemView
            lastFolderID = directoryItemView.id
        }

        saveNote()
        model.createNewNote(lastFolderID)
    }

    fun deleteNote() {
            model.deleteNote()
    }

    fun deleteFolder() {
        val directoryItemView = directoryView.selectionModel.selectedItem as DirectoryItemView
        model.deleteFolder(directoryItemView.id)
    }

    fun handleQuitMenu() {
        saveNote()
        Platform.exit()
    }

    fun saveNote() {
        model.noteCurrent.content = noteView.htmlText
        model.noteCurrent.title = titleView.text
        model.saveNote()
    }

    fun selectNote() {
        // Get ID before saving note
        // To avoid clearing selection during directory view update
        if (directoryView.selectionModel.selectedItem == null) return // clicking empty space

        val directoryItemView = directoryView.selectionModel.selectedItem as DirectoryItemView

        if (!directoryItemView._isFolder) {

            val noteId = directoryItemView.id
            model.selectNote(noteId)
            saveNote()
        }
        //if (directoryItemView.isFolder) return
    }

    fun searchNotes() {
        saveNote()
        model.searchText = searchBar.text
    }

    fun cut() {
        noteView.cut?.fire()
    }

    fun copy() {
        noteView.copy?.fire()
    }

    fun paste() {
        noteView.paste?.fire()
    }

    fun bulletList() {
        noteView.bullets?.fire()
    }

    fun numberedList() {
        noteView.numbers?.fire()
    }

    fun bold() {
        noteView.bold?.fire()
    }

    fun italic() {
        noteView.italic?.fire()
    }

    fun underline() {
        noteView.underline?.fire()
    }

    fun strikethrough() {
        noteView.strike?.fire()
    }

    fun fonts() {
        noteView.font?.show()
    }

    fun alignRight() {
        noteView.right?.fire()
    }

    fun alignLeft() {
        noteView.left?.fire()
    }

    fun centre() {
        noteView.centre?.fire()
    }

    fun increaseIndent() {
        noteView.indentInc?.fire()
    }

    fun decreaseIndent() {
        noteView.indentDec?.fire()
    }

    fun textColour() {
        noteView.textCol?.show()
    }

    fun highlightColour() {
        noteView.highCol?.show()
    }

    fun formatCode() {
        noteView.codeFormat?.fire()
    }

    fun moveNoteToFolder() {
        val selected = moveFolder.selectionModel.selectedItem
        if (selected != null) {
            model.moveCurrentToFolder(selected.value.id)
        }
    }

    fun mouseClicked(mouseEvent: MouseEvent) {
        selectNote()

        if (mouseEvent.clickCount == 2){
            val directoryItem = directoryView.selectionModel.selectedItem as DirectoryItemView
            if (directoryItem._isFolder && directoryItem.id != 1) {
                popUpRenameFolder(directoryItem.id, directoryItem.value)
            }
        }
    }

    fun renameFolder() {
        val currentFolder = model.folders[model.noteCurrent.folder]
        if (currentFolder!!.id != 1) {
            popUpRenameFolder(currentFolder.id, currentFolder.name)
        }
    }

    private fun popUpRenameFolder(id: Int, oldTitle: String) {
        val newStage = Stage()
        newStage.isResizable = false
        val buttons = HBox()
        val text = VBox()
        val root = VBox()

        val okButton = Button("Rename")
        val noButton = Button("Cancel")
        val textTop = Text("Rename folder")
        val textBottom = TextField(oldTitle)
        val textGap = Text("")


        okButton.onAction = EventHandler {
            val newTitle = textBottom.characters.toString()
            model.renameFolder(id, newTitle)
            newStage.close()
        }

        noButton.onAction = EventHandler {
            newStage.close()
        }

        buttons.children.addAll(noButton, okButton)
        text.children.addAll(textTop,textBottom, textGap)
        root.children.addAll(text, buttons)
        buttons.alignment = Pos.BOTTOM_CENTER
        text.alignment = Pos.TOP_CENTER
        root.alignment = Pos.CENTER

        val stageScene = Scene(root, 200.0, 100.0)
        newStage.scene = stageScene
        newStage.show()
    }


}
