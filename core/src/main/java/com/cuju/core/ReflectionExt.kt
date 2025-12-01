package com.cuju.core

import kotlin.reflect.KClass
import kotlin.reflect.full.allSupertypes

fun KClass<*>.getSuperclassTypeArgumentAsSimpleName(superclass: KClass<*>, index: Int = 0) =
    allSupertypes
        .first { it.classifier == superclass }.arguments[index].type?.classifier
        .let { it as? KClass<*> }?.simpleName
        ?: throw IllegalStateException("Cannot retrieve simple name")
