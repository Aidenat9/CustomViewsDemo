package me.tmgg.viewsdemoapp


import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import org.jetbrains.annotations.NotNull


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * 承载自定义view的布局
 *
 */
class PageFragment : Fragment() {
    @LayoutRes
    private var layoutRes: Int? = null

    companion object {
        fun newInstance(@NotNull @LayoutRes LayoutRes: Int): PageFragment {
            val fragment = PageFragment()
            val args = Bundle()
            args.putInt("LayoutRes", LayoutRes)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_page, container, false)
        val viewStub = view.findViewById<ViewStub>(R.id.viewStub)
        viewStub.layoutResource = layoutRes!!
        viewStub.inflate()
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundles = arguments
        if (null != bundles) {
            layoutRes = bundles.getInt("LayoutRes")
        }
    }


}
