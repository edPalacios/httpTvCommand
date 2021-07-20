package com.gompa.remotehttpcommand.screens.editor.repository

import com.gompa.database.dao.RequestDao
import com.gompa.database.entities.RequestEntity
import com.gompa.models.Request
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface RequestRepository {
    suspend fun saveRequest(request: Request) // TODO pending save also icon
    suspend fun getRequests(): Flow<List<Request>>
}

class RequestRepositoryImpl(private val dao: RequestDao) : RequestRepository {

    override suspend fun saveRequest(request: Request) =
        dao.insert(RequestEntity(request))

    override suspend fun getRequests(): Flow<List<Request>> {
        return dao.requests().map { entities ->
            entities.map { it.request }
        }
    }
}
