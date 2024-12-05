package umagic.ai.aiart.retrofit;

import android.util.Log;

public class TokenUtils {

    public static final TokenUtils f12988a = new TokenUtils();

    static {
        try {
            System.loadLibrary("tk");
        } catch (Throwable unused) {
            Log.e("TokenUtils", "could not be loaded");
        }
    }

    public native String paramsToken(String str);
}
