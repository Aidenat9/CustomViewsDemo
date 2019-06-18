package me.tmgg.viewsdemoapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_vertical_tab.*
import me.tmgg.viewsdemoapp.R
import q.rorbin.verticaltablayout.VerticalTabLayout


/**
 * A simple [Fragment] subclass.
 *
 */
class VerticalTabFragment : android.support.v4.app.Fragment() {
    lateinit var mContext: Context
    var datas:MutableList<String>?= null
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context!!
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vertical_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        datas = mutableListOf("item1","item2","item3","item4","item5","item6","item7","item8","item9","item10","item1","item2","item3","item4","item5","item6","item7","item8","item9","item10")
        vetticaltab.setTabs(datas as List<String>,object :VerticalTabLayout.OnTabClickedListener{
            override fun selected(position: Int) {
                Toast.makeText(context,"position  "+position,Toast.LENGTH_SHORT).show()
            }

        })
    }


}
