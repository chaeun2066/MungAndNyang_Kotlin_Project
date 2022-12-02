package com.example.mungandnyang

data class Message(
    var message: String?,
    var sendId: String?
){
    constructor():this("","")
}
