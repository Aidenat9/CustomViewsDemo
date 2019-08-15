package me.tmgg.viewsdemoapp.picpreview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import me.tmgg.viewsdemoapp.R;


public class RecyclerCardAdapter extends RecyclerView.Adapter<RecyclerCardAdapter.CardHolder> {

	private Context             mContext;
	private OnItemClickListener mListener;

	public interface OnItemClickListener {

		void onItemClick(View view, int position);
	}

	public RecyclerCardAdapter(Context context, OnItemClickListener listener) {
		mContext = context;
		mListener = listener;
	}

	@Override
	public CardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
	public void onBindViewHolder(CardHolder holder, int position) {
		Glide.with(mContext).asBitmap().load(ImageConstants.IMAGE_SOURCE[position])
				.into(new CustomTarget<Bitmap>() {
					@Override
					public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
						holder.mAlbumImage.setImage(ImageSource.bitmap(resource));
					}

					@Override
					public void onLoadCleared(@Nullable Drawable placeholder) {
						holder.mAlbumImage.setImage(ImageSource.resource(0));
					}
				});
		/**
		 * 设置共享元素的名称
		 */
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
			holder.mAlbumImage.setTransitionName(ImageConstants.IMAGE_SOURCE[position]);
		}
		holder.mAlbumImage.setTag(ImageConstants.IMAGE_SOURCE[position]);
	}

	@Override
	public int getItemCount() {
		return ImageConstants.IMAGE_SOURCE.length;
	}

	static class CardHolder extends RecyclerView.ViewHolder {

		final SubsamplingScaleImageView mAlbumImage;

		CardHolder(View itemView) {
			super(itemView);
			mAlbumImage = itemView.findViewById(R.id.main_card_album_image);
		}
	}
}
