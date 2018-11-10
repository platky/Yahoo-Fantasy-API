package io.github.platky.YahooFantasyAPI.data

import io.github.platky.YahooFantasyAPI.OAuth
import io.github.platky.YahooFantasyAPI.Requests
import io.github.platky.YahooFantasyAPI.getListXMLValues
import io.github.platky.YahooFantasyAPI.getXMLValue
import io.github.platky.YahooFantasyAPI.yahooToBoolean

data class Team(val key: String,
                val id: Int,
                val name: String,
                val isOwnedByCurrentLogin: Boolean,
                val url: String,
                val logoUrl: String,
                /*val division: Int, missing?*/
                val waiverPriority: Int,
                val numberOfMoves: Int,
                val numberOfTrades: Int,
                val hasClinchedPlayoffs: Boolean, //missing?
                val scoringType: ScoringType,
                val hasDraftGrade: Boolean
) //TODO roster_adds

fun createTeamFromXML(xml: String): Team {
    return Team(
            xml.getXMLValue("team_key"),
            xml.getXMLValue("team_id").toInt(),
            xml.getXMLValue("name"),
            xml.getXMLValue("is_owned_by_current_login").yahooToBoolean(),
            xml.getXMLValue("url"),
            xml.getXMLValue("team_logos").getXMLValue("url"),
            xml.getXMLValue("waiver_priority").toInt(),
            xml.getXMLValue("number_of_moves").toInt(),
            xml.getXMLValue("number_of_trades").toInt(),
            xml.getXMLValue("has_clinched_playoffs", "0")!!.yahooToBoolean(),
            ScoringType.fromName(xml.getXMLValue("scoring_type")),
            xml.getXMLValue("has_draft_grade").yahooToBoolean()
    )
}

internal fun createTeamsFromXML(xmlBlocks: List<String>): List<Team> {
    return xmlBlocks.map { createTeamFromXML(it) }
}

internal fun retrieveTeams(oAuth: OAuth, teamKeys: List<String>): List<Team> {
    val response = oAuth.sendRequest(Requests.getTeams(teamKeys))
    return createTeamsFromXML(response.body.getListXMLValues("team"))
}

internal fun retrieveUsersTeams(oAuth: OAuth, sport: Sport): List<Team> {
    val response = oAuth.sendRequest(Requests.getTeamsForUser(sport))
    return createTeamsFromXML(response.body.getListXMLValues("team"))
}

internal fun retrieveLeaguesTeams(oAuth: OAuth, leagueKey: String): List<Team> {
    val response = oAuth.sendRequest(Requests.getTeamsFromLeague(leagueKey))
    return createTeamsFromXML(response.body.getListXMLValues("team"))
}

data class TeamPoints(
        val coverageType: String, //ENUM
        val season: Int,
        val totalPoints: Int,
        val rank: Int,
        val playoffSeed: Int,
        val wins: Int,
        val losses: Int,
        val ties: Int,
        val percentage: Float
)

internal fun createTeamPointsFromXML(pointsXML: String, standingsXML: String): TeamPoints {
    return TeamPoints(
            pointsXML.getXMLValue("coverage_type"),
            pointsXML.getXMLValue("season").toInt(),
            pointsXML.getXMLValue("total").toInt(),
            standingsXML.getXMLValue("rank").toInt(),
            standingsXML.getXMLValue("playoff_seed").toInt(),
            standingsXML.getXMLValue("wins").toInt(),
            standingsXML.getXMLValue("losses").toInt(),
            standingsXML.getXMLValue("ties").toInt(),
            standingsXML.getXMLValue("percentage").toFloat()
    )
}