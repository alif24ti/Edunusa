package com.example.edunusa.progres

data class PencapaianModel(
    val section: String,
    val title: String,
    val body: String,
    val badgeText: String,
    val icon: Int,
    val iconBg: Int,
    val iconColor: Int,
    val isNew: Boolean = false
)
