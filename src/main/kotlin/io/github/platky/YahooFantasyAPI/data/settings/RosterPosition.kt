package io.github.platky.YahooFantasyAPI.data.settings

import io.github.platky.YahooFantasyAPI.YahooEnum
import io.github.platky.YahooFantasyAPI.getXMLValue

enum class Position(override val yahooName: String) : YahooEnum {
    CENTER("C"),
    LEFT_WING("LW"),
    RIGHT_WING("RW"),
    DEFENSE("D"),
    UTIL("Util"),
    GOALIE("G"),
    BENCH("BN"),
    INJURED_RESERVE("IR"),
    INJURED_RESERVE_PLUS("IR+");

    companion object {
        private val map = Position.values().associateBy(Position::yahooName)
        fun fromName(name: String): Position = map[name] ?: Position.BENCH //TODO dont default
    }
}

enum class PositionType(override val yahooName: String) : YahooEnum {
    PLAYER("P"),
    GOALIE("G");

    companion object {
        private val map = PositionType.values().associateBy(PositionType::yahooName)
        fun fromName(name: String): PositionType = map[name] ?: PositionType.PLAYER //TODO dont default
    }
}

//TODO there is a duplication of this
data class RosterPosition(
        val position: Position,
        val type: PositionType?,
        val count: Int
)

fun createRosterPositionsFromXML(xmlBlocks: List<String>): List<RosterPosition> {
    return xmlBlocks.map { createRosterPositionFromXML(it) }
}

fun createRosterPositionFromXML(xml: String): RosterPosition {
    val type = xml.getXMLValue("position_type", null)
    return RosterPosition(
            Position.fromName(xml.getXMLValue("position")),
            if (type != null) PositionType.fromName(type) else null,
            xml.getXMLValue("count").toInt()
    )
}