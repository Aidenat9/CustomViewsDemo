package me.tmgg.viewsdemoapp

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_views.*

class ViewsActivity : AppCompatActivity() {
    var dataList = mutableListOf<ViewsModel>()
    init {
        dataList.add(ViewsModel(R.layout.layout_dashboard,"仪表盘"))
        dataList.add(ViewsModel(R.layout.layout_telescope,"望远镜"))
        dataList.add(ViewsModel(R.layout.layout_textview,"textview"))
        dataList.add(ViewsModel(R.layout.layout_arc,"arc"))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_views)
        pager.adapter = object :FragmentPagerAdapter(supportFragmentManager){
            override fun getItem(p0: Int): Fragment {
                return PageFragment.newInstance(dataList[p0].layoutRes)
            }

            override fun getCount(): Int {
                return dataList.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return dataList[position].titleRes
            }

        }
        pager.offscreenPageLimit = 3
        tabLayout.setupWithViewPager(pager)
    }
     inner  class ViewsModel internal constructor(@LayoutRes val layoutRes:Int ,@StringRes val titleRes:String)
}
