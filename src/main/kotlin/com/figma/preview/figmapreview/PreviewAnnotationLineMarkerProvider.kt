package com.figma.preview.figmapreview

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.codeInsight.daemon.LineMarkerProvider
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiIdentifier
import com.intellij.ui.IconManager
import org.jetbrains.kotlin.idea.KotlinLanguage
import org.jetbrains.kotlin.psi.KtAnnotationEntry
import org.jetbrains.kotlin.psi.KtNamedFunction
import javax.swing.Icon

class PreviewAnnotationLineMarkerProvider : RelatedItemLineMarkerProvider() {

    override fun collectNavigationMarkers(
        element: PsiElement,
        result: MutableCollection<in RelatedItemLineMarkerInfo<*>>
    ) {
        if (element.language != KotlinLanguage.INSTANCE) return
        if (element !is KtAnnotationEntry || element.shortName?.asString() != "Preview") return

        val icon = IconManager.getInstance().getIcon("icons/preview.png", this::class.java)
        val builder = NavigationGutterIconBuilder.create(icon)
            .setTarget(element)
            .setTooltipText("Jetpack Compose Preview")

        result.add(builder.createLineMarkerInfo(element))
    }
}