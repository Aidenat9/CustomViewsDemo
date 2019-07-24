package me.tmgg.viewsdemoapp

import android.view.View

/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/7/24 11:01
 * package：me.tmgg.viewsdemoapp
 * version：1.0
 * <p>description：              </p>
 */
fun <T :View> View.getViewById(id:Int):T{
        return findViewById<T>(id)
}