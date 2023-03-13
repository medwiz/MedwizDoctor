package com.medwiz.medwiz.model

import androidx.annotation.NonNull

data class Doctors(
    val about: String,
    val age: Int,
    val certificateFile: Any,
    val certificateUrl: String,
    val createdAt: String,
    val credit: String,
    val currency: Any,
    val experience: Int,
    val fees: Double,
    val id: Int,
    val isApproved: Boolean,
    val isUpdated: Boolean,
    val pinCode: String,
    val specialization: String,
    val updatedAt: String,
    val user: User
)

data class User(
    val address: Address,
    val createdAt: String,
    val email: String,
    val firstName: String,
    val gender: String,
    val id: Int,
    val imageUrl: String,
    val lastName: String,
    val roles: List<Role>,
    val updatedAt: String,
    val userPhoneNumber: String
)

data class Address(
    val address1: String,
    val address2: String,
    val city: String,
    val country: String,
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val state: String,
    val zip: String
)

data class Role(
    val id: Int,
    val roleName: String
)