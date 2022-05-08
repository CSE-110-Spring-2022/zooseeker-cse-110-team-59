package com.example.zooseeker_cse_110_team_59;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class Utilities {

    public static void showAlert(Activity activity, String title, String message) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);

        alertBuilder
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok", null);

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }
}
