package com.datflix.repositories

interface Repository<T, ID> {
    suspend fun getAll(): List<T>
    suspend fun getById(id: ID): T?
    suspend fun create(entity: T): T
    suspend fun update(entity: T): Boolean
    suspend fun delete(id: ID): Boolean
}
