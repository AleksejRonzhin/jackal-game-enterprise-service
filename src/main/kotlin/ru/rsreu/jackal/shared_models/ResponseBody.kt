package ru.rsreu.jackal.shared_models

interface ResponseBody<T: Enum<T>> {
    val responseStatus: T
}