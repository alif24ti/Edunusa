package com.example.edunusa.home

import java.io.Serializable

data class MateriModel(
    val subject: String,
    val modulInfo: String,
    val title: String,
    val desc: String,
    val duration: String,
    val level: String,
    val rating: Double,
    val progress: Int,
    val badgeBg: Int = 0,
    val badgeColor: Int = 0,
    val imageUrl: String = ""
) : Serializable
