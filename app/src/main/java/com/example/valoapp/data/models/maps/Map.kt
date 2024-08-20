package com.example.valoapp.data.models.maps

data class Map(
    val uuid: String,
    val displayName: String,
    val narrativeDescription: String? = null,
    val tacticalDescription: String,
    val coordinates: String,
    val displayIcon: String,
    val listViewIcon: String,
    val listViewIconTall: String,
    val splash: String,
    val stylizedBackgroundImage: String,
    val premierBackgroundImage: String,
    val assetPath: String,
    val mapUrl: String,
    val xMultiplier: Float,
    val yMultiplier: Float,
    val xScalarToAdd: Float,
    val yScalarToAdd: Float,
    val callouts: List<Callout>
)