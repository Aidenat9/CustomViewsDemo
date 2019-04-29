package me.tmgg.viewsdemoapp.fragments


import android.app.Dialog
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import me.tmgg.viewsdemoapp.R



// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class FullSheetDialogFragment : BottomSheetDialogFragment() {
    private var behavior: BottomSheetBehavior<*>? = null
    private var list:MutableList<String>? = null
    private var mContext:Context? = null
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        val view = View.inflate(context, R.layout.fragment_full_sheet_dialog, null)
        initView(view)
        dialog.setContentView(view)
        behavior = BottomSheetBehavior.from(view.parent as View)
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        behavior?.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun initView(view: View?) {
        list = ArrayList(100)
        for (i in 0..99) {
            list?.add("条目$i")
        }
        val recyclerView = view?.findViewById<RecyclerView>(R.id.rv_item)
        recyclerView?.layoutManager = LinearLayoutManager(mContext)
        val adapter = Adapter(android.R.layout.simple_list_item_1,list)
        recyclerView?.adapter = adapter
    }

    private class Adapter(layoutResId: Int, data: List<String>?) : BaseQuickAdapter<String, BaseViewHolder>(layoutResId, data) {
        override fun convert(helper: BaseViewHolder?, item: String?) {
            helper?.setText(android.R.id.text1,item)
        }

    }

}
