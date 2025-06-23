package com.datflix.services
import com.datflix.models.Actor
import com.datflix.repositories.ActorRepository
import java.util.UUID

class ActorService(private val actorRepository: ActorRepository) {

    suspend fun getAllActors(): List<Actor> = actorRepository.getAll()

    suspend fun getActorById(id: String): Actor? = actorRepository.getById(id)

    suspend fun createActor(actor: Actor): Actor {
        require(!actor.name.isNullOrBlank()) { "Actor name cannot be empty" }  // <-- тут
        val id = actor.id ?: UUID.randomUUID().toString()
        require(actorRepository.getById(id) == null) { "Actor with this ID already exists" }
        val newActor = actor.copy(id = id)
        return actorRepository.create(newActor)
    }

    suspend fun updateActor(actor: Actor): Boolean {
        val id = actor.id ?: return false
        val existing = actorRepository.getById(id) ?: return false
        return actorRepository.update(actor)
    }

    suspend fun deleteActor(id: String): Boolean {
        val existing = actorRepository.getById(id) ?: return false
        return actorRepository.delete(id)
    }
}
