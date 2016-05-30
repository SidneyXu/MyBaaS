package com.bookislife.flow.data

import com.bookislife.flow.data.utils.JacksonDecoder
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import com.google.inject.AbstractModule
import com.google.inject.Guice
import org.junit.Before
import org.junit.Test

/**
 * Created by SidneyXu on 2016/05/26.
 */
class MongoDBStorageTest {

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
    fun setUp() {
        val inject = Guice.createInjector(TestModule())
        storage = inject.getInstance(MongoDBStorage::class.java)
    }

    //TODO
    @Test
    fun insert() {
        val request = """
        {
           "n":1,
           "name":"Peter",
           "male":true,
           "id":"57471ee4aed076eb7586f1c0"
        }
        """

        //TODO

        val entity = storage?.insert(database, table, request)
        println(entity)

        assert(entity is MongoDocument)
        (entity as MongoDocument).apply {
            println(id)
            assert(id.isNotEmpty())
//            assert(id == "lmn")
        }
    }

    @Test
    fun delete() {
        val id = "xyz"
        val n = storage?.delete(database, table, id)
        assert(n == 1)
    }

    @Test
    fun deleteQuery() {
        val commentQuery = BaseQuery.from(table)
        val condition = commentQuery.newCondition()
                .gt("i", 2)
                .create()
        commentQuery.condition = condition

        val constraint = commentQuery.newConstraint()
                .limit(3)
                .skip(0)
                .sort("i", false)
                .create()
        commentQuery.constraint = constraint

        val projection = commentQuery.newProjection()
                .select("i")
                .select("j")
                .create()
        commentQuery.projection = projection

        val query = writer.writeValueAsString(commentQuery)
        println(query)

        val n = storage?.deleteAll(database, table, query)
        println(n)
    }

    @Test
    fun count() {
        val n = storage?.count(database, table, null)
        println(n)
    }

    @Test
    fun countQuery() {
        val q = BaseQuery.from(table)
        val condition = q.newCondition()
                //                .gt("i", 1)
                .eq(BaseEntity.FIELD_ID, "foobar2")
                .create()
        q.condition = condition

        val query = writer.writeValueAsString(q)
        println(query)

        val n = storage?.count(database, table, query)
        println(n)
    }

    @Test
    fun get() {
        val id = "xyz"
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
//                .gt("i", 3)
                .eq("id","57471ee4aed076eb7586f1c3")
                .create()
        commentQuery.condition = condition

        val constraint = commentQuery.newConstraint()
                .limit(3)
                .skip(0)
                .sort("i", false)
                .create()
        commentQuery.constraint = constraint

        val projection = commentQuery.newProjection()
//                .select("i")
//                .select("j")
                .create()
        commentQuery.projection = projection

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

    @Test
    fun update() {
        val commentQuery = BaseQuery.from(table)
        val condition = commentQuery.newCondition()
                .eq(BaseEntity.FIELD_ID, "foo")
                .create()
        commentQuery.condition = condition

        val query = writer.writeValueAsString(commentQuery)

        val modifier = BaseModifier.newBuilder()
                .inc("n", 1)
                .set("name", "Jane")
                .create()
        val m = writer.writeValueAsString(modifier)
        println(m)

        val nm = JacksonDecoder.decode(m, BaseModifier::class.java)
        println(nm)

        val n = storage?.update(database, table, query, m)
        println(n)

    }

}

class TestModule : AbstractModule() {

    override fun configure() {
        bind(MongoDao::class.java)
        bind(MongoDBStorage::class.java)
        bind(MongoSchemaService::class.java)
    }
}