package me.tmgg.viewsdemoapp.fragments


import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import me.tmgg.viewsdemoapp.R
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ABottomSheetDialogFragment : BottomSheetDialogFragment() {

    companion object {
        var _context: Context? = null
        fun getContext(): Context {
            return _context!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_bottom_sheet_dialog, container, false)
        initView(view)
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        _context = context!!
    }

    private var datas: MutableList<String>? = null
    private fun initView(view: View?) {
        datas = ArrayList(15)
        for (i in 0..14) {
            datas?.add("item $i")
        }
        val recyclerView = view!!.findViewById<RecyclerView>(R.id.recyclerView1)
        recyclerView.layoutManager = LinearLayoutManager(_context)
        recyclerView.adapter = Adapter(android.R.layout.simple_list_item_1,datas)
    }

    private class Adapter(layoutResId: Int, data: List<String>?) : BaseQuickAdapter<String, BaseViewHolder>(layoutResId, data) {
        override fun convert(helper: BaseViewHolder?, item: String?) {
            helper?.setText(android.R.id.text1,item)
        }

    }


}
