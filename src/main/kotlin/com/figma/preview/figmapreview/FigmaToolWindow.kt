package com.figma.preview.figmapreview

import com.intellij.openapi.wm.ToolWindow
import javax.swing.*

class FigmaToolWindow(toolWindow: ToolWindow , token:String) {
    private val figmaService = FigmaService(token)
    private val mainPanel: JPanel = JPanel()
    private val fileIdField: JTextField = JTextField(20)
    private val nodeIdField: JTextField = JTextField(20)
    private val getImageButton: JButton = JButton("Get Component Image")
    private val resultArea: JTextArea = JTextArea(10, 30)

    init {
        mainPanel.layout = BoxLayout(mainPanel, BoxLayout.Y_AXIS)
        mainPanel.add(JLabel("File ID:"))
        mainPanel.add(fileIdField)
        mainPanel.add(JLabel("Node ID:"))
        mainPanel.add(nodeIdField)
        mainPanel.add(getImageButton)
        mainPanel.add(JScrollPane(resultArea))

        getImageButton.addActionListener {
            val imageUrl = figmaService.getComponentImage(fileIdField.text, nodeIdField.text)
            resultArea.text = imageUrl
        }
    }

    fun getContent() = mainPanel
}