package com.example.authvertical.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelperMethods {
    static Context mContext;

    private static final String TAG = "HelperMethods";
    // private static String localPath;
    static File root = new File(Environment.getExternalStorageDirectory(), "Khel");

    public static void CreateDirectory() {
        if (!root.exists()) {
            root.mkdirs();
            Log.e("Directory Created ", "HeyFitUserGalary");
        }
    }

    public static String createDirectoryAndSaveFile(Bitmap imageToSave, String fileName) {

        File direct = new File(Environment.getExternalStorageDirectory() + "Khel");

        if (!direct.exists()) {
            CreateDirectory();
        }

//        File file = new File(new File("/sdcard/" + AppConstants.DIR + "/"), fileName);
        File file = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
//            imageToSave.compress(Bitmap.CompressFormat.JPEG, 600, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    public static String saveImageBitmap(Bitmap imageToSave, String fileName) {

        File direct = new File(Environment.getExternalStorageDirectory() + "Khel");

        if (!direct.exists()) {
            CreateDirectory();
        }

//        File file = new File(new File("/sdcard/" + AppConstants.DIR + "/"), fileName);
        File file = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 70, out);
//            imageToSave.compress(Bitmap.CompressFormat.JPEG, 600, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }


    public static void showToast(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
//        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static boolean isUserNameValid(String name) {
        final String USERNAME_PATTERN = "^[a-zA-Z0-9_.]{3,15}$";
        Pattern pattern = Pattern.compile(USERNAME_PATTERN);
        Matcher matcher;
        matcher = pattern.matcher(name);
        return matcher.matches();


    }

    public static boolean isYearValid(String year) {
        final String USERNAME_PATTERN = "^[0-9]{4,4}$";
        Pattern pattern = Pattern.compile(USERNAME_PATTERN);
        ;
        Matcher matcher;
        matcher = pattern.matcher(year);
        return matcher.matches();
    }


    public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager input = (InputMethodManager) activity
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);

            input.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getWindowToken(), 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void showSoftKeyboard(Activity activity) {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void showSoftKeyboard(Activity activity, EditText et) {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean isEmailValid(String email) {
        String regExpn = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches())
            return true;
        else
            return false;
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target)
                && android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                .matches();
    }


    public interface BitmapResponse {
        void bitmapReceived(Bitmap mBitmap);

        void failed(Exception e);
    }

    public static int getWidth(Context context) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        return width;
    }

    public static int getHeight(Context context) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
//        int width = displaymetrics.widthPixels;
        return height;
    }

    public static int calculateHeight(Context context) {

        int w = getWidth(context);
//        int h = (w / 3) * 4;
        int h = (w / 16) * 9;
        return h;
    }

    public static int calculateViewHeight(Context context, int width) {

        int w = width;
//        int h = (w / 3) * 4;
        int h = (w / 16) * 9;
        return h;
    }


    public static String getDateOnly(long time) {
        return new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(time);
    }

    public static String getDateAndTime(long time) {
//        SimpleDateFormat sample = new SimpleDateFormat("MMM dd, yyyy, hh:mm a", Locale.getDefault());
        SimpleDateFormat sample = new SimpleDateFormat("MMM dd, yyyy, hh:mm a", Locale.US);
        return sample.format(new Date(time));
    }

    public static String getTimeOnly(long time) {
        SimpleDateFormat sample = new SimpleDateFormat("hh:mm a", Locale.US);
        return sample.format(time);
    }

}
