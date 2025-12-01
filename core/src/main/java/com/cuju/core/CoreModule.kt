package com.cuju.core

import org.koin.dsl.module

fun coreModule() = module {
    factory { TickerFlowProvider() }
}
