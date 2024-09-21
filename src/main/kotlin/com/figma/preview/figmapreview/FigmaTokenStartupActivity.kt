package com.figma.preview.figmapreview

import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import com.intellij.openapi.ui.Messages

class FigmaTokenStartupActivity : StartupActivity {
    override fun runActivity(project: Project) {
        val tokenService = project.getService(FigmaTokenService::class.java)
        
        if (tokenService.getToken().isNullOrEmpty()) {
            val token = Messages.showInputDialog(
                project,
                "Please enter your Figma API token:",
                "Figma Token",
                Messages.getQuestionIcon()
            )
            
            if (!token.isNullOrEmpty()) {
                tokenService.setToken(token)
            }
        }
    }
}