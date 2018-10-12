import com.github.scribejava.apis.YahooApi20
import com.github.scribejava.core.builder.ServiceBuilder
import com.github.scribejava.core.model.*
import com.github.scribejava.core.oauth.OAuth20Service

class OAuth(private val apiKey: String, private val apiSecret: String) {
    private val service: OAuth20Service = ServiceBuilder(apiKey)
            .apiSecret(apiSecret)
            .callback(OAuthConstants.OOB)
            .build(YahooApi20.instance())

    private var accessToken: OAuth2AccessToken? = null

    internal fun startAuthentication(accessToken: String = "", refreshToken: String = "") : AuthenticationResult {
        if (accessToken == "")
            return AuthorizationResult(service.authorizationUrl)

        this.accessToken = OAuth2AccessToken(accessToken, "", 3600, refreshToken, "", "")
        val authenticationResult = finishAuthentication()
        if (authenticationResult.isSuccessful())
            return authenticationResult

        resetAuthentication()
        return startAuthentication()
    }

    internal fun sendUserToken(token: String) : AuthenticationResult {
        accessToken = service.getAccessToken(token)
        return finishAuthentication()
    }

    private fun finishAuthentication() : AuthenticationResult {
        val request = OAuthRequest(Verb.GET, Requests.BASE)
        service.signRequest(accessToken, request)
        val result = service.execute(request)
        return CodedAuthenticationResult(result.code, result.message,
                accessToken!!.accessToken, accessToken!!.refreshToken)
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