package me.tmgg.viewsdemoapp

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/6/6 11:30
 * package：me.tmgg.viewsdemoapp
 * version：1.0
 * <p>description：              </p>
 */
class SimpleAdapter(datas: MutableList<String>?, res: Int) : BaseQuickAdapter<String, BaseViewHolder>(res, datas) {
    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.setText(R.id.tv,item)
    }
}