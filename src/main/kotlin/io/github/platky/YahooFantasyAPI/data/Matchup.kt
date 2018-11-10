package io.github.platky.YahooFantasyAPI.data

import io.github.platky.YahooFantasyAPI.YAHOO_DATE_FORMAT
import io.github.platky.YahooFantasyAPI.getListXMLValues
import io.github.platky.YahooFantasyAPI.getXMLValue
import io.github.platky.YahooFantasyAPI.yahooToBoolean
import java.text.SimpleDateFormat
import java.util.*


data class Matchup(
        val week: Int,
        val weekStart: Date,
        val weekEnd: Date,
        val status: String, //ENUM
        val isPlayOffs: Boolean,
        val isConsolation: Boolean,
        val teamStats : Map<Team, TeamStats>
)

internal fun createMatchupsFromXML(xmlBlocks: List<String>): List<Matchup> {
    return xmlBlocks.map {  createMatchupFromXML(it)  }
}

internal fun createMatchupFromXML(xml: String): Matchup {
    return Matchup(
            xml.getXMLValue("week").toInt(),
            SimpleDateFormat(YAHOO_DATE_FORMAT).parse(xml.getXMLValue("week_start")),
            SimpleDateFormat(YAHOO_DATE_FORMAT).parse(xml.getXMLValue("week_end")),
            xml.getXMLValue("status"),
            xml.getXMLValue("is_playoffs").yahooToBoolean(),
            xml.getXMLValue("is_consolation").yahooToBoolean(),
            xml.getListXMLValues("team").map {
                createTeamFromXML(it) to createTeamStatsFromXML(it.getXMLValue("team_stats"))
            }.toMap()
    )
}