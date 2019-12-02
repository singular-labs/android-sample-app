package net.singular.singularsampleapp;

import android.content.Context;
import android.widget.Toast;

public class Utils {
    public static boolean isNullOrEmpty(String string) {
        return string == null || string.trim().equals("");
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
