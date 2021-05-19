package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

//Helper factory for making objects such as bitmaps
public class HelperFactory {
    Context context;

    public HelperFactory(Context context) {
        this.context = context;
    }

    //make a square bitmap
    public Bitmap bitmapMaker(int id, int size) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);
        bitmap = Bitmap.createScaledBitmap(bitmap, size, size, false);
        return bitmap;
    }

    //make a rectangular bitmap
    public Bitmap bitmapMaker(int id, int width, int height) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        return bitmap;
    }
}
