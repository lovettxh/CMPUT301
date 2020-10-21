package com.example.gearbook;
import android.content.Context;
import android.widget.Toast;

// A class that contain toast object and a text setter function for the toast
// Used to indicate user for invalid input
public class Utility {
    private static Toast toast;
    public static void showToast(Context context, String text){
        if (toast == null){
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        }else{
            toast.setText(text);
        }
        toast.show();
    }
}
