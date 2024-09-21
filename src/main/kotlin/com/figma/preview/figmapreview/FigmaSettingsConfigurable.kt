package com.figma.preview.figmapreview

import com.intellij.openapi.options.Configurable
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTextField
import com.intellij.openapi.project.Project
import javax.swing.*

class FigmaSettingsConfigurable(private val project: Project) : Configurable {
    private var tokenField: JTextField? = null
    private val tokenService = project.getService(FigmaTokenService::class.java)

    override fun createComponent(): JComponent {
        val panel = JPanel()
        tokenField = JTextField(20)
        panel.add(JLabel("Figma API Token:"))
        panel.add(tokenField)
        return panel
    }

    override fun isModified(): Boolean {
        return tokenField?.text != tokenService.getToken()
    }

    override fun apply() {
        tokenService.setToken(tokenField?.text ?: "")
    }

    override fun reset() {
        tokenField?.text = tokenService.getToken() ?: ""
    }

    override fun getDisplayName(): String = "Figma Settings"
}