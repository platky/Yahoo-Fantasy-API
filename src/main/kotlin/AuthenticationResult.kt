interface AuthenticationResult {
    val code: Int
    fun isSuccessful() : Boolean = code in 200..399
}

data class AuthorizationResult(val url: String) : AuthenticationResult {
    override val code: Int
        get() = 0
}

data class CodedAuthenticationResult(override val code: Int, val message: String) : AuthenticationResult
