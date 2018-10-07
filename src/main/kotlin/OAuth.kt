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

    internal fun startAuthentication() : AuthenticationResult {
        if (accessToken != null) return CodedAuthenticationResult(200, "Already authenticated")

        val accessTokenRaw = PropertiesHelper.getProperty("accessToken")

        if (accessTokenRaw == null)
            return AuthorizationResult(service.authorizationUrl)

        accessToken = OAuth2AccessToken(accessTokenRaw)
        val authenticationResult = finishAuthentication()
        if (authenticationResult.isSuccessful())
            return authenticationResult

        resetAuthentication()
        return startAuthentication() //This pass will require user token
    }

    internal fun sendUserToken(token: String) : AuthenticationResult {
        accessToken = service.getAccessToken(token)
        PropertiesHelper.setProperty("token", token)
        PropertiesHelper.setProperty("rawResponse", accessToken!!.rawResponse) //TODO synchronize
        PropertiesHelper.setProperty("refreshToken", accessToken!!.refreshToken)
        PropertiesHelper.setProperty("accessToken", accessToken!!.accessToken)

        return finishAuthentication()
    }

    private fun finishAuthentication() : AuthenticationResult {
        val request = OAuthRequest(Verb.GET, Requests.BASE)
        service.signRequest(accessToken, request)
        val result = service.execute(request)
        return CodedAuthenticationResult(result.code, result.message)
    }

    fun sendRequest(requestUrl: String): Response {
        val request = OAuthRequest(Verb.GET, requestUrl)
        service.signRequest(accessToken, request)

        return service.execute(request)
    }

    internal fun resetAuthentication() {
        accessToken = null
        PropertiesHelper.clearProperty("token")
        PropertiesHelper.clearProperty("rawResponse")
        PropertiesHelper.clearProperty("accessToken")
    }
}