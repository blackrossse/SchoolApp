package com.example.school.base

interface EventHandler<T> {
    fun obtainEvent(event: T)
}