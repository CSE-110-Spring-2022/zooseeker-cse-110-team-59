package com.example.zooseeker_cse_110_team_59.Data;

import androidx.annotation.VisibleForTesting;

import com.example.zooseeker_cse_110_team_59.TestSettings;

/**
 * Class:            FilesToLoad
 * Description:      This class calls methods that would read in a graph file, vertex file, and an edge file.
 *
 * Public functions: getGraphFile          - return the graph file of the Zoo
 * Public functions: getVertexFile         - return the file with all the vertices
 * Public functions: getEdgeFile           - return the file with all the edges
 */
public class FilesToLoad {
    private static String[] filesToLoad = new String[]{"zoo_graph.json", "exhibit_info.json", "trail_info.json"};

    public static String getGraphFile() {
        return filesToLoad[0];
    }

    public static String getVertexFile() {
        return filesToLoad[1];
    }

    public static String getEdgeFile() {
        return filesToLoad[2];
    }

    /**
     * The injectNewFiles is used to load up new file stream. Viable for testing.
     * @param newFiles of file from the command line
     *
     * @return None
     */
    @VisibleForTesting
    public static void injectNewFiles(String[] newFiles) {
        filesToLoad = newFiles;
    }

}
