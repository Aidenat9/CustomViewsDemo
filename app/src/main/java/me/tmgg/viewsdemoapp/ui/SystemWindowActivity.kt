package me.tmgg.viewsdemoapp.ui

import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_system_window.*
import me.tmgg.viewsdemoapp.R

class SystemWindowActivity : AppCompatActivity(), View.OnTouchListener {
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

        when (event?.action) {
            MotionEvent.ACTION_MOVE -> {
                var x = event?.rawX
                var y = event?.rawY
                layoutparams.x = x!!.toInt()
                layoutparams.y = y!!.toInt()
                mWindowManager.updateViewLayout(image, layoutparams)
            }
        }

        return false
    }

    lateinit var context: Context
    lateinit var mWindowManager: WindowManager
    lateinit var image: ImageView
    lateinit var layoutparams: WindowManager.LayoutParams
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        setContentView(R.layout.activity_system_window)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(context)) {
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                startActivityForResult(intent, 100)
            } else {
                initWindow()
            }
        } else {
            initWindow()
        }
    }

    private fun remove() {
        if (null != mWindowManager && null != image && image.isAttachedToWindow) {
            mWindowManager.removeViewImmediate(image)
        }
    }

    private fun add() {
        image = ImageView(this)
        image.maxWidth = 100
        image.maxHeight = 100
        image.setImageResource(R.mipmap.ic_launcher)
        remove()
        layoutparams = WindowManager.LayoutParams(200, 200,
                2099,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        or WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED,
                PixelFormat.TRANSPARENT)
        layoutparams.gravity = Gravity.TOP and Gravity.START
        layoutparams.x = 0
        layoutparams.y = 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutparams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            layoutparams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR
        }
        image.setOnTouchListener(this)
        mWindowManager.addView(image, layoutparams)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            initWindow()
        }
    }

    private fun initWindow() {
        mWindowManager = application.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        btn_add.setOnClickListener {
            add()
        }
        btn_remove.setOnClickListener {
            remove()
        }
    }

    override fun onDestroy() {
        try {
            mWindowManager.removeViewImmediate(image)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        super.onDestroy()
    }

}
