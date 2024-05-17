package com.example.societyapp

import android.app.Application
import com.example.societyapp.ui.data.SocietyDatabase

class SocietyApplication: Application() {
    val database: SocietyDatabase by lazy { SocietyDatabase.getDatabase(this) }
}

