package com.example.webapitest.repository

import com.example.webapitest.model.User
import com.example.webapitest.repository.db.DatabaseFactory.dbQuery
import com.example.webapitest.repository.db.UserEntity
import com.example.webapitest.repository.db.toUser
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository {

    suspend fun getByOrNull(id: Long): User? {
        return dbQuery {
            UserEntity.findById(id)?.toUser()
        }
    }

    suspend fun list(limit: Int, offset: Long): List<User> {
        return dbQuery {
            UserEntity.all()
            .limit(limit, offset=offset)
            .map { it.toUser() }
        }
    }

    fun insert(user: User): User {
        return transaction {
            val entity = UserEntity.new {
                organizationId = user.organizationId
                name = user.name
                email = user.email
                password = user.password
                verified = user.verified
            }

            // FIXME: insert した User（not null であること）をデータベースから取得して返却すること
            entity.toUser()
        }
    }

    suspend fun count() : Long {
        return dbQuery {
            UserEntity.count()
        }
    }
}