package me.tmgg.viewsdemoapp.dataFaultTolerant

import com.google.gson.GsonBuilder


/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/5/6 10:12
 * package：me.tmgg.viewsdemoapp.dataFaultTolerant
 * version：1.0
 * <p>description： registerTypeAdapter() 方法，要求我们传递一个明确的类型，也就是说它不支持继承，而 registerTypeHierarchyAdapter() 则可以支持继承。             </p>
 */
fun main() {
//    intDefault0()
//    listDefaultEmpty()
//    userGsonStr()
}


fun intDefault0() {
    val jsonStr = """
        {
            "name":"承香墨影",
            "age":""
        }
    """.trimIndent()
    val user = GsonBuilder()
            .registerTypeAdapter(
                    Int::class.java,
                    IntDefaut0Adapter())
            .create()
            .fromJson<User>(jsonStr, User::class.java)
    print("user:${user.toString()}")
}

fun listDefaultEmpty() {
    val jsonStr = """
        {
            "name":"承香墨影",
            "age":"xx",
         "languages":["CN","EN"]
        }
    """.trimIndent()
    val user = GsonBuilder()
            .registerTypeAdapter(Int::class.java,IntDefaut0Adapter())
            .registerTypeHierarchyAdapter(
                    List::class.java,
                    ArraySecurityAdapter())
            .create()
            .fromJson<User>(jsonStr, User::class.java)
    print("user: ${user.toString()}")
}

fun userGsonStr() {
    val jsonStr = """
        {
            "name":"承香墨影",
            "age":"18",
            "languages":["CN","EN"]
        }
    """.trimIndent()
    val user = GsonBuilder()
            .registerTypeAdapter(
                    User::class.java,
                    UserGsonAdapter())
            .create()
            .fromJson<User>(jsonStr, User::class.java)
    print("user: \n${user.toString()}")
}
