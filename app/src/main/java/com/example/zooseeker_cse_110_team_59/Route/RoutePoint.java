package com.example.zooseeker_cse_110_team_59.Route;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class:           RoutePoint
 * Description:     A custom route class that implements the <code>Parcelable</code> interface,
 *                  which is similar to the Java Serializable class, which allows us to
 *                  transfer objects between different components in android
 */
public class RoutePoint implements Parcelable {

    public String exhibitName;
    public String streetName;
    public String ID;
    public double cumDistance;


    public RoutePoint(String exhibitName, String streetname, String ID, double cumDistance) {
        this.exhibitName = exhibitName;
        this.streetName = streetname;
        this.ID = ID;
        this.cumDistance = cumDistance;
    }

    // Link: https://stackoverflow.com/questions/7181526/how-can-i-make-my-custom-objects-parcelable
    // Title: How can I make my custom objects Parcelable?
    // Date Accessed: April 30th, 2022
    // Usage: I looked up how to pass list of objects as intents, and this is what came up as apparently
    // the most efficient. Android studio filled in all of the methods for us though, this just
    // told us that it was a thing
    protected RoutePoint(Parcel in) {
        exhibitName = in.readString();
        streetName = in.readString();
        ID = in.readString();
        cumDistance = in.readDouble();
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
        parcel.writeString(streetName);
        parcel.writeString(ID);
        parcel.writeDouble(cumDistance);
    }
}
