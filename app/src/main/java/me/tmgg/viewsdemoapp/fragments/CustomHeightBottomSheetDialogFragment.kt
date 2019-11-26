package me.tmgg.viewsdemoapp.fragments


import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import me.tmgg.viewsdemoapp.R
import me.tmgg.viewsdemoapp.utils.Utils
import me.tmgg.viewsdemoapp.widgets.CustomHeightBottomSheetDialog

/**
 *设置高度的底部对话框
 */
class CustomHeightBottomSheetDialogFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_custom_height_bottom_sheet_dialog, container, false)
        val button = view.findViewById<Button>(R.id.show)
        button.setOnClickListener { show() }
        return view
    }
    private var mContext: Activity? = null

    override fun onAttach(context2: Activity?) {
        super.onAttach(context2)
        mContext = context2
    }

    fun show() {
        val bottomSheetDialog = activity?.let { CustomHeightBottomSheetDialog(it, Utils.dp2px(200f).toInt(), Utils.dp2px(300f).toInt()) };
        bottomSheetDialog?.apply {
            setContentView(R.layout.content_bottom_sheet)
            show()
        }
    }


}
