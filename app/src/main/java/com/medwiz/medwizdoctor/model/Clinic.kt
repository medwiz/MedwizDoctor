package com.medwiz.medwizdoctor.model

/**
 * @Author: Prithwiraj Nath
 * @Date:13/03/23
 */
data class Clinic(
    val address1: String,
    val city: String,
    val country: String,
    val doctor: Doctor,
    val periods: List<Period>,
    val state: String,
    val zip: String
)

data class Doctor(
    val id: Int
)

data class Period(
    val dayOfVisit: String,
    val periodTimings: List<PeriodTiming>
)

data class PeriodTiming(
    val endTime: String,
    val periodName: String,
    val startTime: String
)