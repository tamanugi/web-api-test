package com.example.webapitest.service

import com.example.webapitest.model.User
import com.example.webapitest.repository.UserRepository

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
}