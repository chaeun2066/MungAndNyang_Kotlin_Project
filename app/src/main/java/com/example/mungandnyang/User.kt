package com.example.mungandnyang

data class User(
    var name: String,
    var email: String,
    var phone: String,
    var uId: String
){
    constructor(): this("","","","")
}
