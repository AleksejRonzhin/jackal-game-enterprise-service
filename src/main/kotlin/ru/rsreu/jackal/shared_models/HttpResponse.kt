package ru.rsreu.jackal.shared_models

open class HttpResponse(
    override val responseStatus: HttpResponseStatus
) : ResponseBody<HttpResponseStatus>

enum class HttpResponseStatus {
    OK,
    LOBBY_SERVICE_NOT_AVAILABLE,
    LOBBY_SERVICE_FAIL,
    USER_ALREADY_IN_LOBBY,
    NOT_UNIQUE_LOBBY_TITLE,
    USER_NOT_FOUND,
    USER_NOT_IN_LOBBY,
    USER_NOT_LOBBY_HOST,
    GAME_NOT_FOUND,
    LOBBY_NOT_FOUND,
    WRONG_PASSWORD,
    USER_IN_LOBBY_BLACK_LIST,
    LOBBY_IN_GAME,
    LOBBY_MEMBER_NOT_READY,
    LOBBY_MEMBERS_NOT_CONNECTED,
    LOBBY_ALREADY_GETTING_READY
}