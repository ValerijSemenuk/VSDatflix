package com.datflix.services
import com.datflix.models.Review
import com.datflix.repositories.ReviewRepository

class ReviewService(private val reviewRepository: ReviewRepository) {

    suspend fun getAllReviews(): List<Review> = reviewRepository.getAll()

    suspend fun getReviewById(id: String): Review? = reviewRepository.getById(id)

    suspend fun createReview(review: Review): Review {
        require(!review.text.isNullOrBlank()) { "Review text cannot be empty" }
        require(review.rating in 1..10) { "Rating must be between 1 and 10" }
        return reviewRepository.create(review)
    }

    suspend fun updateReview(review: Review): Boolean {
        val existing = reviewRepository.getById(review.id ?: return false) ?: return false
        return reviewRepository.update(review)
    }

    suspend fun deleteReview(id: String): Boolean {
        val existing = reviewRepository.getById(id) ?: return false
        return reviewRepository.delete(id)
    }
}
