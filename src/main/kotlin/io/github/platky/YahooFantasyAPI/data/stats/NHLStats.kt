package io.github.platky.YahooFantasyAPI.data.stats

import io.github.platky.YahooFantasyAPI.data.stats.Position.*
import io.github.platky.YahooFantasyAPI.data.stats.StatOrder.HIGHER
import io.github.platky.YahooFantasyAPI.data.stats.StatOrder.LOWER


enum class NHLStat(
        override val id: Int,
        override val statName: String,
        override val displayName: String,
        override val positions: List<Position>,
        override val order: StatOrder = HIGHER
) : StatCategory{

    GAMES_PLAYED(0, "Games Played", "GP", listOf(Player, Goalie)),
    GOALS(1, "Goals", "G", listOf(Player)),
    ASSISTS(2, "Assists", "A", listOf(Player)),
    POINTS(3, "Points", "P", listOf(Player)),
    PLUS_MINUS(4, "Plus/Minus", "+/-", listOf(Player)),
    PENALTY_MINUTES(5, "Penalty Minutes", "PIM", listOf(Player)),
    POWERPLAY_GOALS(6, "Powerplay Goals", "PPG", listOf(Player)),
    POWERPLAY_ASSISTS(7, "Powerplay Assists", "PPA", listOf(Player)),
    POWERPLAY_POINTS(8, "Powerplay Points", "PPP", listOf(Player)),
    SHORTHANDED_GOALS(9, "Shorthanded Goals", "SHG", listOf(Player)),
    SHORTHANDED_ASSISTS(10, "Shorthanded Assists", "SHA", listOf(Player)),
    SHORTHANDED_POINTS(11, "Shorthanded Points", "SHP", listOf(Player)),
    GAME_WINNING_GOALS(12, "Game-Winning Goals", "GWG", listOf(Player)),
    GAME_TYING_GOALS(13, "Game-Tying Goals", "GTG", listOf(Player)),
    SHOTS_ON_GOAL(14, "Shots on Goal", "SOG", listOf(Player)),
    SHOOTING_PERCENTAGE(15, "Shooting Percentage", "SH%", listOf(Player)),
    FACEOFFS_WON(16, "Faceoffs Won", "FW", listOf(Player)),
    FACEOFFS_LOST(17, "Faceoffs Lost", "FL", listOf(Player)), //TODO lower?
    GAMES_STARTED(18, "Games Started", "GS", listOf(Goalie)),
    WINS(19, "Wins", "W", listOf(Goalie)),
    LOSSES(20, "Losses", "L", listOf(Goalie), LOWER), //TODO lower?
    TIES(21, "Ties", "T", listOf(Goalie)), //TODO lower?
    GOALS_AGAINST(22, "Goals Against", "GA", listOf(Goalie), LOWER),
    GOALS_AGAINST_AVERAGE(23, "Goals Against Average", "GAA", listOf(Goalie), LOWER),
    SHOTS_AGAINST(24, "Shots Against", "SA", listOf(Goalie)),
    SAVES(25, "Saves", "SV", listOf(Goalie)),
    SAVE_PERCENTAGE(26, "Save Percentage", "SV%", listOf(Goalie)),
    SHUTOUTS(27, "Shutouts", "SHO", listOf(Goalie)),
    GOALIE_TIME_ON_ICE(28, "Time on Ice", "TOI", listOf(Goalie)),
    PLAYER_GAMES(29, "F/D Games", "GP", listOf(Player)),
    GOALIE_GAMES(30, "Goalie Games", "GP", listOf(Goalie)),
    HITS(31, "Hits", "HIT", listOf(Player)),
    BLOCKS(32, "Blocks", "BLK", listOf(Player)),
    TIME_ON_ICE(33, "Time on Ice", "TOI", listOf()),
    AVERAGE_TIME_ON_ICE(34, "Average Time on Ice", "TOI/G", listOf());

    companion object {
        private val idMap = NHLStat.values().associateBy(NHLStat::id)

        fun fromId(id: Int) = idMap[id]
    }
}

enum class Position {
    Player,
    Goalie
}