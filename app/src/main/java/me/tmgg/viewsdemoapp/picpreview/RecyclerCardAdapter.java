package me.tmgg.viewsdemoapp.picpreview;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import me.tmgg.viewsdemoapp.R;


public class RecyclerCardAdapter extends RecyclerView.Adapter<RecyclerCardAdapter.CardHolder> {

	private Context             mContext;
	private OnItemClickListener mListener;
    private RequestOptions options;

    public interface OnItemClickListener {

		void onItemClick(View view, int position);
	}

	public RecyclerCardAdapter(Context context, OnItemClickListener listener) {
		mContext = context;
		mListener = listener;
	}

	@NonNull
	@Override
	public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		final CardHolder holder = new CardHolder(LayoutInflater.from(mContext).inflate(R.layout.item_card_album_image, parent, false));
		holder.mAlbumImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mListener.onItemClick(holder.mAlbumImage, holder.getAdapterPosition());
			}
		});
		return holder;
	}

	@Override
	public void onBindViewHolder(@NonNull CardHolder holder, int position) {
        if(null==options){
            options = new RequestOptions();
            options.placeholder(R.drawable.ic_launcher).error(R.drawable.ic_launcher).dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL);
        }
		Glide.with(mContext).asBitmap().load(ImageConstants.IMAGE_SOURCE[position])
				.apply(options)
				.into(holder.mAlbumImage);
		/**
		 * 设置共享元素的名称
		 */
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
			holder.mAlbumImage.setTransitionName(ImageConstants.IMAGE_SOURCE[position]+position);
		}
		holder.mAlbumImage.setTag(ImageConstants.IMAGE_SOURCE[position]+position);
	}

	@Override
	public int getItemCount() {
		return ImageConstants.IMAGE_SOURCE.length;
	}

	static class CardHolder extends RecyclerView.ViewHolder {

		final ImageView mAlbumImage;

		CardHolder(View itemView) {
			super(itemView);
			mAlbumImage = (ImageView) itemView;
		}
	}
}
