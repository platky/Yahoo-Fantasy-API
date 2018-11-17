import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class BasicTest : TestBase() {

    @Test
    fun getSingleGame() {
        val game = yahooFantasy.getGame()
        println(game)
    }

    @Test
    fun getScoreboard() {
        val leagues = yahooFantasy.getUsersLeagues()
        assertTrue(leagues.isNotEmpty())
        val scoreboard = yahooFantasy.getLeaguesScoreboard(leagues[0].key)
    }

    @Test
    fun getStandings() {
        val leagues = yahooFantasy.getUsersLeagues()
        assertTrue(leagues.isNotEmpty())
        val standings = yahooFantasy.getLeagueStandings(leagues[0].key)
    }

    @Test
    fun getSettings() {
        val leagues = yahooFantasy.getUsersLeagues()
        assertTrue(leagues.isNotEmpty())
        val settings = yahooFantasy.getLeagueSettings(leagues[0].key)
    }
}