package com.example.zooseeker_cse_110_team_59;

import android.app.Activity;
import android.app.AlertDialog;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import java.util.ArrayList;

/**
 * Class:            Utilities
 * Description:      sets up the Alert method in the application and indicates when a message, a button or a title is set
 *
 * Public functions: showAlert          - alert the system when a button, a message, or a title is changed.
 */
public class Utilities {
    /**
     * The showAlert calls the AlertDialog class which help sets up title, messages, and buttons.
     * It allows the user to enter in an animal in the dialog.
     * @param       Activity activity
     *              String title
     *              String message
     *
     * @return None
     */
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
