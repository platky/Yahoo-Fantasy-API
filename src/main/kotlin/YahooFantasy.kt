class YahooFantasy(apiKey: String, apiSecret: String) {
    private val oAuth = OAuth(apiKey, apiSecret)

    val game: Game by lazy {
        val response = oAuth.sendRequest(Requests.getGame(GameName.HOCKEY))
        createGameFromXML(response.body.getXMLValue("game"))
    }

    val usersLeagues: List<League> by lazy {
        val response = oAuth.sendRequest(Requests.getUsersLeagues(GameName.HOCKEY))
        createLeaguesFromXML(response.body.getListXMLValues("league"))
    }

    val usersTeams: List<Team> by lazy {
        val response = oAuth.sendRequest(Requests.getUsersTeams(GameName.HOCKEY))
        createTeamsFromXML(response.body.getListXMLValues("team"))
    }

    fun startAuthentication() = oAuth.startAuthentication()
    fun sendUserAuthenticationToken(token: String) = oAuth.sendUserToken(token)
    fun resetAuthentication() = oAuth.resetAuthentication()
}