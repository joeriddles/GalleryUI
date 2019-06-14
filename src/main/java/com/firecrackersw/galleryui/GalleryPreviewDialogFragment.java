package com.firecrackersw.galleryui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

public class GalleryPreviewDialogFragment extends DialogFragment {

    public final static String SCREENSHOT_URI_KEY = "screenshot_key";
    private Uri mUri;

    public static GalleryPreviewDialogFragment newInstance(Uri uri) {
        GalleryPreviewDialogFragment dialog = new GalleryPreviewDialogFragment();

        Bundle args = new Bundle();
        args.putParcelable(SCREENSHOT_URI_KEY, uri);
        dialog.setArguments(args);

        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mUri = getArguments().getParcelable(SCREENSHOT_URI_KEY);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.dialog_gallery_preview, container, false);

        final FrameLayout galleryView = v.findViewById(R.id.gallery_preview);
        final ImageView imageView = v.findViewById(R.id.gallery_preview_image);
        final ImageView closeView = v.findViewById(R.id.gallery_preview_close);

        if (mUri != null && getActivity() != null) {
            Glide.with(getActivity())
                    .load(mUri)
                    .apply(new RequestOptions()
                            .error(R.drawable.image_not_found)
                            .fallback(R.drawable.image_not_found))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            galleryView.setVisibility(View.VISIBLE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            galleryView.setVisibility(View.VISIBLE);
                            return false;
                        }
                    })
                    .into(imageView);
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setData(mUri);
                getActivity().setResult(Activity.RESULT_OK, intent);
                getActivity().finish();
            }
        });

        closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return v;
    }
}