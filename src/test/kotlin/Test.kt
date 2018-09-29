import org.junit.Test
import java.util.*

class Test {

    //TODO remove these
    private val apiKey = "dj0yJmk9WVB3dTRhNEE5YXk5JmQ9WVdrOVZGZ3piRE5FTmpJbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD01OA--"
    private val secret = "acbe7d4e780aa74b0307efbb5999fc1321be149c"

    @Test
    fun mainTest() {
        val yahooFantasy = YahooFantasy(apiKey, secret)
        yahooFantasy.authorize()
    }
}