package com.cuju.core

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class TickerFlowProvider {
    fun provideTickerFlow(periodMillis: Long) = tickerFlow(periodMillis)
}

fun tickerFlow(periodMillis: Long) = flow {
    while (true) {
        emit(Unit)
        delay(periodMillis)
    }
}
