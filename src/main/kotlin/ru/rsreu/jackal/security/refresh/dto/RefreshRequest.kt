package ru.rsreu.jackal.security.refresh.dto

data class RefreshRequest(val accessToken: String, val refreshToken: String)