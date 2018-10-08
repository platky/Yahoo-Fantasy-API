object Requests {
    private const val ANONYMOUS_BASE = "https://fantasysports.yahooapis.com/fantasy/v2"
    const val BASE = "$ANONYMOUS_BASE/users;use_login=1"

    fun getGame(gameName: GameName) = "$ANONYMOUS_BASE/game/${gameName.code}"

    //TODO offer multiple input types instead of just list. vararg, or singular
    fun getLeagues(leagueKeys: List<String>) = "$ANONYMOUS_BASE/leagues;league_keys=${leagueKeys.joinToString(",")}"

    fun getLeagueForUser(gameName: GameName) = "$BASE/games;game_keys=${gameName.code}/usersLeagues"

    fun getTeamsFromLeague(leagueKey: String) = "$ANONYMOUS_BASE/league/$leagueKey/teams"
    fun getTeams(teamKeys: List<String>) = "$ANONYMOUS_BASE/teams;${teamKeys.joinToString(",")}"
    fun getTeamsForUser(gameName: GameName) = "$BASE/games;game_keys=${gameName.code}/usersTeams"

    fun getRoster(teamKey: String) = "$ANONYMOUS_BASE/team/$teamKey/roster/players"

    fun getPlayersFromLeagues(leagueKeys: List<String>) = "$ANONYMOUS_BASE/league/${leagueKeys.joinToString(",")}/players"
    fun getPlayersFromTeam
    fun getPlayers
}