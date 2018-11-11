package io.github.platky.YahooFantasyAPI

import com.github.scribejava.apis.YahooApi20
import com.github.scribejava.core.builder.ServiceBuilder
import com.github.scribejava.core.model.*
import com.github.scribejava.core.oauth.OAuth20Service
import javax.naming.AuthenticationException

data class AccessToken(
        val accessToken: String,
        val refreshToken: String,
        val secondsToExpiry: Int
)

internal class OAuth(private val apiKey: String, private val apiSecret: String) {
    private val service: OAuth20Service = ServiceBuilder(apiKey)
            .apiSecret(apiSecret)
            .callback(OAuthConstants.OOB)
            .build(YahooApi20.instance())

    private var accessToken: OAuth2AccessToken? = null

    /*
        Begin the authentication process and get an authorization url
     */
    internal fun startAuthentication(): String {
        return service.authorizationUrl
    }

    internal fun finishAuthentication(token: String): AccessToken {
        val localAccess = service.getAccessToken(token)
        return authenticate(localAccess)
    }

    internal fun refreshAccessToken(refreshToken: String): AccessToken {
        val result = service.refreshAccessToken(refreshToken)
        return AccessToken(result.accessToken, result.refreshToken, result.expiresIn)
    }

    private fun authenticate(accessToken: OAuth2AccessToken): AccessToken {
        val request = OAuthRequest(Verb.GET, Requests.BASE)
        service.signRequest(accessToken, request)
        val result = service.execute(request)
        if (result.code !in 200..399)
            throw AuthenticationException("Invalid authentication request: ${result.code}")

        return AccessToken(accessToken.accessToken, accessToken.refreshToken, accessToken.expiresIn)
    }

    internal fun setAccessToken(accessToken: String) {
        this.accessToken = OAuth2AccessToken(accessToken)
    }

    internal fun sendRequest(requestUrl: String): Response {
        val request = OAuthRequest(Verb.GET, requestUrl)
        service.signRequest(accessToken, request)

        return service.execute(request)
    }

    internal fun resetAuthentication() {
        accessToken = null
    }
}