package com.gompa.remotehttpcommand.screens.editor.repository

import com.gompa.database.dao.RequestDao

interface RequestRepository {
}

class RequestRepositoryImpl(private val dao: RequestDao): RequestRepository{

}
