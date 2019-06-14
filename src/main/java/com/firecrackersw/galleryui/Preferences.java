package com.firecrackersw.galleryui;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.firecrackersw.galleryui.gallery.GalleryWidth;

public class Preferences {
    private final static String sGalleryWidthSelected = "gallery_width_selected";

    public static GalleryWidth getSelectedGalleryWidth(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return GalleryWidth.values()[prefs.getInt(sGalleryWidthSelected, GalleryWidth.THREE.ordinal())];
    }

    public static int getSelectedGalleryWidthAsInt(Context context) {
        switch (getSelectedGalleryWidth(context)) {
            default:
            case TWO:
                return 2;
            case THREE:
                return 3;
            case FOUR:
                return 4;
        }
    }

    public static void setSelectedGalleryWidth(Context context, GalleryWidth width) {
        SharedPreferences.Editor prefs_editor = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).edit();
        prefs_editor.putInt(sGalleryWidthSelected, width.ordinal());
        prefs_editor.apply();
    }
}
