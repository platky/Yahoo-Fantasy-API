package io.github.platky.YahooFantasyAPI.data

import io.github.platky.YahooFantasyAPI.OAuth
import io.github.platky.YahooFantasyAPI.Requests
import io.github.platky.YahooFantasyAPI.getListXMLValues
import io.github.platky.YahooFantasyAPI.getXMLValue


data class Standings(
    val teams: List<Pair<Team, TeamPoints>>
)

internal fun createStandingsFromXML(xml: String): Standings {
    return Standings(
            xml.getListXMLValues("team").map {
                Pair(createTeamFromXML(it),
                        createTeamPointsFromXML(it.getXMLValue("team_points"),
                                it.getXMLValue("team_standings")))
            }
    )
}

internal fun retrieveStandings(oAuth: OAuth, leagueKey: String): Standings {
    val response = oAuth.sendRequest(Requests.getStandingsFromLeague(leagueKey))
    println(response.body)
    return createStandingsFromXML(response.body.getXMLValue("standings"))
}