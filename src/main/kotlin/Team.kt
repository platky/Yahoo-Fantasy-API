

data class Team(val key: String,
                val id: Int,
                val name: String,
                val isOwnedByCurrentLogin: Boolean,
                val url: String,
                val logoUrl: String,
                /*val division: Int, missing?*/
                val waiverPriority: Int,
                val numberOfMoves: Int,
                val numberOfTrades: Int,
                val hasClinchedPlayoffs: Boolean, //missing?
                val scoringType: ScoringType,
                val hasDraftGrade: Boolean
) //TODO roster_adds

fun createTeamFromXML(xml: String): Team {
    return Team(
            xml.getXMLValue("team_key"),
            xml.getXMLValue("team_id").toInt(),
            xml.getXMLValue("name"),
            xml.getXMLValue("is_owned_by_current_login").yahooToBoolean(),
            xml.getXMLValue("url"),
            xml.getXMLValue("team_logos").getXMLValue("url"),
            xml.getXMLValue("waiver_priority").toInt(),
            xml.getXMLValue("number_of_moves").toInt(),
            xml.getXMLValue("number_of_trades").toInt(),
            xml.getXMLValue("has_clinched_playoffs", "0").yahooToBoolean(),
            ScoringType.fromName(xml.getXMLValue("scoring_type")),
            xml.getXMLValue("has_draft_grade").yahooToBoolean()
    )
}

fun createTeamsFromXML(xmlBlocks: List<String>): List<Team> {
    return xmlBlocks.map { createTeamFromXML(it) }
}