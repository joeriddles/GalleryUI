package com.firecrackersw.galleryui.gallery;

import android.net.Uri;

public class MediaStoreImage {
    private Uri mUri;
    private String mDateModified;
    private String mKey;

    public MediaStoreImage(Uri uri, String dateModified) {
        mUri = uri;
        mDateModified = dateModified;
        mKey = uri.toString() + ":" + dateModified;
    }

    public Uri getUri() {
        return mUri;
    }

    public String getDateModified() {
        return mDateModified;
    }

    public String getKey() {
        return mKey;
    }
}
