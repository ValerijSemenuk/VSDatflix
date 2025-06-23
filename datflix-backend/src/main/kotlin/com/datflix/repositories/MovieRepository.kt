package com.datflix.repositories

import com.datflix.models.Movie
import com.google.cloud.firestore.Firestore
import com.google.firebase.cloud.FirestoreClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository : Repository<Movie, String> {

    private val firestore: Firestore = FirestoreClient.getFirestore()
    private val collection = firestore.collection("movies")

    override suspend fun getAll(): List<Movie> = withContext(Dispatchers.IO) {
        collection.get().get().documents.mapNotNull { doc ->
            doc.toObject(Movie::class.java)?.copy(id = doc.id)
        }
    }

    override suspend fun getById(id: String): Movie? = withContext(Dispatchers.IO) {
        val doc = collection.document(id).get().get()
        if (doc.exists()) doc.toObject(Movie::class.java)?.copy(id = doc.id) else null
    }

    override suspend fun create(movie: Movie): Movie = withContext(Dispatchers.IO) {
        val docRef = collection.document()
        val data = hashMapOf<String, Any?>(
            "id" to docRef.id,
            "title" to movie.title,
            "description" to movie.description,
            "releaseYear" to movie.releaseYear,
            "durationMinutes" to movie.durationMinutes,
            "posterUrl" to movie.posterUrl,
            "trailerUrl" to movie.trailerUrl,
            "videoUrl" to movie.videoUrl,
            "genreIds" to (movie.genreIds?.toList() ?: emptyList<String>()),
            "averageRating" to movie.averageRating,
            "isTrending" to movie.isTrending
        )
        println("Setting data for create: $data")
        docRef.set(data).get()

        val savedDoc = docRef.get().get()
        println("Saved data after create: ${savedDoc.data}")

        movie.copy(id = docRef.id)
    }


    override suspend fun update(movie: Movie): Boolean = withContext(Dispatchers.IO) {
        val id = movie.id ?: return@withContext false
        val docRef = collection.document(id)
        if (!docRef.get().get().exists()) return@withContext false
        val data = hashMapOf<String, Any?>(
            "title" to movie.title,
            "description" to movie.description,
            "releaseYear" to movie.releaseYear,
            "durationMinutes" to movie.durationMinutes,
            "posterUrl" to movie.posterUrl,
            "trailerUrl" to movie.trailerUrl,
            "videoUrl" to movie.videoUrl,
            "genreIds" to (movie.genreIds?.toList() ?: emptyList<String>()),
            "averageRating" to movie.averageRating,
            "isTrending" to movie.isTrending
        )

        println("Setting data for update: $data")
        docRef.set(data).get()
        true
    }

    override suspend fun delete(id: String): Boolean = withContext(Dispatchers.IO) {
        collection.document(id).delete().get()
        true
    }
}