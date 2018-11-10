package io.github.platky.YahooFantasyAPI.data

import io.github.platky.YahooFantasyAPI.OAuth
import io.github.platky.YahooFantasyAPI.Requests
import io.github.platky.YahooFantasyAPI.getListXMLValues
import io.github.platky.YahooFantasyAPI.getXMLValue


data class Scoreboard(
        val week : Int,
        val matchups : List<Matchup>
)


internal fun createScoreboardFromXML(xml: String): Scoreboard {
    return Scoreboard(
            xml.getXMLValue("week").toInt(),
            createMatchupsFromXML(xml.getListXMLValues("matchup"))
    )
}

internal fun retrieveScoreboard(oAuth: OAuth, leagueKey: String, week: Int?): Scoreboard {
    val response = oAuth.sendRequest(Requests.getScoreboardFromLeague(leagueKey, week))
    return createScoreboardFromXML(response.body.getXMLValue("scoreboard"))
}
