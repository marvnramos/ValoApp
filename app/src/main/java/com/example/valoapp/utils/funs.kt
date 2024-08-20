package com.example.valoapp.utils

import com.example.valoapp.data.models.Agent
import java.util.Locale

fun hexToColorInt(hex: String): Int {
    return hex.toLong(16).toInt()
}

fun matchesSearchCriteria(agent: Agent, query: String): Boolean {
    val lowercasedQuery = query.lowercase(Locale.getDefault())
    return lowercasedQuery == agent.displayName.lowercase(Locale.getDefault()) ||
            lowercasedQuery == agent.role?.displayName?.lowercase(Locale.getDefault())
}