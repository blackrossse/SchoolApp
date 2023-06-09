package com.example.school.database

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


class FirebaseRealtimeDatabaseServiceImpl : FirebaseRealtimeDatabaseService {


    // Flow<MutableList<Map<String, Any>>>
    override fun getMessages(): Flow<MutableList<Map<String, Any>>> {


        val list = listOf<Map<String, Any>>(hashMapOf("key1" to "value1"))

        return callbackFlow {
            list
            awaitClose()
        }
    }

    override fun sendMessage(message: MutableList<Map<String, Any>>) {

    }
}