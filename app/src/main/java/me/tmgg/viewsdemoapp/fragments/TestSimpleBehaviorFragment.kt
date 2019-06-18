package me.tmgg.viewsdemoapp.fragments


import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_test_simple_behavior.*
import me.tmgg.viewsdemoapp.R
import me.tmgg.viewsdemoapp.SimpleAdapter


/**
 * A simple [Fragment] subclass.
 *
 */
class TestSimpleBehaviorFragment : android.support.v4.app.Fragment() {
    lateinit var mContext: Context
    var datas:MutableList<String>?= null
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_simple_behavior, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        datas = mutableListOf("item1","item2","item3","item4","item5","item6","item7","item8","item9","item10","item1","item2","item3","item4","item5","item6","item7","item8","item9","item10")
        recyclerview01.layoutManager = LinearLayoutManager(mContext)
        recyclerview01.adapter = SimpleAdapter(datas,R.layout.item_tv)
        activity?.actionBar?.hide()
    }


}
