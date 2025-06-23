package com.datflix.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import io.ktor.server.application.Application
import java.io.InputStream

fun Application.configureFirebase() {
    val serviceAccountStream: InputStream = this::class.java.classLoader
        .getResourceAsStream("firebase/serviceAccountKey.json")
        ?: throw IllegalStateException("Firebase service account key not found!")

    val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
        .build()

    if (FirebaseApp.getApps().isEmpty()) {
        FirebaseApp.initializeApp(options)
    }
}
