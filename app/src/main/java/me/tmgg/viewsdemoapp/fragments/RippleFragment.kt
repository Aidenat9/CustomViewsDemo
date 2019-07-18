package me.tmgg.viewsdemoapp.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.fragment_ripple.*
import me.tmgg.viewsdemoapp.R
import me.tmgg.viewsdemoapp.widgets.RippleViewLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class RippleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_ripple, container, false)
        initView(view)
        return view
    }

    var b1 = false
    var b = false
    private fun initView(view: View?) {
        val rippleview = view?.findViewById<RippleViewLayout>(R.id.RippleViewLayout2)
        val fl = view?.findViewById<FrameLayout>(R.id.fl2)
        view?.findViewById<Button>(R.id.btn)?.setOnClickListener {
            if(b){
                rippleview?.setFocus(false)
                fl?.setBackgroundColor(resources.getColor(R.color.rippelColor))
            }
            else{
                rippleview?.setFocus(true)
                fl?.setBackgroundColor(resources.getColor(R.color.normalColor))

            }
            b = !b
        }
        val rippleview1 = view?.findViewById<RippleViewLayout>(R.id.RippleViewLayout1)
        rippleview1?.setOnClickListener {
            if(b1){
                RippleViewLayout1.setFocus(false)
            }
            else{
                RippleViewLayout1.setFocus(true)
            }
            b1 = !b1
        }
    }


}
