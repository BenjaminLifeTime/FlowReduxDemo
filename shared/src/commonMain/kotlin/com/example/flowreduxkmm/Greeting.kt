package com.example.flowreduxkmm

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}