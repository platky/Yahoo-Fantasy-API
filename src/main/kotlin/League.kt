import java.text.SimpleDateFormat
import java.util.*

enum class ScoringType(val yahooName: String) {
    HEAD_TO_HEAD("head"),
    POINTS("points"); //TODO confirm

    companion object {
        private val map = ScoringType.values().associateBy(ScoringType::yahooName)
        fun fromName(name: String): ScoringType = map[name] ?: HEAD_TO_HEAD //TODO dont default
    }
}

enum class LeagueType(val yahooName: String) {
    PRIVATE("private");

    companion object {
        private val map = LeagueType.values().associateBy(LeagueType::yahooName)
        fun fromName(name: String): LeagueType = map[name] ?: PRIVATE //TODO dont default
    }
}

data class League(
        val key: String,
        val id: Int,
        val name: String,
        val url: String,
        val logoUrl: String,
        val draftStatus: String, //TODO use enum once values are known
        val numOfTeams: Int,
        val editKey: Date,
        val weeklyDeadline: String, //TODO enum?
        val scoreType: ScoringType,
        val leagueType: LeagueType,
        val renew: String,
        val irisGroupChatId: String, //What?
        val shortInvitationUrl: String,
        val allowAddToDlExtraPos: Boolean, //what?
        val isProLeague: Boolean,
        val isCashLeague: Boolean,
        val currentWeek: Int,
        val startWeek: Int,
        val startDate: Date,
        val endWeek: Int,
        val endDate: Date,
        val gameCode: String,
        val seasonYear: Int
)

fun createLeagueFromXML(xml: String): League {
    return League(
            xml.getXMLValue("league_key"),
            xml.getXMLValue("league_id").toInt(),
            xml.getXMLValue("name"),
            xml.getXMLValue("url"),
            xml.getXMLValue("logo_url"),
            xml.getXMLValue("draft_status"),
            xml.getXMLValue("num_teams").toInt(),
            SimpleDateFormat(YAHOO_DATE_FORMAT).parse(xml.getXMLValue("edit_key")),
            xml.getXMLValue("weekly_deadline"),
            ScoringType.fromName(xml.getXMLValue("scoring_type")),
            LeagueType.fromName(xml.getXMLValue("league_type")),
            xml.getXMLValue("renew"),
            xml.getXMLValue("iris_group_chat_id"),
            xml.getXMLValue("short_invitation_url"),
            xml.getXMLValue("allow_add_to_dl_extra_pos").yahooToBoolean(),
            xml.getXMLValue("is_pro_league").yahooToBoolean(),
            xml.getXMLValue("is_cash_league").yahooToBoolean(),
            xml.getXMLValue("current_week").toInt(),
            xml.getXMLValue("start_week").toInt(),
            SimpleDateFormat(YAHOO_DATE_FORMAT).parse(xml.getXMLValue("start_date")),
            xml.getXMLValue("end_week").toInt(),
            SimpleDateFormat(YAHOO_DATE_FORMAT).parse(xml.getXMLValue("end_date")),
            xml.getXMLValue("game_code"),
            xml.getXMLValue("season").toInt()
    )
}

fun createLeaguesFromXML(xmlBlocks: List<String>): List<League> {
    return xmlBlocks.map { createLeagueFromXML(it) }
}