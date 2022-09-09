package com.example.cs398project

import javafx.application.Platform
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.ColorPicker
import javafx.scene.control.ComboBox
import javafx.scene.control.Separator
import javafx.scene.control.ToggleButton
import javafx.scene.control.ToolBar
import javafx.scene.web.HTMLEditor

class NoteView: Observer, HTMLEditor() {
    var model: Model? = null
    var bullets: ToggleButton? = null
    var numbers: ToggleButton? = null
    var bold: ToggleButton? = null
    var italic: ToggleButton? = null
    var underline: ToggleButton? = null
    var strike: ToggleButton? = null
    var headings: ComboBox<*>? = null
    var cut: Button? = null
    var copy: Button? = null
    var paste: Button? = null
    var left: ToggleButton? = null
    var right: ToggleButton? = null
    var centre: ToggleButton? = null
    var indentDec: Button? = null
    var indentInc: Button? = null
    var textCol: ColorPicker? = null
    var highCol: ColorPicker? = null
    var font: ComboBox<*>? = null
    var codeFormat = Button("</>")
    val checklist = Button("✓")

    fun setupToolbar(toolBar: ToolBar) {
        isVisible = false
        Platform.runLater {
            val nodes = lookupAll(".tool-bar")
            val allItems = ArrayList<Node>()
            for (node in nodes) {
                val toolBars = node as ToolBar
                for (n in toolBars.items) {
                    allItems.add(n)
                }
                node.isVisible = false
                node.isManaged = false
            }
            bullets = allItems[12] as ToggleButton
            numbers = allItems[13] as ToggleButton
            bold = allItems[21] as ToggleButton
            italic = allItems[22] as ToggleButton
            underline = allItems[23] as ToggleButton
            strike = allItems[24] as ToggleButton
            headings = allItems[19] as ComboBox<*>

            cut = allItems[0] as Button
            copy = allItems[1] as Button
            paste = allItems[2] as Button
            left = allItems[4] as ToggleButton
            centre = allItems[5] as ToggleButton
            right = allItems[6] as ToggleButton
            indentInc = allItems[10] as Button
            indentDec = allItems[9] as Button

            textCol = allItems[15] as ColorPicker
            highCol = allItems[16]as ColorPicker
            font = allItems[18] as ComboBox<*>

            toolBar.items.addAll(headings, Separator(), bullets, numbers, checklist, Separator(), bold, italic, underline, strike, codeFormat)

            isVisible = true
        }
    }

    override fun update() {
        if (model != null) {
            htmlText = model?.noteCurrent?.content
        }
    }
}