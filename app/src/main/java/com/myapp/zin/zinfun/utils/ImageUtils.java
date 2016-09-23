package com.myapp.zin.zinfun.utils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ZIN on 2016/4/2.
 */
public class ImageUtils {

    public static String getSDPath()
    {
        boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if(hasSDCard)
        {
            return Environment.getExternalStorageDirectory().toString() + "/mmpicture";
        }
        else
            return "/data/data/com.example.imageviewsave2bitmap/mmpicture";
    }
    public static Bitmap convertViewToBitmap(View view)
    {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();

        return bitmap;
    }

    public static void saveImage(String strFileName,Bitmap bitm,String savePath)
    {
        Bitmap bitmap = bitm;
        String strPath = savePath;

        try
        {
            File destDir = new File(strPath);
            if (!destDir.exists())
            {
                Log.d("MagicMirror", "Dir not exist create it " + strPath);
                destDir.mkdirs();
                Log.d("MagicMirror", "Make dir success: " + strPath);
            }

            File imageFile = new File(strPath + "/" + strFileName);
            imageFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
