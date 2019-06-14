package com.firecrackersw.galleryui.gallery;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;
import com.firecrackersw.galleryui.GalleryPreviewDialogFragment;
import com.firecrackersw.galleryui.R;

import java.util.List;

public class UriAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final List<MediaStoreImage> mImages;
    public final static String GALLERY_PREVIEW_KEY = "gallery_preview_key";
    private final AppCompatActivity mActivity;

    public UriAdapter(List<MediaStoreImage> images, AppCompatActivity galleryActivity) {
        mImages = images;
        mActivity = galleryActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView view = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        final ImageView glideView = viewHolder.view.findViewById(R.id.gallery_image_view_glide);

        final MediaStoreImage image = mImages.get(position);
        final Uri imageUri = image.getUri();

        Glide.with(mActivity)
                .load(imageUri)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        notifyItemRemoved(viewHolder.getAdapterPosition());
                        MediaStoreImageHolder.addFailedImage(image.getKey());
                        return true;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .apply(new RequestOptions()
                        .signature(new ObjectKey(imageUri + ":" + image.getDateModified()))
                        .error(R.drawable.image_not_found))
                .into(glideView);

        glideView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setData(imageUri);
                mActivity.setResult(Activity.RESULT_OK, intent);
                mActivity.finish();
            }
        });

        glideView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                GalleryPreviewDialogFragment previewDialog = GalleryPreviewDialogFragment.newInstance(imageUri);
                FragmentManager manager = mActivity.getSupportFragmentManager();
                previewDialog.show(manager, GALLERY_PREVIEW_KEY);

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }
}