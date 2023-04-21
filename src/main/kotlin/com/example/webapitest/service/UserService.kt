package com.example.webapitest.service

import com.example.webapitest.model.User
import com.example.webapitest.repository.UserRepository
import kotlin.math.max

class UserService(
    // TODO: DI by koin
    private val userRepository: UserRepository) {

    suspend fun findUserById(id: Long): User? {
        return userRepository.getByOrNull(id)?.let {
            User(
                id=it.id,
                organizationId= it.organizationId,
                name = it.name,
                email = it.email,
                password = it.password,
                verified = it.verified
            )
        }
    }

    fun createUser(user: User): User {
        return userRepository.insert(user)
    }

    suspend fun findUsers(page: Int, limit: Int): List<User> {
        val limit = max(limit, 0)
        val offset = max((page - 1) * limit, 0)

        return userRepository.list(limit=limit, offset=offset.toLong())
    }

    suspend fun count(): Long {
        return userRepository.count()
    }
}