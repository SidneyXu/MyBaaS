package com.bookislife.flow.data

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import com.google.inject.Guice
import org.junit.Before
import org.junit.Test

/**
 * Created by SidneyXu on 2016/05/29.
 */
class MongoDBSchemaTest {

    private var storage: MongoDBStorage? = null

    private val database = "test"
    private val table = "test"

    val objectMapper = ObjectMapper().apply {
        setSerializationInclusion(JsonInclude.Include.NON_NULL)
    }
    var writer: ObjectWriter = lazy {
        objectMapper.writerWithDefaultPrettyPrinter()
    }.value

    @Before
    fun setUp(){
        val inject = Guice.createInjector(TestModule())
        storage = inject.getInstance(MongoDBStorage::class.java)
    }

    @Test
    fun insert(){

    }
}