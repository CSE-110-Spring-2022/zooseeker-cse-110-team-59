package com.example.zooseeker_cse_110_team_59;

import androidx.annotation.VisibleForTesting;

public class FilesToLoad {
    private static String[] filesToLoad = new String[]{"sample_zoo_graph.json", "sample_node_info.json", "sample_edge_info.json"};

    public static String getGraphFile() {
        return filesToLoad[0];
    }

    public static String getVertexFile() {
        return filesToLoad[1];
    }

    public static String getEdgeFile() {
        return filesToLoad[2];
    }

    @VisibleForTesting
    public static void injectNewFiles(String[] newFiles) {
        filesToLoad = newFiles;
    }
}
