package com.firecrackersw.galleryui.gallery;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.AppGlideModule;

@GlideModule
public class SmallCacheGlideModule extends AppGlideModule {
    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        int diskCacheSizeBytes = 1024 * 1024 * 10; // 10 MB
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, diskCacheSizeBytes));
        // Memory cache size will also be roughly equal to the set disk cache size.
    }
}
