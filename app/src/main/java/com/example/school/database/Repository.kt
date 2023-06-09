package com.example.school.database

import kotlinx.coroutines.flow.Flow


class Repository(
    private val firebaseRealtimeDatabaseService: FirebaseRealtimeDatabaseServiceImpl
) {

    suspend fun getMessages(): Flow<MutableList<Map<String, Any>>> {
        return firebaseRealtimeDatabaseService.getMessages()
    }

    suspend fun addMessage(message: MutableList<Map<String, Any>>) {
        firebaseRealtimeDatabaseService.sendMessage(message)
    }
}