package com.datflix.services
import com.datflix.models.User
import com.datflix.repositories.UserRepository
import java.util.UUID

class UserService(private val userRepository: UserRepository) {

    suspend fun getAllUsers(): List<User> = userRepository.getAll()

    suspend fun getUserById(id: String): User? = userRepository.getById(id)

    suspend fun createUser(user: User): User {
        require(!user.email.isNullOrBlank()) { "Email cannot be empty" }
        require(!user.username.isNullOrBlank()) { "Username cannot be empty" }
        require(!user.passwordHash.isNullOrBlank()) { "Password hash cannot be empty" }
        val id = user.id ?: UUID.randomUUID().toString()
        require(userRepository.getById(id) == null) { "User with this ID already exists" }
        val newUser = user.copy(id = id)
        return userRepository.create(newUser)
    }

    suspend fun updateUser(user: User): Boolean {
        val id = user.id ?: return false
        val existing = userRepository.getById(id) ?: return false
        return userRepository.update(user)
    }

    suspend fun deleteUser(id: String): Boolean {
        val existing = userRepository.getById(id) ?: return false
        return userRepository.delete(id)
    }
}
