package com.firecrackersw.galleryui.gallery;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class MediaStoreImageHolder {
    private static List<MediaStoreImage> mImages = new ArrayList<>();
    private static List<String> mFailedImages = new ArrayList<>();

    public static List<MediaStoreImage> getImages() {
        return mImages;
    }

    public static void addFailedImage(String key) {
        mFailedImages.add(key);
    }

    public static void setImages(@NonNull Context context) {

        final ContentResolver cr = context.getContentResolver();
        final String[] query = new String[] {
                MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.DATE_TAKEN,
                MediaStore.Images.ImageColumns.DATE_MODIFIED
        };

        Cursor cursor = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, query, null,
                null, query[1] + " DESC");

        if (cursor != null && cursor.moveToFirst()) {
            // Only drop old images if we can get new ones.
            mImages = new ArrayList<>();
            do {
                Uri uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cursor.getString(0));
                String dateModified = cursor.getString(2);
                MediaStoreImage image = new MediaStoreImage(uri, dateModified);
                if (!mFailedImages.contains(image.getKey())) {
                    mImages.add(image);
                }
            } while (cursor.moveToNext());

            cursor.close();
        }
    }
}
