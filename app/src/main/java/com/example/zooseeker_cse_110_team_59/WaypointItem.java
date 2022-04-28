package com.example.zooseeker_cse_110_team_59;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WaypointItem {
    public String id;
    public String itemType;
    public String[] tags;

    WaypointItem(String id, String itemType, String[] tags) {
        this.id = id;
        this.itemType = itemType;
        this.tags = tags;
    }

    public static List<WaypointItem> loadJSON(Context context, String path) {
        try {
            InputStream input = context.getAssets().open(path);
            Reader reader = new InputStreamReader(input);
            Gson gson = new Gson();
            Type type = new TypeToken<List<WaypointItem>>(){}.getType();
            return gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public String toString() {
        return "WaypointItem{" +
                "id='" + id + '\'' +
                ", itemType='" + itemType + '\'' +
                ", tags=" + Arrays.toString(tags) +
                '}';
    }
}
