package com.gompa.remotehttpcommand.screens.editor

import com.gompa.database.Database
import com.gompa.remotehttpcommand.screens.editor.repository.IconRepository
import com.gompa.remotehttpcommand.screens.editor.repository.RequestRepository
import com.gompa.remotehttpcommand.screens.editor.repository.RequestRepositoryImpl

object RequestEditorModule {
    fun requestRepository(): RequestRepository = RequestRepositoryImpl(Database.httpDao)
    fun iconRepository() : IconRepository = IconRepository
}