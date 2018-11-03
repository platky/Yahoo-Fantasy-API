package io.github.platky.YahooFantasyAPI.data

import io.github.platky.YahooFantasyAPI.OAuth
import io.github.platky.YahooFantasyAPI.Requests
import io.github.platky.YahooFantasyAPI.getListXMLValues
import io.github.platky.YahooFantasyAPI.getXMLValue
import io.github.platky.YahooFantasyAPI.yahooToBoolean

enum class Sport(val code: String) {
    HOCKEY("nhl")
}

data class Game(
        val key: Int,
        val id: Int,
        val name: Sport,
        val code: String,
        val type: String,
        val url: String,
        val season: Int,
        val isRegistrationOver: Boolean,
        val isGameOver: Boolean,
        val isOffSeason: Boolean
)

internal fun createGameFromXML(xml: String) : Game {
    return Game(
            xml.getXMLValue("game_key").toInt(),
            xml.getXMLValue("game_id").toInt(),
            Sport.valueOf(xml.getXMLValue("name").toUpperCase()),
            xml.getXMLValue("code"),
            xml.getXMLValue("type"),
            xml.getXMLValue("url"),
            xml.getXMLValue("season").toInt(),
            xml.getXMLValue("is_registration_over").yahooToBoolean(),
            xml.getXMLValue("is_game_over").yahooToBoolean(),
            xml.getXMLValue("is_offseason").yahooToBoolean()
    )
}

internal fun createGamesFromXML(xmlBlocks: List<String>): List<Game> {
    return xmlBlocks.map { createGameFromXML(it) }
}

internal fun retrieveGame(oAuth: OAuth, sport: Sport): Game {
    val response = oAuth.sendRequest(Requests.getGame(sport))
    return createGameFromXML(response.body.getXMLValue("game"))
}

internal fun retrieveGames(oAuth: OAuth, gameKeys: List<String>): List<Game> {
    val response = oAuth.sendRequest(Requests.getGames(gameKeys))
    return createGamesFromXML(response.body.getListXMLValues("game"))
}