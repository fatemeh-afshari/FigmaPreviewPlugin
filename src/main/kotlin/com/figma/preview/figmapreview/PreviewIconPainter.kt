package com.figma.preview.figmapreview

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.EditorLinePainter
import com.intellij.openapi.editor.LineExtensionInfo
import com.intellij.openapi.editor.markup.TextAttributes
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiManager
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import org.jetbrains.kotlin.idea.KotlinLanguage
import org.jetbrains.kotlin.psi.KtAnnotationEntry
import org.jetbrains.kotlin.psi.KtFile
import javax.swing.Icon
import com.intellij.openapi.util.IconLoader
import com.intellij.openapi.fileEditor.FileDocumentManager


class PreviewIconPainter : EditorLinePainter() {
    private val previewIcon: Icon = IconLoader.getIcon("/icons/preview_icon.svg", this::class.java)

    override fun getLineExtensions(project: Project, file: VirtualFile, lineNumber: Int): Collection<LineExtensionInfo>? {
        val psiFile = PsiManager.getInstance(project).findFile(file) as? KtFile ?: return null
        if (psiFile.language != KotlinLanguage.INSTANCE) return null

        val document = FileDocumentManager.getInstance().getDocument(file) ?: return null
        val lineStartOffset = document.getLineStartOffset(lineNumber)
        val lineEndOffset = document.getLineEndOffset(lineNumber)
        val element = psiFile.findElementAt(lineStartOffset) ?: return null

        val annotation = PsiTreeUtil.getParentOfType(element, KtAnnotationEntry::class.java, false) ?: return null

        if (annotation.shortName?.asString() == "Preview" &&
            annotation.textRange.startOffset >= lineStartOffset &&
            annotation.textRange.endOffset <= lineEndOffset) {
            return listOf(LineExtensionInfo("", TextAttributes()))
        }

        return null
    }
}