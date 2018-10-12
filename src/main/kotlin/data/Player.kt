package data

import OAuth
import Requests
import getListXMLValues
import getXMLValue
import yahooToBoolean

data class Player (
        val key: String,
        val id: Int?,
        val firstName: String,
        val lastName: String,
        val status: String, //TODO Enum
        val statusFull: String,
        val isOnDisabledList: Boolean,
        val editorialPlayerKey: String,
        val editorialTeamKey: String,
        val teamName: String,
        val teamAbbreviation: String,
        val uniformNumber: Int?,
        val position: String, //TODO Enum
        val headshotUrl: String,
        val isDroppable: Boolean, //Negated from Yahoo
        val positionType: String, //TODO enum
        val eligiblePositions: Set<String>, //TODO enum
        val hasNotes: Boolean,
        val hasRecentNotes: Boolean,
        val selectedPosition: String, //TODO enum (do we need the other data?)
        val isEditable: Boolean
)

internal fun createPlayerFromXML(xml: String): Player {
    return Player(
            xml.getXMLValue("player_key"),
            xml.getXMLValue("player_id", null)?.toInt(),
            xml.getXMLValue("first"),
            xml.getXMLValue("last"),
            xml.getXMLValue("status"),
            xml.getXMLValue("status_full"),
            xml.getXMLValue("on_disabled_list").yahooToBoolean(),
            xml.getXMLValue("editorial_player_key"),
            xml.getXMLValue("editorial_team_key"),
            xml.getXMLValue("editorial_team_full_name"),
            xml.getXMLValue("editorial_team_abbr"),
            xml.getXMLValue("uniform_number", null)?.toInt(),
            xml.getXMLValue("display_position"),
            xml.getXMLValue("image_url"),
            !xml.getXMLValue("is_undroppable").yahooToBoolean(),
            xml.getXMLValue("position_type"),
            xml.getXMLValue("eligible_positions").getListXMLValues("position").toSet(),
            xml.getXMLValue("has_player_notes").yahooToBoolean(),
            xml.getXMLValue("has_recent_player_notes").yahooToBoolean(),
            xml.getXMLValue("selected_position").getXMLValue("position"),
            xml.getXMLValue("is_editable").yahooToBoolean()
    )
}

internal fun createPlayersFromXML(xmlBlocks: List<String>): List<Player> {
    return xmlBlocks.map { createPlayerFromXML(it) }
}

//TODO multi league retrieval (probably stored in map)
internal fun retrieveLeaguesPlayers(oAuth: OAuth, leagueKey: String): List<Player> {
    val response = oAuth.sendRequest(Requests.getPlayersFromLeagues(listOf(leagueKey)))
    return createPlayersFromXML(response.body.getListXMLValues("player"))
}

internal fun retrieveTeamsPlayers(oAuth: OAuth, teamKey: String): List<Player> {
    val response = oAuth.sendRequest(Requests.getPlayersFromTeams(listOf(teamKey)))
    return createPlayersFromXML(response.body.getListXMLValues("player"))
}

internal fun retrievePlayers(oAuth: OAuth, playerKeys: List<String>): List<Player> {
    val response = oAuth.sendRequest(Requests.getPlayers(playerKeys))
    return createPlayersFromXML(response.body.getListXMLValues("player"))
}

