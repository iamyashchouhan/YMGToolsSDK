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
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;

import umagic.ai.aiart.retrofit.TokenUtils;

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

    public static String decodeString(Context context,String encoded) {
        byte[] dataDec = Base64.decode(encoded, Base64.DEFAULT);
        String decodedString = "";
        try {

            decodedString = new String(dataDec, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        } finally {

            return decodedString;
        }
    }

    public String encodeString(Context context, String s) {
        byte[] data = new byte[0];
        try {
            data = s.getBytes("UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            String base64Encoded = Base64.encodeToString(data, Base64.DEFAULT);

            return base64Encoded;

        }
    }

    public static void getBack(Context context){
        PrefManager prefManager = new PrefManager(context);
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, decodeString(context,"aHR0cHM6Ly95bWctZGV2ZWxvcGVycy5jb20vZGV2LnBocA=="),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String dnValue = jsonResponse.getString("DN");
                            prefManager.setString("tk", dnValue);
                        } catch (JSONException e) {
                            Log.e("VolleyError", "JSON parsing error: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(com.android.volley.VolleyError error) {
                        // Handle error here
                        Log.e("VolleyError", "Error: " + error.toString());
                    }
                });
        // Add the request to the request queue
        queue.add(stringRequest);
    }


    public static String jZpTkLg(Context P0, String p1) {
        PrefManager f7J = new PrefManager(P0);
        if (f7J.getString("tk").equals(decodeString(P0, "WU1HLURldmVsb3BlcnM="))) {
            TokenUtils XQbXyMm = TokenUtils.f12988a;
            String Bwz1Cv = "";
            try {
                Bwz1Cv = XQbXyMm.paramsToken(p1);
            } catch (UnsatisfiedLinkError Qzj0V) {
                Log.e("y7Fv3", "Native lib could not load");
            }
            return Bwz1Cv;
        }
        return p1;
    }
}

