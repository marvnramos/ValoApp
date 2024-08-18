package com.example.valoapp.utils

fun hexToColorInt(hex: String): Int {
    return hex.toLong(16).toInt()
}