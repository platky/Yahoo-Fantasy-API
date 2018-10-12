package data

import OAuth
import Requests
import getListXMLValues
import getXMLValue

data class Transaction(
        val key: String,
        val id: Int,
        val type: String, //TODO Enum
        val status: String, //TODO Enum
        val timestamp: String,
        val players: List<Pair<Player, TransactionData>>?
)

internal fun createTransactionFromXML(xml: String): Transaction {
    val type = xml.getXMLValue("type")
    return Transaction(
            xml.getXMLValue("transaction_key"),
            xml.getXMLValue("transaction_id").toInt(),
            type,
            xml.getXMLValue("status"),
            xml.getXMLValue("timestamp"),
            if (type != "commish") {
                xml.getListXMLValues("player").map {
                    Pair(createPlayerFromXML(it), createTransactionDataFromXML(it.getXMLValue("transaction_data")))
                }
            } else {
                null
            }
    )
}

//TODO is relying on nullability the best choice?
data class TransactionData(
        val type: String, //TODO Enum
        val source: String, //TODO ENum
        val sourceTeamKey: String?,
        val destinationType: String, //TODO Enum
        val destinationTeam: String?
)

internal fun createTransactionDataFromXML(xml: String): TransactionData {
    return TransactionData(
            xml.getXMLValue("type"),
            xml.getXMLValue("source_type"),
            xml.getXMLValue("source_team_key", null),
            xml.getXMLValue("destination_type"),
            xml.getXMLValue("destination_team", null)
    )
}

internal fun createTransactionsFromXML(xmlBlocks: List<String>): List<Transaction> {
    return xmlBlocks.map { createTransactionFromXML(it) }
}

internal fun retrieveLeaguesTransactions(oAuth: OAuth, leagueKey: String): List<Transaction> {
    val response = oAuth.sendRequest(Requests.getTransactionsFromLeagues(listOf(leagueKey)))
    return createTransactionsFromXML(response.body.getListXMLValues("transaction"))
}

internal fun retrieveTransactions(oAuth: OAuth, transactionKeys: List<String>): List<Transaction> {
    val response = oAuth.sendRequest(Requests.getTransactions(transactionKeys))
    return createTransactionsFromXML(response.body.getListXMLValues("transaction"))
}