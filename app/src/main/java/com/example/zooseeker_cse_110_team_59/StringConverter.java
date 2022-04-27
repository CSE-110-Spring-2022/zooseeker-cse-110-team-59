package com.example.zooseeker_cse_110_team_59;

import android.hardware.biometrics.BiometricManager;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringConverter {
    public static String normalToCamelCase (String input) {
        List<String> words = Arrays.stream(input.split(" ")).collect(Collectors.toList());
        String output = words.remove(0).toLowerCase();
        List<String> wordsUpper = words.stream().map(str -> str.substring(0, 1).toUpperCase() + str.substring(1)).collect(Collectors.toList());
        return output + String.join("", wordsUpper);
    }

    public static List<String> normalToCamelCaseList (List<String> input) {
        return input.stream().map(str -> normalToCamelCase(str)).collect(Collectors.toList());
    }

    public static String dashedToCamelCase (String input) {
        List<String> words = Arrays.stream(input.split("-")).collect(Collectors.toList());
        String first = words.remove(0);
        List<String> wordsUpper = words.stream().map(str -> str.substring(0, 1).toUpperCase() + str.substring(1)).collect(Collectors.toList());
        wordsUpper.add(0, first);
        return String.join("", wordsUpper);
    }

    public static List<String> dashedToCamelCaseList(List<String> input) {
        return input.stream().map(str -> dashedToCamelCase(str)).collect(Collectors.toList());
    }

    public static String dashedToNormal (String input) {
        List<String> words = Arrays.stream(input.split("-")).collect(Collectors.toList());
        List<String> wordsUpper = words.stream().map(str -> str.substring(0, 1).toUpperCase() + str.substring(1)).collect(Collectors.toList());
        return String.join(" ", wordsUpper);
    }

    public static List<String> dashedToNormalList(List<String> input) {
        return input.stream().map(str -> dashedToNormal(str)).collect(Collectors.toList());
    }
}
