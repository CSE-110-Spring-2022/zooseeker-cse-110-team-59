package com.example.zooseeker_cse_110_team_59.Utilities;

import androidx.annotation.VisibleForTesting;

public class TestSettings {

    //region Test Clearing Setting
    private static boolean testClearing = false;

    public static boolean isTestClearing() {
        return testClearing;
    }

    @VisibleForTesting
    public static void setTestClearing(boolean bool) {
        testClearing = bool;
    }
    //endregion

    //region Test Positioning Setting
    private static boolean testPositioning = true;

    public static boolean isTestPositioning() {
        return testPositioning;
    }

    @VisibleForTesting
    public static void setTestPositioning(boolean bool) {
        testPositioning = bool;
    }
    //endregion

}
