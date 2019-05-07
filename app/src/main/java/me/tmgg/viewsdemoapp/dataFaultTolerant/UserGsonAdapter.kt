package me.tmgg.viewsdemoapp.dataFaultTolerant

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 * 用 JsonDeserializer 来接管解析，这一次将 User 类的整个解析都接管了。
 */
class UserGsonAdapter:JsonDeserializer<User>{
    override fun deserialize(json: JsonElement,
                             typeOfT: Type?,
                             context: JsonDeserializationContext?): User {

        var user = User()
        if(json.isJsonObject){
            print(json.asJsonObject.toString())
            val jsonObject = Gson().fromJson<User>(json.asJsonObject.toString(),User::class.java)
            user.name = jsonObject.name
            user.age = jsonObject.age
            user.languageStr = jsonObject.languages.toString()
            user.languages = ArrayList()
            val typeToken = object :TypeToken<List<String>>(){}
            val type = typeToken.type
            val languageJsonArray = Gson().fromJson<List<String>>(user.languageStr, type)
            for(i in 0 until languageJsonArray.size){
                user.languages.add(languageJsonArray[i])
            }
        }
        return user
    }
}
