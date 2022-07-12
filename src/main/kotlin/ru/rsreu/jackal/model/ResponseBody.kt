package ru.rsreu.jackal.model

interface ResponseBody<T: Enum<T>> {
    val responseStatus: T
}