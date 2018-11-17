package io.github.platky.YahooFantasyAPI

import io.github.platky.YahooFantasyAPI.data.*
import io.github.platky.YahooFantasyAPI.data.settings.retrieveLeagueSettings

class YahooFantasy(apiKey: String, apiSecret: String) {
    private val oAuth = OAuth(apiKey, apiSecret)
    var sport: Sport = Sport.HOCKEY

    fun getGame(sport: Sport = this.sport): Game = retrieveGame(oAuth, sport)
    fun getGames(gameKeys: List<String>): List<Game> = retrieveGames(oAuth, gameKeys)

    fun getLeague(leagueKey: String): League = retrieveLeagues(oAuth, listOf(leagueKey))[0]
    fun getLeagues(leagueKeys: List<String>): List<League> = retrieveLeagues(oAuth, leagueKeys)
    fun getUsersLeagues(sport: Sport = this.sport): List<League> = retrieveUsersLeagues(oAuth, sport)

    fun getTeam(teamKey: String): Team = retrieveTeams(oAuth, listOf(teamKey))[0]
    fun getTeams(teamKeys: List<String>): List<Team> = retrieveTeams(oAuth, teamKeys)
    fun getUsersTeams(sport: Sport = this.sport): List<Team> = retrieveUsersTeams(oAuth, sport)
    fun getLeaguesTeams(leagueKey: String): List<Team> = retrieveLeaguesTeams(oAuth, leagueKey)

    fun getRoster(teamKey: String): Roster = retrieveRoster(oAuth, teamKey)

    fun getLeaguesPlayers(leagueKey: String): List<Player> = retrieveLeaguesPlayers(oAuth, leagueKey)
    fun getTeamsPlayers(teamKey: String): List<Player> = retrieveTeamsPlayers(oAuth, teamKey)
    fun getPlayer(playerKey: String): Player = retrievePlayers(oAuth, listOf(playerKey))[0]
    fun getPlayers(playerKeys: List<String>): List<Player> = retrievePlayers(oAuth, playerKeys)

    fun getLeaguesTransactions(leagueKey: String): List<Transaction> = retrieveLeaguesTransactions(oAuth, leagueKey)
    fun getTransaction(transactionKey: String): Transaction = retrieveTransactions(oAuth, listOf(transactionKey))[0]
    fun getTransactions(transactionKeys: List<String>): List<Transaction> = retrieveTransactions(oAuth, transactionKeys)

    fun getLeaguesScoreboard(leagueKey: String, week: Int? = null): Scoreboard = retrieveScoreboard(oAuth, leagueKey, week)
    fun getLeagueStandings(leagueKey: String): Standings = retrieveStandings(oAuth, leagueKey)
    //fun getLeagueSettings(leagueKey: String):

    fun startAuthentication() = oAuth.startAuthentication()
    fun finishAuthentication(userToken: String) = oAuth.finishAuthentication(userToken)
    fun refreshAccessToken(refreshToken: String) = oAuth.refreshAccessToken(refreshToken)
    fun setAccessToken(accessToken: String) = oAuth.setAccessToken(accessToken)


    //TODO remove once all stats are gathered
    internal fun getStats() = println(oAuth.sendRequest("https://fantasysports.yahooapis.com/fantasy/v2/game/nhl/stat_categories").body)
    internal fun getLeagueSettings(leagueKey: String) = retrieveLeagueSettings(oAuth, leagueKey)
}