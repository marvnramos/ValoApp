package com.example.valoapp.data.models.agents

data class Agent(
    val uuid: String,
    val displayName: String,
    val description: String,
    val developerName: String,
    val characterTags: List<String>?,
    var displayIcon: String,
    val displayIconSmall: String,
    val bustPortrait: String,
    val fullPortrait: String,
    val fullPortraitV2: String,
    val killfeedPortrait: String,
    val background: String,
    val backgroundGradientColors: List<String>,
    val assetPath: String,
    val isFullPortraitRightFacing: Boolean,
    val isPlayableCharacter: Boolean,
    val isAvailableForTest: Boolean,
    val isBaseContent: Boolean,
    val role: Role?,
    val recruitmentData: Any?,
    val abilities: List<Ability>,
    val voiceLine: Any?
)