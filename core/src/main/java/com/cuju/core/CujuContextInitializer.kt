package com.cuju.core

import android.content.Context
import androidx.startup.Initializer

lateinit var applicationContext: Context
    private set

object CujuContext

class CujuContextInitializer : Initializer<CujuContext> {
    override fun create(context: Context): CujuContext {
        applicationContext = context.applicationContext
        return CujuContext
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
