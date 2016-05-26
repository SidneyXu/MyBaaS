package com.bookislife.flow.data

import com.bookislife.flow.data.utils.JacksonDecoder
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.Injector
import org.junit.Before
import org.junit.Test
import java.util.*

/**
 * Created by SidneyXu on 2016/05/26.
 */
class MongoDataStorageTest {

    private var storage: MongoDataStorage? = null

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
        storage = inject.getInstance(MongoDataStorage::class.java)
    }

    @Test
    fun insert() {
        val request = """
        {
           "n":1,
           "name":"Peter",
           "male":true
        }
        """
        val entity = storage?.insert(database, table, request)
        println(entity)

        assert(entity is MongoDocument)
        (entity as MongoDocument).apply {
            println(id)

            assert(id.isNotEmpty())
        }
    }

    @Test
    fun delete() {
        val id = "5746771670fe842fac9a1a6c"
        val n = storage?.delete(database, table, id)
        assert(n == 1)
    }

    @Test
    fun count() {
        val n = storage?.count(database, table)
        println(n)
    }

    @Test
    fun get() {
        val id = "5746777c70fe842fb5604c67"
        val entity = storage?.findById(database, table, id)
        println(entity)
    }

    @Test
    fun list() {
        val entities = storage?.findAll(database, table, null)
        entities?.forEach {
            println(it)
        }
    }

    @Test
    fun find() {
        val commentQuery = BaseQuery.from(table)
        val condition = commentQuery.newCondition()
                .gt("i", 3)
                .create()
        commentQuery.condition = condition

        val constraint = commentQuery.newConstraint()
                .limit(3)
                .skip(2)
                .sort("i",false)
                .createConstraint()
        commentQuery.constraint = constraint

        val query = writer.writeValueAsString(commentQuery)

        println(query)

        val newQuery = JacksonDecoder.decode(query, MongoQuery::class.java)
        println(newQuery)
        println(newQuery.query)

        val entities = storage?.findAll(database, table, query)
        entities?.forEach {
            println(it)
        }
    }

    private inner class TestModule : AbstractModule() {

        override fun configure() {
            bind(MongoDao::class.java)
            bind(MongoDataStorage::class.java)
        }
    }
}