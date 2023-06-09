package com.example.school.database

import kotlinx.coroutines.flow.Flow

interface FirebaseRealtimeDatabaseService {
    fun getMessages(): Flow<MutableList<Map<String, Any>>>
    fun sendMessage(message: MutableList<Map<String, Any>>)
}