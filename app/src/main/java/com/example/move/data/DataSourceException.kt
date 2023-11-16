package com.example.move.data

class DataSourceException(
    var code: Int,
    message: String,
    var details: List<String>?
) : Exception(message)