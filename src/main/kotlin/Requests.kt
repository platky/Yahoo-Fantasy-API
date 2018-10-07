object Requests {
    private const val ANONYMOUS_BASE = "https://fantasysports.yahooapis.com/fantasy/v2"
    const val BASE = "$ANONYMOUS_BASE/users;use_login=1"

    fun getGame(gameName: GameName) = "$ANONYMOUS_BASE/game/${gameName.code}"

    fun getLeagues(leagueKeys: List<String>) {
        val keys: String = leagueKeys.joinToString(",")
        "$ANONYMOUS_BASE/leagues;league_keys=$keys"
    }
    fun getUsersLeagues(gameName: GameName) = "$BASE/games;game_keys=${gameName.code}/usersLeagues"

    fun getTeamsFromLeague(leagueKey: String) = "$ANONYMOUS_BASE/league/$leagueKey/teams"
    fun getUsersTeams(gameName: GameName) = "$BASE/games;game_keys=${gameName.code}/usersTeams"
}