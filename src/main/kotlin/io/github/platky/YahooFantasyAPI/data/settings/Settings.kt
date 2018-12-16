package io.github.platky.YahooFantasyAPI.data.settings

import io.github.platky.YahooFantasyAPI.*
import io.github.platky.YahooFantasyAPI.data.League
import io.github.platky.YahooFantasyAPI.data.ScoringType
import io.github.platky.YahooFantasyAPI.data.createLeaguesFromXML
import io.github.platky.YahooFantasyAPI.data.stats.StatCategory
import io.github.platky.YahooFantasyAPI.data.stats.getStatById
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

enum class DraftType(val yahooName: String) {
    LIVE("live");

    companion object {
        private val map = DraftType.values().associateBy(DraftType::yahooName)
        fun fromName(name: String): DraftType = map[name] ?: DraftType.LIVE //TODO dont default
    }
}

enum class TradeRatifyType(val yahooName: String) {
    VOTE("vote");

    companion object {
        private val map = TradeRatifyType.values().associateBy(TradeRatifyType::yahooName)
        fun fromName(name: String): TradeRatifyType = map[name] ?: TradeRatifyType.VOTE //TODO dont default
    }
}

data class Settings(
        val draftType: DraftType,
        val isAuctionDraft: Boolean,
        val scoringType: ScoringType,
        val persistentUrl: String,
        val usesPlayoffs: Boolean,
        val hasPlayoffConsolationGames: Boolean,
        val playoffStartWeek: Int,
        val usesPlayoffReseeding: Boolean,
        val usesLockEliminatedTeams: Boolean,
        val numberOfPlayoffTeams: Int,
        val numberOfPlayoffConsolationTeams: Int,
        val hasMultiWeekChampionship: Boolean,
        val usesRosterImport: Boolean,
        val rosterImportDeadline: Date?,
        val waiverType: String, //switch to enum
        val waiverRule: String, //enum
        val usesFaab: Boolean,
        val draftTime: Date,
        val timePerDraftPick: Int,
        val postDraftPlayers: String, //enum
        val maxTeams: Int,
        val waiverTime: Int,
        val tradeEndDate: Date,
        val tradeRatifyType: TradeRatifyType,
        val tradeRejectTime: Int,
        val playerPool: String, //enum
        val cantCutList: String, //enum
        val isPubliclyViewable: Boolean,
        val canTradeDraftPicks: Boolean,
        val rosterPositions: List<RosterPosition>,
        val statCategories: List<StatCategory>,
        val maxWeeklyAdds: Int,
        val minGoalieGamesPlayed: Int
)

fun createSettingsFromXML(xml: String): Settings {
    return Settings(
            DraftType.fromName(xml.getXMLValue("draft_type")),
            xml.getXMLValue("is_auction_draft").yahooToBoolean(),
            ScoringType.fromName(xml.getXMLValue("scoring_type")),
            xml.getXMLValue("persistent_url"),
            xml.getXMLValue("uses_playoff").yahooToBoolean(),
            xml.getXMLValue("has_playoff_consolation_games").yahooToBoolean(),
            xml.getXMLValue("playoff_start_week").toInt(),
            xml.getXMLValue("uses_playoff_reseeding").yahooToBoolean(),
            xml.getXMLValue("uses_lock_eliminated_teams").yahooToBoolean(),
            xml.getXMLValue("num_playoff_teams").toInt(),
            xml.getXMLValue("num_playoff_consolation_teams").toInt(),
            xml.getXMLValue("has_multiweek_championship").yahooToBoolean(),
            xml.getXMLValue("uses_roster_import").yahooToBoolean(),
            xml.getXMLValue("roster_import_deadline").yahooToDate(),
            xml.getXMLValue("waiver_type"),
            xml.getXMLValue("waiver_rule"),
            xml.getXMLValue("uses_faab").yahooToBoolean(),
            Date(xml.getXMLValue("draft_time").toLong()),
            xml.getXMLValue("draft_pick_time").toInt(),
            xml.getXMLValue("post_draft_players"),
            xml.getXMLValue("max_teams").toInt(),
            xml.getXMLValue("waiver_time").toInt(),
            SimpleDateFormat(YAHOO_DATE_FORMAT).parse(xml.getXMLValue("trade_end_date")),
            TradeRatifyType.fromName(xml.getXMLValue("trade_ratify_type")),
            xml.getXMLValue("trade_reject_time").toInt(),
            xml.getXMLValue("player_pool"),
            xml.getXMLValue("cant_cut_list"),
            xml.getXMLValue("is_publicly_viewable").yahooToBoolean(),
            xml.getXMLValue("can_trade_draft_picks").yahooToBoolean(),
            createRosterPositionsFromXML(xml.getListXMLValues("roster_position")),
            xml.getXMLValue("stat_categories").getListXMLValues("stat").filter {
                it.getXMLValue("is_only_display_stat", null) == null
            }.map {
                getStatById(it.getXMLValue("stat_id").toInt())
            },
            xml.getXMLValue("max_weekly_adds").toInt(),
            xml.getXMLValue("min_games_played").toInt()
    )
}


internal fun retrieveLeagueSettings(oAuth: OAuth, leagueKey: String): Settings {
    val response = oAuth.sendRequest(Requests.getSettingsFromLeague(leagueKey))
    return createSettingsFromXML(response.body.getXMLValue("settings"))
}