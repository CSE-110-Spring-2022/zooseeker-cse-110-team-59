package com.example.zooseeker_cse_110_team_59;

import android.os.Parcel;
import android.os.Parcelable;

public class RoutePoint implements Parcelable {
    public String exhibitName;
    public String directions;
    public double distance;


    public RoutePoint(String exhibitName, String directions, double distance) {
        this.exhibitName = exhibitName;
        this.directions = directions;
        this.distance = distance;
    }

    // Link: https://stackoverflow.com/questions/7181526/how-can-i-make-my-custom-objects-parcelable
    // Title: How can I make my custom objects Parcelable?
    // Date Accessed: April 30th, 2022
    // Usage: I looked up how to pass list of objects as intents, and this is what came up as apparently
    // the most efficient. Android studio filled in all of the methods for us though, this just
    // told us that it was a thing
    protected RoutePoint(Parcel in) {
        exhibitName = in.readString();
        directions = in.readString();
        distance = in.readDouble();
    }

    public static final Creator<RoutePoint> CREATOR = new Creator<RoutePoint>() {
        @Override
        public RoutePoint createFromParcel(Parcel in) {
            return new RoutePoint(in);
        }

        @Override
        public RoutePoint[] newArray(int size) {
            return new RoutePoint[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(exhibitName);
        parcel.writeString(directions);
        parcel.writeDouble(distance);
    }


}
