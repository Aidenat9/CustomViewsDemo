package me.tmgg.viewsdemoapp.dataFaultTolerant

class User{
    var name = ""
    var age = 0
    var languageStr = ""
    var languages = ArrayList<String>()
    override fun toString(): String {
        return """
            {
                "name":"${name}",
                "age":${age},
                "languages":${languages},
                "languageStr":${languageStr}
            }
        """.trimIndent()
    }
}