package io.github.platky.YahooFantasyAPI.data

import io.github.platky.YahooFantasyAPI.data.stats.StatCategory
import io.github.platky.YahooFantasyAPI.data.stats.getStatById
import io.github.platky.YahooFantasyAPI.getListXMLValues
import io.github.platky.YahooFantasyAPI.getXMLValue

data class Stat(
        val id: Int,
        val stat: StatCategory,
        val value: Float?
)

internal fun createStatsFromXML(xmlBlocks: List<String>): List<Stat> {
    return xmlBlocks.map { createStatFromXML(it) }
}

internal fun createStatFromXML(xml: String): Stat {
    val id = xml.getXMLValue("stat_id").toInt()
    return Stat(
            id,
            getStatById(id),
            xml.getXMLValue("value", null)?.toFloat()
    )
}

data class TeamStats(
        val coverageType: String, //ENUM
        val stats: List<Stat>
)

internal fun createTeamStatsFromXML(xml: String): TeamStats {
    return TeamStats(
            xml.getXMLValue("coverage_type"),
            createStatsFromXML(xml.getListXMLValues("stat"))
    )
}