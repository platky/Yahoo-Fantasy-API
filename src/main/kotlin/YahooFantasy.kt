import data.*

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

    fun startAuthentication(accessToken: String = "", refreshToken: String = "") =
            oAuth.startAuthentication(accessToken, refreshToken)
    fun sendUserAuthenticationToken(token: String) = oAuth.sendUserToken(token)
    fun resetAuthentication() = oAuth.resetAuthentication()
}