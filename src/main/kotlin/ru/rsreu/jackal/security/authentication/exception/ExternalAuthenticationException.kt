package ru.rsreu.jackal.security.authentication.exception

import ru.rsreu.jackal.security.authentication.service.provider.ExternalAuthenticationProviderType

class ExternalAuthenticationException(
    val externalAuthenticationProviderType: ExternalAuthenticationProviderType
) : RuntimeException()