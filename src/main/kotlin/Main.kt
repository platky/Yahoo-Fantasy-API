import java.util.*

//TODO remove these
private val apiKey = "dj0yJmk9WVB3dTRhNEE5YXk5JmQ9WVdrOVZGZ3piRE5FTmpJbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD01OA--"
private val secret = "acbe7d4e780aa74b0307efbb5999fc1321be149c"

fun main(args: Array<String>) {
    val at = PreferenceHelper.getProperty("accessToken") ?: ""
    val refresh = PreferenceHelper.getProperty("refreshToken") ?: ""

    val yahooFantasy = YahooFantasy(apiKey, secret)
    //println(PreferenceHelper.getProperty("token"))
    var response = yahooFantasy.startAuthentication(at, refresh)
    if (response is AuthorizationResult) {
        println(response.url)
        val scanner = Scanner(System.`in`)
        val input = scanner.nextLine()
        response = yahooFantasy.sendUserAuthenticationToken(input)
    }

    if (!response.isSuccessful())
        throw Exception("Not authorized")

    if (response is CodedAuthenticationResult) {
        println(response.accessToken)
        println(response.refreshToken)
    }

    val league = yahooFantasy.getUsersLeagues()
    val transactions = yahooFantasy.getLeaguesTransactions(league[0].key)
}