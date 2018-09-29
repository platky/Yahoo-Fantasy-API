

class YahooFantasy(apiKey: String, apiSecret: String) {
    val oAuth = OAuth(apiKey, apiSecret)

    fun startAuthorization() = oAuth.startAuthorization()
    fun sendUserAuthorizationToken(token: String) = oAuth.sendUserToken(token)
    fun resetAuth() = oAuth.resetAuthentication()

    fun getGameData() {
        val response = oAuth.sendRequest(Requests.getGame(GameName.HOCKEY))
        println(response.code)
        println()
        println(response.body)
        var gameXML = "<game>" + response.body.substringAfter("<game>")
        gameXML = gameXML.substringBefore("</game>") + "</game>"
        val game = createGameFromXML(gameXML)
        println(game.toString())
    }

    fun populateLeagueData() {
        val response = oAuth.sendRequest(Requests.getUsersLeagues(GameName.HOCKEY))
        println(response.code)
        println()
        println(response.body)
        val allLeagueXML = response.body.getListXMLValues("league")
        val leagues = createLeaguesFromXML(allLeagueXML)
        println(leagues)
    }

    fun populateTeamData() {
        val response = oAuth.sendRequest(Requests.getUsersTeams(GameName.HOCKEY))
        println(response.code)
        println()
        println(response.body)
    }
}