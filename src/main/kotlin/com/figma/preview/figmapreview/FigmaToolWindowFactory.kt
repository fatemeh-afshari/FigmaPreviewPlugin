package com.figma.preview.figmapreview

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import javax.swing.*
import com.intellij.openapi.ui.Messages

class FigmaToolWindowFactory : ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val tokenService = project.getService(FigmaTokenService::class.java)
        val token = tokenService.getToken()

        if (token.isNullOrEmpty()) {
            Messages.showErrorDialog(project, "Figma token is not set", "Error")
            return
        }
        val myToolWindow = FigmaToolWindow(toolWindow, token)
        val content = ContentFactory.getInstance().createContent(myToolWindow.getContent(), "", false)
        toolWindow.contentManager.addContent(content)
    }
}