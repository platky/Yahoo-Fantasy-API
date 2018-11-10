import io.github.platky.YahooFantasyAPI.YahooFantasy
import org.junit.jupiter.api.BeforeAll
import utils.PropertiesReader
import java.io.File
import java.util.*

open class TestBase {
    companion object {
        private val apiKey = PropertiesReader.getProperty("apiKey")
        private val secret = PropertiesReader.getProperty("secret")
        val yahooFantasy = YahooFantasy(apiKey, secret)

        @BeforeAll
        @JvmStatic
        fun authenticate() {
            val accessToken = readToken()

            if(accessToken == null)
                manuallyAuthenticate()
            else
                automaticallyAuthenticate(accessToken)
        }


        private fun manuallyAuthenticate() {
            val authUrl = yahooFantasy.startAuthentication()
            println(authUrl)
            println("Enter the token: ")
            val scanner = Scanner(System.`in`)
            val input = scanner.nextLine()

            val response = yahooFantasy.finishAuthentication(input)
            yahooFantasy.setAccessToken(response.accessToken)
            storeToken(response.accessToken)
        }

        private fun automaticallyAuthenticate(accessToken: String) {
            yahooFantasy.setAccessToken(accessToken)
        }

        private fun storeToken(token: String) {
            File("token.txt").writeText(token)
        }

        private fun readToken(): String? {
            try {
                val lines = File("token.txt").readLines()
                if (lines.isNotEmpty())
                    return lines[0]
            } catch(e: Exception) {
                return null
            }
            return null
        }
    }
}