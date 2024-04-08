package com.example.repository

import com.example.data.table.UserTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import javax.xml.validation.Schema

object DatabaseFactory {

    /* Hikari is a popular connection pooling library for Java applications, particularly used in
       database access. Connection pooling is a technique used to enhance the performance and scalability
      of applications that need to access databases frequently.
    When an application needs to connect to a database, establishing a new connection can be resource-intensive
    and time-consuming. Connection pooling addresses this issue by creating a pool of pre-initialized database connections that can be reused by multiple clients. Instead of creating a new connection each time, clients can borrow a connection from the pool, use it, and then return it to the pool for reuse.
    HikariCP, often simply referred to as Hikari, is known for its high performance, reliability,
    and efficiency. It is designed to be lightweight and fast, making it a popular choice for Java
    developers building applications that require efficient database access.
    In summary, Hikari is a connection pooling library that helps Java applications manage
    and reuse database connections effectively, improving performance and scalability.*/

    //Connect to database
    fun init(){
        Database.connect(hikari())

        //Create usertable in the database
        transaction{
            SchemaUtils.create(UserTable)
        }
    }

//hikari(), is responsible for configuring and returning an instance of HikariDataSource,
    // which is typically used for managing connections to a database in Java or Kotlin applications.
    // Let's break down the code step by step:
    fun hikari(): HikariDataSource{

        //create an instance of the HikariConfig to configure hikari connection
        val config = HikariConfig()

    //Sets the driver class name for the database connection by retrieving it from an environment variable named "JDBC_DRIVER".
        config.driverClassName = System.getenv("JDBC_DRIVER")

    //Sets the JDBC URL for the database connection by retrieving it from an environment variable named "JDBC_DATABASE_URL".
        config.jdbcUrl = System.getenv("DATABASE_URL")

    //Sets the maximum number of connections that can be maintained in the connection pool to 3.
        config.maximumPoolSize = 3

    //Sets the auto-commit mode for the connections to false, meaning that transactions must be explicitly committed or rolled back.
        config.isAutoCommit = false

    //Sets the transaction isolation level to TRANSACTION_REPEATABLE_READ, ensuring that transactions are isolated from each other to a certain degree.
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"

    //Validates the configuration settings to ensure they are valid.
    config.validate()

        return HikariDataSource(config)
    }

    //Perform database transaction in the background
    suspend fun<T> dbQuery(block:() -> T):T = withContext(Dispatchers.IO){
        transaction{block()}
    }
}