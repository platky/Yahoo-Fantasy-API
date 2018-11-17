package io.github.platky.YahooFantasyAPI

import io.github.platky.YahooFantasyAPI.data.Sport

object Requests {
    private const val ANONYMOUS_BASE = "https://fantasysports.yahooapis.com/fantasy/v2"
    const val BASE = "$ANONYMOUS_BASE/users;use_login=1"

    fun getGame(sport: Sport) = "$ANONYMOUS_BASE/game/${sport.code}"
    fun getGames(gameKeys: List<String>) = "$ANONYMOUS_BASE/games;game_keys=${gameKeys.combine()}"

    fun getLeagues(leagueKeys: List<String>) = "$ANONYMOUS_BASE/leagues;league_keys=${leagueKeys.combine()}"
    fun getLeaguesForUser(sport: Sport) = "$BASE/games;game_keys=${sport.code}/leagues"

    fun getTeamsFromLeague(leagueKey: String) = "$ANONYMOUS_BASE/league/$leagueKey/teams"
    fun getTeams(teamKeys: List<String>) = "$ANONYMOUS_BASE/teams;${teamKeys.combine()}"
    fun getTeamsForUser(sport: Sport) = "$BASE/games;game_keys=${sport.code}/teams"

    fun getRoster(teamKey: String) = "$ANONYMOUS_BASE/team/$teamKey/roster/players"

    fun getPlayersFromLeagues(leagueKeys: List<String>) =
            "$ANONYMOUS_BASE/leagues;league_keys=${leagueKeys.combine()}/players"
    fun getPlayersFromTeams(teamKeys: List<String>) = "$ANONYMOUS_BASE/teams;team_keys=${teamKeys.combine()}/players"
    fun getPlayers(playerKeys: List<String>) = "$ANONYMOUS_BASE/players;player_keys=${playerKeys.combine()}"

    fun getTransactionsFromLeagues(leagueKeys: List<String>) =
            "$ANONYMOUS_BASE/leagues;league_keys=${leagueKeys.combine()}/transactions"
    fun getTransactions(transactionKeys: List<String>) =
            "$ANONYMOUS_BASE/transactions;transaction_keys=${transactionKeys.combine()}"

    fun getScoreboardFromLeague(leagueKey: String, week: Int?): String {
        val weekParameter = when(week) {
            null -> ""
            else -> ";week=$week"
        }
        return "$ANONYMOUS_BASE/league/$leagueKey/scoreboard$weekParameter"
    }

    fun getStandingsFromLeague(leagueKey: String) = "$ANONYMOUS_BASE/league/$leagueKey/standings"
    fun getSettingsFromLeague(leagueKey: String) = "$ANONYMOUS_BASE/league/$leagueKey/settings"

    private fun List<String>.combine() = this.joinToString(",")
}