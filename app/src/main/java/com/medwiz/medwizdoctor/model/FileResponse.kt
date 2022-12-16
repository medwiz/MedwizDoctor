package com.medwiz.medwizdoctor.model

data class FileResponse(
    val downloadUrl: String,
    val fileName: String,
    val fileSize: Int,
    val fileType: String
)