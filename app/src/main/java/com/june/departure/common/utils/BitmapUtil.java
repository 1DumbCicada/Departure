package com.june.departure.common.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Maibenben on 2017/10/4.
 */

public class BitmapUtil {

    /**
     * 获取图片的宽高
     *
     * @param filePath
     * @return
     */
    private static int[] getImageWidthAndHeight(String filePath) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, opts);
        // 从Options中获取图片的分辨率
        int imageHeight = opts.outHeight;
        int imageWidth = opts.outWidth;
        return new int[]{imageWidth, imageHeight};
    }

    /**
     * 压缩图片质量
     * 从100开始压缩，每次减10，直到减到30
     *
     * @param bitmap
     * @param filePath
     * @return
     */
    public static File compressByQuality(Bitmap bitmap, String filePath) {
        long MAX_LENGTH = 10 * 1024;
        int MAX_OPTION = 80;
        File file = new File(filePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
        while (baos.toByteArray().length > MAX_LENGTH && options > MAX_OPTION) {
            baos.reset();
            options -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        if (baos.toByteArray().length > MAX_LENGTH) {
            return null;
        }
        FileOutputStream fOut = null;
        try {
            file.createNewFile();
            fOut = new FileOutputStream(file);
            fOut.write(baos.toByteArray());
            fOut.flush();
        } catch (IOException e) {
            Log.e("ExpressionUtils", Log.getStackTraceString(e));
        } finally {
            if (null != fOut) {
                try {
                    fOut.close();
                } catch (IOException e) {
                    Log.e("ExpressionUtils", Log.getStackTraceString(e));
                }
            }
        }
        return file;

    }
}
