package com.cesar31.audiogeneratorclient.model

class Err(
    var type: String,
    var line: Int,
    var column: Int,
    var lexeme: String,
    var description: String
) {
    override fun toString(): String {
        return "Err{" +
                "type='" + type + '\'' +
                ", line=" + line +
                ", column=" + column +
                ", lexeme='" + lexeme + '\'' +
                ", description='" + description + '\'' +
                '}'
    }
}