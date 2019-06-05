package com.example.authvertical.utils;


import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.authvertical.R;
import com.example.authvertical.interfaces.ImagePickerCallBack;


public class DialogUtils {
    private static final String TAG = "DialogUtils";
    private static DialogUtils instance = null;
    private Dialog dialog;

    public synchronized static DialogUtils getInstance() {
        if (instance == null) {
            instance = new DialogUtils();
        }
        return instance;
    }


    public void pickImageDialog(Activity activity , ImagePickerCallBack callBack) {
        dialog = new Dialog ( activity );
        dialog.requestWindowFeature ( Window.FEATURE_NO_TITLE );
        dialog.setContentView ( R.layout.image_picker_dialog );
        dialog.getWindow ().setBackgroundDrawable ( new ColorDrawable ( Color.TRANSPARENT ) );
        dialog.setCancelable ( true );
        dialog.show ();

        Window window = dialog.getWindow ();
        window.setLayout ( RelativeLayout.LayoutParams.MATCH_PARENT , RelativeLayout.LayoutParams.MATCH_PARENT );
        TextView tvTakeImage = dialog.findViewById ( R.id.tvTakeImage );
        TextView tvPickImage = dialog.findViewById ( R.id.tvPickImage );
        TextView tvCancel = dialog.findViewById ( R.id.tvCancel );
        tvTakeImage.setOnClickListener ( v -> {
            dialog.dismiss ();
            callBack.openCamera ();

        } );
        tvPickImage.setOnClickListener ( v -> {
            dialog.dismiss ();
            callBack.openGallery ();

        } );
        tvCancel.setOnClickListener ( v -> {
            dialog.dismiss ();

        } );


    }
}

