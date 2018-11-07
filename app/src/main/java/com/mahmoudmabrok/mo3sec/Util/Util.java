package com.mahmoudmabrok.mo3sec.Util;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.mahmoudmabrok.mo3sec.R;

/**
 * Created by Mahmoud on 10/17/2018.
 */
public class Util {
    public static void hideInputKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public static AlertDialog getDialog(Context context, String message, String title) {
        View view = LayoutInflater.from(context).inflate(R.layout.custome_dialoge_title, null);
        TextView textView = view.findViewById(R.id.tvDialogeText);
        textView.setText(message);
        return new AlertDialog.Builder(context).setView(view).create();
    }
}
