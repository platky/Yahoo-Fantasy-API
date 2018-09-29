

data class Team(val key: String,
                val id: Int,
                val name: String,
                val isOwnedByCurrentLogin: Boolean,
                val url: String,
                val logoUrl: String,
                val division: Int,
                val waizerPriority: Int,
                val numberOfMoves: Int,
                val numberOfTrades: Int,
                val hasClinchedPlayoffs: Boolean,
                val scoringType: ScoringType,
                val hasDraftGrade: Boolean
)

fun createTeamFromXML(xml: String): Team {
    return Team(
            xml.getXMLValue("team_key"),
            xml.getXMLValue("team_id").toInt(),
            xml.getXMLValue("name"),
            xml.getXMLValue("isOwnedByCurrentLogin").yahooToBoolean(),
            xml.getXMLValue("url"),
            xml.getXMLValue("team_logos").getXMLValue("url"),
            xml.getXMLValue("waiver_priority"),
    )
}

fun createTeamsFromXML(xmlBlocks: List<String>): List<Team> {
    return xmlBlocks.map { createTeamFromXML(it) }
}