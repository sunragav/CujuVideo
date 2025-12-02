package com.cuju.core

import org.koin.dsl.module
import java.text.SimpleDateFormat
import java.util.Locale

fun coreModule() = module {
    factory { TickerFlowProvider() }
}

fun getFormattedTimeStamp(timeMillis: Long, pattern: String): String =
    SimpleDateFormat(pattern, Locale.US)
        .format(timeMillis)

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
fun parseTimeStamp(formattedValue: String, pattern: String) =
    SimpleDateFormat(pattern, Locale.US).parse(formattedValue).time
