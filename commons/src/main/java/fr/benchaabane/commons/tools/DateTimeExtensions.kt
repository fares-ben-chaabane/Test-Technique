package fr.benchaabane.commons.tools

import org.threeten.bp.LocalDate
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.DateTimeFormatter.ISO_ZONED_DATE_TIME
import java.util.Locale

fun today() = Now.localDate()
fun yesterday() = today().minusDays(1)

fun String.toDate(formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE): LocalDate = LocalDate.parse(this, formatter)
fun String.toDateTime(formatter: DateTimeFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME): ZonedDateTime = ZonedDateTime.parse(this, formatter)
fun LocalDate.toShortDay(): String = format(FORMATTER_SHORT_DAY.withLocale(Locale.getDefault()))
fun ZonedDateTime.toJsonValue(): String = format(ISO_ZONED_DATE_TIME.withLocale(Locale.getDefault()))

private val FORMATTER_SHORT_DAY = DateTimeFormatter.ofPattern("EEE")
