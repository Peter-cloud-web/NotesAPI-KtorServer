package com.example.repository

import com.example.data.model.user_model.User
import com.example.data.table.UserTable
import com.example.repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*

class Repo {
    // Add user to database in the user table
    suspend fun addUser(user: User){
        dbQuery{
            UserTable.insert { userTable ->
                userTable[UserTable.email] = user.email
                userTable[UserTable.hashPassword] = user.hashPassword
                userTable[UserTable.name] = user.userName
            }
        }
    }
    //Authenticate user by email which is unique
    //Convert row to user using row to user method
    suspend fun findUserByEmail(email:String) = dbQuery {
        UserTable.selectAll().where { UserTable.email.eq(email) }
            .map { row ->
                rowToUser(row)
            }
            .singleOrNull()
    }

    //convert row to user object
    private fun rowToUser(row: ResultRow?): User?{
        if(row == null){
            return null
        }
        return User(
            email = row[UserTable.email],
            hashPassword = row[UserTable.hashPassword],
            userName = row[UserTable.name]
        )
    }
}