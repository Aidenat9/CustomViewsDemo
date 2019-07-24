package me.tmgg.viewsdemoapp.fragments


import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.graphics.Palette
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import jp.wasabeef.glide.transformations.BlurTransformation
import me.tmgg.viewsdemoapp.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * 取色、毛玻璃
 *
 */
class PaleteeBlurFragment : Fragment() {
    private lateinit var mContext: Context
    private lateinit var data: List<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_paletee_blur, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context!!
    }

    private fun initView(view: View) {
        data = listOf(
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1564543021&di=d021ce837eb8a206a96e43a8851d4748&imgtype=jpg&er=1&src=http%3A%2F%2Fpic35.nipic.com%2F20131121%2F3822951_144045377000_2.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1563948303143&di=d7cdd1749ac78534272d7ec6e82dcf40&imgtype=0&src=http%3A%2F%2Fpic1.nipic.com%2F2008-12-05%2F200812584425541_2.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1563948303143&di=d1b9dd805ebabe723b65a70e913afeea&imgtype=0&src=http%3A%2F%2Fpic163.nipic.com%2Ffile%2F20180421%2F7092831_140036752037_2.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1563948303143&di=12da65dd22b7602dff7a61582e57ed28&imgtype=0&src=http%3A%2F%2Fimg.sccnn.com%2Fbimg%2F337%2F31452.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1563948303142&di=7af8a08e790a0fb972437308cdfd93ad&imgtype=0&src=http%3A%2F%2Fattachments.gfan.com%2Fforum%2Fattachments2%2Fattachments2%2Fday_110120%2F11012008485012851f3b754fad.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1563948362426&di=9a167bdfe1dc3e045003d287ebfe1ef1&imgtype=0&src=http%3A%2F%2Fpic31.nipic.com%2F20130708%2F3822951_162311192000_2.jpg",
                "https://ss3.baidu.com/-fo3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=b5e4c905865494ee982209191df4e0e1/c2cec3fdfc03924590b2a9b58d94a4c27d1e2500.jpg",
                "https://b-ssl.duitang.com/uploads/item/201709/20/20170920135431_J45HL.jpeg",
                "https://b-ssl.duitang.com/uploads/item/201612/13/20161213152103_PHScB.jpeg",
                "https://b-ssl.duitang.com/uploads/blog/201508/17/20150817190839_wfL32.jpeg",
                "http://img1.imgtn.bdimg.com/it/u=2725711155,3964253102&fm=26&gp=0.jpg",
                "https://b-ssl.duitang.com/uploads/item/201804/15/20180415165549_J8rw5.thumb.700_0.jpeg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1563948623114&di=3eaa487103f5af29a05971c83b917395&imgtype=0&src=http%3A%2F%2Fimg1.sc115.com%2Fuploads%2Fsc%2Fjpg%2F138%2F10168.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1563948637068&di=1306cc3ef7646886c24e3a5ab13485b6&imgtype=0&src=http%3A%2F%2Fbbs-fd.zol-img.com.cn%2Ft_s1200x5000%2Fg5%2FM00%2F00%2F04%2FChMkJ1gEgOWIMWSKAAm-FylQMIUAAW-CAHQkFYACb4v361.jpg"
        )
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview_paletee)
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        recyclerView.adapter = MAdapter(R.layout.item_pic, data)
    }

    private class MAdapter(layoutres: Int, data: List<String>?) : BaseQuickAdapter<String, BaseViewHolder>(layoutres, data) {
         var options:RequestOptions?  = null
        init {
            options = RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        }
        override fun convert(helper: BaseViewHolder?, item: String?) {
            val bg = helper?.getView<LinearLayout>(R.id.fl_bg)
            val image = helper?.getView<ImageView>(R.id.img)
            val tv0 = helper?.getView<TextView>(R.id.tv0)
            val tv1 = helper?.getView<TextView>(R.id.tv1)
            val tv2 = helper?.getView<TextView>(R.id.tv2)
            val tv3 = helper?.getView<TextView>(R.id.tv3)
            val tv4 = helper?.getView<TextView>(R.id.tv4)
            Glide.with(mContext).load(item).apply(options!!).fitCenter().into(image!!)
            Glide.with(mContext).load(item).apply(options!!).transform(BlurTransformation(10)).into(object :SimpleTarget<Drawable>(){
                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    bg?.background = resource
                }
            })
            Glide.with(mContext).asBitmap().apply(options!!).load(item).into(object :SimpleTarget<Bitmap>(){
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    try {
                        resource.apply {
                            Palette.from(resource!!).generate {
                                // 获取到柔和的深色的颜色（可传默认值）
                                val darkMutedColor = it?.getDarkMutedColor(Color.BLUE)
                                // 获取到活跃的深色的颜色（可传默认值）
                                val darkVibrantColor = it?.getDarkVibrantColor(Color.BLACK)
                                // 获取到柔和的明亮的颜色（可传默认值）
                                val lightMutedColor = it?.getLightMutedColor(Color.BLUE)
                                // 获取到活跃的明亮的颜色（可传默认值）
                                val lightVibrantColor = it?.getLightVibrantColor(Color.BLUE)
                                // 获取图片中最活跃的颜色（也可以说整个图片出现最多的颜色）（可传默认值）
                                val vibrantColor = it?.getVibrantColor(Color.BLUE)
                                try {
                                    tv0?.setBackgroundColor(darkMutedColor!!)
                                    tv1?.setBackgroundColor(darkVibrantColor!!)
                                    tv2?.setBackgroundColor(lightMutedColor!!)
                                    tv3?.setBackgroundColor(lightVibrantColor!!)
                                    tv4?.setBackgroundColor(vibrantColor!!)
                                } catch (e: Exception) {
                                }
                            }
                        }
                    }catch (exception:java.lang.Exception){}
                }
            })
        }

    }


}
