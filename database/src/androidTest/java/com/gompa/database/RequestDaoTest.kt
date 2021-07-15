package com.gompa.database

import com.gompa.database.dao.RequestDao
import com.gompa.database.entities.RequestEntity
import com.gompa.models.Request
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class RequestDaoTest : BaseDaoTest() {

    private lateinit var underTest: RequestDao

    @Before
    fun setUp() {
        underTest = db.httpDao()
    }

    @Test
    fun given_duplicated_entity_then_apply_onConflict_strategy() = scope.runBlockingTest {
        val entity = RequestEntity(request = Request(title = "title", url = "url"))
        underTest.insert(entity)
        underTest.insert(entity)

        // To avoid underTest.requests() flow run forever we need to wrap it into async block taking 1 to consume a single event :_(
        val count =  async {
            underTest.requests().take(1).count()
        }
        assertEquals("Duplicated entities are replaced (same 'url' constraint)",  1, count.await())
    }
}