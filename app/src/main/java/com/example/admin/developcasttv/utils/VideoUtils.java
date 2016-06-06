package com.example.admin.developcasttv.utils;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.io.File;

/**
 * Created by admin on 29/04/2016.
 */
public class VideoUtils {


    public static String getPathVideo(Uri uri,Context ctx){

        Context context = ctx;
        Uri contentUri = uri;
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = null;

        try {
            if (Build.VERSION.SDK_INT > 19) {


                String wholeID = DocumentsContract.getDocumentId(contentUri);

                // Split at colon, use second item in the array
                String id = wholeID.split(":")[1];

                // where id is equal to
                //String sel = MediaStore.Images.Media._ID + "=?";
                String sel = MediaStore.Video.Media._ID + "=?";

                cursor = context.getContentResolver().query(
                        // MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        projection, sel, new String[]{id}, null);
                if(cursor == null){

                }

            } else {
                cursor = context.getContentResolver().query(contentUri,
                        projection, null, null, null);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        String path = null;
        try {
            int column_index = cursor
                    //.getColumnIndex(MediaStore.Images.Media.DATA);
                    .getColumnIndex(MediaStore.Video.Media.DATA);

            cursor.moveToFirst();
            path = cursor.getString(column_index).toString();
            cursor.close();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return path;
    }

    public static int getDuracaoVideo(Context context,String path){

        int duracao;

        duracao = MediaPlayer.create(context,Uri.fromFile(new File(path))).getDuration();

        return (int) (duracao * 0.001);
    }

}



