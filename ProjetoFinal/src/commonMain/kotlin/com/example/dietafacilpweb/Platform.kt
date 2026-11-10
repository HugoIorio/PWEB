package com.example.dietafacilpweb

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform