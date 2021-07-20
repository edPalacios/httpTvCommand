package com.gompa.remotehttpcommand.screens.editor.repository

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

// TODO Dummy repository in memory to speed up things. This should be done with Room keeping the ref of the icon from a lib
object IconRepository : LiveData<ImageVector>() {
    private val _icon = MutableLiveData<ImageVector>() // TODO replace VectorDrawable
    val icon: LiveData<ImageVector> = _icon

    fun onSaveIcon(icon: ImageVector) {
        _icon.value = icon
    }
}
