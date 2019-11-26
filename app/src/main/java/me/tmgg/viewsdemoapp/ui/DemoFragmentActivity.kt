package me.tmgg.viewsdemoapp.ui

import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import me.tmgg.viewsdemoapp.R
import me.tmgg.viewsdemoapp.fragments.*

class DemoFragmentActivity : AppCompatActivity() {

    private var dialog: BottomSheetDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_fragment)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val transaction = supportFragmentManager.beginTransaction()
        when (item.itemId) {
            R.id.menu_clip_view -> transaction.replace(R.id.framelayout, ClippingBasicFragment())
            R.id.menu_elevation_view -> {
                val fragment = ElevationDragFragment()
                transaction.replace(R.id.framelayout, fragment)
            }
            R.id.menu_constraintlayout -> transaction.replace(R.id.framelayout, ConstraintlayoutFragment())
            R.id.menu_shopcart -> transaction.replace(R.id.framelayout, ShopCartFragment())
            R.id.dstsrc -> transaction.replace(R.id.framelayout, XfermodeFragment())
            R.id.snapHelper -> transaction.replace(R.id.framelayout, SnapHelperDemoFragment())
            R.id.bottomBehavior -> transaction.replace(R.id.framelayout, BottomBehaviorFragment())
            R.id.bottomDialog -> initBottomDialog()
            R.id.bottomFragment -> {
                val sheetDialogFragmentKt = ABottomSheetDialogFragment()
                sheetDialogFragmentKt.show(supportFragmentManager, "CustomFragmentDialog")
            }
            R.id.fullBottomSheetDialogFragment -> {
                val fullSheetDialogFragment = FullSheetDialogFragment()
                fullSheetDialogFragment.show(supportFragmentManager, "fullSheetDialogFragment")
            }
            R.id.heightBottomSheetDialog -> {
                transaction.replace(R.id.framelayout, CustomHeightBottomSheetDialogFragment())
            }
            R.id.diffFragment -> {
                transaction.replace(R.id.framelayout, DiffRecyclerViewFragment())
            }
            R.id.simpleBehavior -> {
                transaction.replace(R.id.framelayout, TestSimpleBehaviorFragment())
            }
            R.id.demoDetailBehavior -> {
                transaction.replace(R.id.framelayout, GoodsDetailBehaviorFragment())
            }
            R.id.vetticaltab -> {
                transaction.replace(R.id.framelayout, GoodsDetailBehaviorFragment())
            }
            R.id.listview -> {
                transaction.replace(R.id.framelayout, ListViewFragment())
            }
            R.id.ripple -> {
                transaction.replace(R.id.framelayout, RippleFragment())
            }
            R.id.paletee_blur -> {
                transaction.replace(R.id.framelayout, PaleteeBlurFragment())
            }
            R.id.scalableImageView -> {
                transaction.replace(R.id.framelayout, ScalableImageViewFragment())
            }
            else -> {
            }
        }
        transaction.commit()
        return super.onOptionsItemSelected(item)
    }

    /**
     * BottomSheetDialog的setContentView最终是被CoordinatorLayout包裹住
     */
    private fun initBottomDialog() {
        if (dialog == null) {
            dialog = BottomSheetDialog(this)
            dialog!!.setCancelable(false)
            dialog!!.setCanceledOnTouchOutside(true)
        }
        val view = LayoutInflater.from(this@DemoFragmentActivity).inflate(R.layout.content_bottom_sheet_dialog, null)
        view.findViewById<View>(R.id.iv_wechat).setOnClickListener {
            Toast.makeText(this@DemoFragmentActivity, "微信", Toast.LENGTH_SHORT).show()
            dialog!!.dismiss()
        }
        dialog!!.setContentView(view)
        dialog!!.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        return super.onPrepareOptionsMenu(menu)
    }
}
