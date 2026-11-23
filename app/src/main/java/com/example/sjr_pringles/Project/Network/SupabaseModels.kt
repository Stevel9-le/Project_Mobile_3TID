package com.example.sjr_pringles.Project.Network

data class UserRequest(
    val nama: String,
    val no_hp: String,
    val username: String,
    val password: String
)

data class KeluhanItem(
    val id: Int? = null,
    val nomor_pc: String,
    val isi_keluhan: String,
    val status: String = "Terkirim"
)

data class BookingItem(
    val id: Int? = null,
    val nama_pemesan: String,
    val pc_target: String,
    val durasi_jam: Int,
    val total_harga: Int
)