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
    private var schemaService: MongoSchemaService? = null

    private val database = "test"
    private val table = "test"

    val objectMapper = ObjectMapper().apply {
        setSerializationInclusion(JsonInclude.Include.NON_NULL)
    }
    var writer: ObjectWriter = lazy {
        objectMapper.writerWithDefaultPrettyPrinter()
    }.value

    @Before
    fun setUp() {
        val inject = Guice.createInjector(TestModule())
        storage = inject.getInstance(MongoDBStorage::class.java)
        schemaService = inject.getInstance(MongoSchemaService::class.java)
    }

    @Test
    fun insert() {
        val schema = MongoSchema("test", "t_test")
        schema.addColumn("x", ColumnType.Number)
        schema.addColumn("name", ColumnType.String)
        schemaService?.insert(schema)

        val document = schema.toDocument()
        println(document)

        val id = schemaService?.insert(schema)
        println(id)

        assert(id != null)
    }

    @Test
    fun get(){
        val schema = schemaService?.get("574cc2c1b740dec655c24266")
        println(schema)

        println(schema?.columnInfos?.values?.iterator()?.next())
    }


}