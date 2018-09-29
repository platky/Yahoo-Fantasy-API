
enum class GameName(val code: String) {
    HOCKEY("nhl")
}

data class Game(
        val key: Int,
        val id: Int,
        val name: GameName,
        val code: String,
        val type: String,
        val url: String,
        val season: Int,
        val isRegistrationOver: Boolean,
        val isGameOver: Boolean,
        val isOffSeason: Boolean
)

fun createGameFromXML(xml: String) : Game {
    return Game(
            xml.getXMLValue("game_key").toInt(),
            xml.getXMLValue("game_id").toInt(),
            GameName.valueOf(xml.getXMLValue("name").toUpperCase()),
            xml.getXMLValue("code"),
            xml.getXMLValue("type"),
            xml.getXMLValue("url"),
            xml.getXMLValue("season").toInt(),
            xml.getXMLValue("is_registration_over").yahooToBoolean(),
            xml.getXMLValue("is_game_over").yahooToBoolean(),
            xml.getXMLValue("is_offseason").yahooToBoolean()
    )
}