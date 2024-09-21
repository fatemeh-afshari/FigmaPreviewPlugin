package com.figma.preview.figmapreview

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@Service
@State(name = "FigmaTokenService", storages = [Storage("figmaToken.xml")])
class FigmaTokenService : PersistentStateComponent<FigmaTokenService.State> {
    data class State(var token: String? = null)

    private var myState = State()

    override fun getState(): State = myState

    override fun loadState(state: State) {
        myState = state
    }

    fun setToken(token: String) {
        myState.token = token
    }

    fun getToken(): String? = myState.token
}