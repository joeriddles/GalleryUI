package com.firecrackersw.galleryui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.firecrackersw.galleryui.gallery.MediaStoreImage;
import com.firecrackersw.galleryui.gallery.MediaStoreImageHolder;
import com.firecrackersw.galleryui.gallery.UriAdapter;

import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    private static final int EXTERNAL_STORAGE_PERMISSION = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                Toast.makeText(this, R.string.permission_request_text, Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        EXTERNAL_STORAGE_PERMISSION);
            }
        } else {
            setupGallery();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case EXTERNAL_STORAGE_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setupGallery();
                } else {
                    // permission denied.
                }
                return;
            }
        }
    }

    private void setupGallery() {
        int columns = Preferences.getSelectedGalleryWidthAsInt(getApplicationContext());

        RecyclerView recyclerView = findViewById(R.id.gallery_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, columns);
        recyclerView.setLayoutManager(mLayoutManager);

        MediaStoreImageHolder.setImages(getApplicationContext());
        List<MediaStoreImage> images = MediaStoreImageHolder.getImages();

        RecyclerView.Adapter adapter = new UriAdapter(images, GalleryActivity.this);
        recyclerView.setAdapter(adapter);
    }

}