package io.github.platky.YahooFantasyAPI.data

import io.github.platky.YahooFantasyAPI.OAuth
import io.github.platky.YahooFantasyAPI.Requests
import io.github.platky.YahooFantasyAPI.YAHOO_DATE_FORMAT
import io.github.platky.YahooFantasyAPI.getListXMLValues
import io.github.platky.YahooFantasyAPI.getXMLValue
import io.github.platky.YahooFantasyAPI.yahooToBoolean
import java.text.SimpleDateFormat
import java.util.*

data class Roster(
        val coverageType: String, //Enum
        val date: Date,
        val isEditable: Boolean,
        val players: List<Player>
)

internal fun createRosterFromXML(xml: String): Roster {
    return Roster(
            xml.getXMLValue("coverage_type"),
            SimpleDateFormat(YAHOO_DATE_FORMAT).parse(xml.getXMLValue("date")),
            xml.getXMLValue("is_editable").yahooToBoolean(),
            createPlayersFromXML(xml.getListXMLValues("player"))
    )
}

internal fun retrieveRoster(oAuth: OAuth, teamKey: String): Roster {
    val response = oAuth.sendRequest(Requests.getRoster(teamKey))
    return createRosterFromXML(response.body.getXMLValue("roster"))
}