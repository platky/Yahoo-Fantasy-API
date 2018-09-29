object Requests {
    private const val ANONYMOUS_BASE = "https://fantasysports.yahooapis.com/fantasy/v2"
    const val BASE = "$ANONYMOUS_BASE/users;use_login=1"

    fun getGame(gameName: GameName) = "$ANONYMOUS_BASE/game/${gameName.code}"

    fun getUsersLeagues(gameName: GameName) = "$BASE/games;game_keys=${gameName.code}/leagues"
    fun getUsersTeams(gameName: GameName) = "$BASE/games;game_keys=${gameName.code}/teams"
}