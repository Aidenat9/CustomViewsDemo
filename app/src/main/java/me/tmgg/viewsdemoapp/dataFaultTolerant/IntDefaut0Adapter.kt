package me.tmgg.viewsdemoapp.dataFaultTolerant

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/5/6 9:52
 * package：me.tmgg.viewsdemoapp.dataFaultTolerant
 * version：1.0
 * <p>description：   反序列化的操作，所以我们实现 JsonDeserializer 接口即可，接管的是 Int 类型           </p>
 */
class IntDefaut0Adapter : JsonDeserializer<Int> {
    override fun deserialize(json: JsonElement?,
                             typeOfT: Type?,
                             context: JsonDeserializationContext?): Int {
        if (json?.getAsString().equals("")) {
            return 0
        }
        try {
            return json!!.getAsInt()
        } catch (e: NumberFormatException) {
            return 0
        }
    }
}

