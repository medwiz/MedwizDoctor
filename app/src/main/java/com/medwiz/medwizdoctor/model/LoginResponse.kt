package com.medwiz.medwizdoctor.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
data class LoginResponse(
    val token: String,
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