package com.example.repository

import com.example.data.model.User
import com.example.data.table.UserTable
import com.example.repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.insert

class repo {
    suspend fun addUser(user:User){
        dbQuery{
            UserTable.insert { userTable ->
                userTable[UserTable.email] = user.email
                userTable[UserTable.hashPassword] = user.hashPassword
                userTable[UserTable.name] = user.userName
            }
        }
    }
}