package com.ymg.ymgdevelopers;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class YmgTools {

    public static void saveImage(Context context, Bitmap bitmap, String imageName) {
        PrefManager prefManager = new PrefManager(context);
        if (prefManager.getString("dev").equals("YMG-Developers")) {
            File directory = new File(Environment.getExternalStorageDirectory().toString() + "/Download");
            directory.mkdirs();
            // Create a file for the image
            File imageFile = new File(directory, imageName + ".jpg");
            try {
                // Create an output stream and save the image
                FileOutputStream outputStream = new FileOutputStream(imageFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                outputStream.flush();
                outputStream.close();

                // Add the image to the device's media gallery
                MediaScannerConnection.scanFile(context, new String[]{imageFile.getAbsolutePath()}, null, null);

                Toast.makeText(context, "Image saved successfully", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("ymgz", e.toString());
                Toast.makeText(context, "Error saving image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void shareText(Context context, String text) {
        PrefManager prefManager = new PrefManager(context);
        if (prefManager.getString("dev").equals("YMG-Developers")) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, text);
            context.startActivity(Intent.createChooser(intent, "Share via"));
        }
    }

    public static void shareImageWithText(Context context, Bitmap imageBitmap, String text) {
        PrefManager prefManager = new PrefManager(context);
        if (prefManager.getString("dev").equals("YMG-Developers")) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/*");

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), imageBitmap, "Image", null);
            Uri imageUri = Uri.parse(path);

            intent.putExtra(Intent.EXTRA_STREAM, imageUri);
            intent.putExtra(Intent.EXTRA_TEXT, text);
            context.startActivity(Intent.createChooser(intent, "Share via"));
        }
    }

    public static boolean isNetworkConnected(Context context) {
        PrefManager prefManager = new PrefManager(context);
        if (prefManager.getString("dev").equals("YMG-Developers")) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                return activeNetworkInfo != null && activeNetworkInfo.isConnected();
            }
        }
        return false;
    }

    public static void copyText(Context context, String text) {
        PrefManager prefManager = new PrefManager(context);
        if (prefManager.getString("dev").equals("YMG-Developers")) {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("label", text);
            assert clipboard != null;
            clipboard.setPrimaryClip(clip);
        }
    }

    public static void rateApp(Context context, String packageName) {
        PrefManager prefManager = new PrefManager(context);
        if (prefManager.getString("dev").equals("YMG-Developers")) {
            try {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
            } catch (ActivityNotFoundException ex) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
            }
        }
    }

    public static void shareApp(Context context, String packageName) {
        PrefManager prefManager = new PrefManager(context);
        if (prefManager.getString("dev").equals("YMG-Developers")) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + packageName);
            context.startActivity(Intent.createChooser(intent, "Share via"));
        }
    }

    public static void openUrl(Context context, String url) {
        PrefManager prefManager = new PrefManager(context);
        if (prefManager.getString("dev").equals("YMG-Developers")) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(url));
            context.startActivity(browserIntent);
        }
    }

    public void downloadAudio(Context context, String url, String title) {
        PrefManager prefManager = new PrefManager(context);
        if (prefManager.getString("dev").equals("YMG-Developers")) {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url))
                    .setTitle(title)
                    .setDescription("Downloading audio...")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(
                            Environment.DIRECTORY_DOWNLOADS,
                            title + ".mp3" // Customize the file name if needed
                    );

            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            if (downloadManager != null) {
                downloadManager.enqueue(request);
            }
        }
    }
}

