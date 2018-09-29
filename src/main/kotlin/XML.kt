const val YAHOO_DATE_FORMAT = "yyyy-MM-dd"

fun String.getXMLValue(key: String): String {
    val after = this.substringAfter(key.xmlKeyify())
    return after.substringBefore(key.xmlKeyifyNegated())
}

fun String.getListXMLValues(key: String): List<String> {
    val trimmed = key.xmlKeyify() + this.substringAfter(key.xmlKeyify()).substringBeforeLast(key.xmlKeyifyNegated()) + key.xmlKeyifyNegated()
    val splits = trimmed.split(key.xmlKeyify()).filter { it != "" }.map { key.xmlKeyify() + it }
    return splits
}

private fun String.xmlKeyify() = "<$this>"
private fun String.xmlKeyifyNegated() = "</$this>"

fun String.yahooToBoolean() = this.toBoolean() || this == "1"