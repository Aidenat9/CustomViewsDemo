package me.tmgg.viewsdemoapp.picpreview;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import me.tmgg.viewsdemoapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageDetailFragment extends Fragment {


    public ImageDetailFragment() {
        // Required empty public constructor
    }

    public static final String ARG_CURRENT_POSITION = "current_position";
    public static final String ARG_START_POSITION = "start_position";
    private int mCurrentPosition;
    private int mStartPosition;
    private ImageView mAlbumImage;


    public static ImageDetailFragment newInstance(int currentPosition, int startPosition) {
        Bundle args = new Bundle();
        args.putInt(ARG_CURRENT_POSITION, currentPosition);
        args.putInt(ARG_START_POSITION, startPosition);
        ImageDetailFragment fragment = new ImageDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentPosition = getArguments().getInt(ARG_CURRENT_POSITION);
        mStartPosition = getArguments().getInt(ARG_START_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_detail, container, false);
    }

}
