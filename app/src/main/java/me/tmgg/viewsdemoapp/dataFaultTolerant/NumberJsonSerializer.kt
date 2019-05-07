package me.tmgg.viewsdemoapp.dataFaultTolerant

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type


/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/5/6 11:53
 * package：me.tmgg.viewsdemoapp.dataFaultTolerant
 * version：1.0
 * <p>description：  数字转为string            </p>
 */
class NumberJsonSerializer: JsonSerializer<Number> {
    override fun serialize(src: Number?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src.toString())
    }

}