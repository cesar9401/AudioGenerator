package com.cesar31.audiogeneratorclient.parser

class Token {
    var type: Int
    var value: String? = null
    var line: Int
    var column: Int

    constructor(type: Int, value: String?, line: Int, column: Int) {
        this.type = type
        this.value = value
        this.line = line
        this.column = column
    }

    constructor(type: Int, line: Int, column: Int) {
        this.type = type
        this.line = line
        this.column = column
    }

    override fun toString(): String {
        return "Token{" +
                "type=" + type +
                ", value='" + value + '\'' +
                ", line=" + line +
                ", column=" + column +
                '}'
    }
}