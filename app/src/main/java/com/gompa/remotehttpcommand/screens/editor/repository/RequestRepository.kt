package com.gompa.remotehttpcommand.screens.editor.repository

import com.gompa.database.dao.RequestDao
import com.gompa.database.entities.RequestEntity
import com.gompa.models.Request

interface RequestRepository {
    suspend fun saveRequest(request: Request) // TODO pending save also icon
}

class RequestRepositoryImpl(private val dao: RequestDao) : RequestRepository {

    override suspend fun saveRequest(request: Request) =
        dao.insert(RequestEntity(request))
}
