package io.github.platky.YahooFantasyAPI.data.stats

import io.github.platky.YahooFantasyAPI.data.Sport


internal fun getStatById(id: Int, sport: Sport = Sport.HOCKEY): StatCategories  {
    return when(sport) {
        Sport.HOCKEY -> NHLStat.fromId(id) ?: throw IllegalArgumentException("StatCategories not found")
    }
}

interface StatCategories {
    val id: Int
    val statName: String
    val displayName: String
    val positions: List<Position>
    val order: StatOrder
}

enum class StatOrder {
    HIGHER,
    LOWER
}