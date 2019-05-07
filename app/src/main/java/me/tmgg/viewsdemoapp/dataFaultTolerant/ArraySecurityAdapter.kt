package me.tmgg.viewsdemoapp.dataFaultTolerant

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.util.*

/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/5/6 10:18
 * package：me.tmgg.viewsdemoapp.dataFaultTolerant
 * version：1.0
 * <p>description： JsonElement 的 isJsonArray() 方法，判断当前是否是一个合法的 JSONArray 的数组，一旦不正确，就直接返回一个空的集合即可。             </p>
 */
class ArraySecurityAdapter :JsonDeserializer<List<*>>{
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): List<*> {
        if(json!!.isJsonArray()){
            val newGson = Gson()
            return newGson.fromJson(json, typeOfT)
        }else{
            return Collections.EMPTY_LIST
        }
    }
}