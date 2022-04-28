package com.example.zooseeker_cse_110_team_59;

import static org.junit.Assert.assertEquals;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class UnitTestsStringConverter {
    @Rule
    public ActivityScenarioRule<MainActivity> scenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testNormalToCamelCaseEmpty() {
        assertEquals("", StringConverter.normalToCamelCase(""));
    }

    @Test
    public void testNormalToCamelCaseNonEmpty() {
        assertEquals("arcticFoxViewpoint1", StringConverter.normalToCamelCase("Arctic Fox Viewpoint 1"));
    }

    @Test
    public void testNormalToCamelCaseListEmpty() {
        List<String> list = new ArrayList<>();
        assertEquals(new ArrayList<>(), StringConverter.normalToCamelCaseList(list));
    }

    @Test
    public void testNormalToCamelCaseListNonEmpty() {
        List<String> list = new ArrayList<>(Arrays.asList("Arctic Fox Viewpoint 1", "Elephant Odyssey"));
        assertEquals(new ArrayList<>(Arrays.asList("arcticFoxViewpoint1", "elephantOdyssey")), StringConverter.normalToCamelCaseList(list));
    }


    @Test
    public void testCamelCaseToNormalEmpty() {
        assertEquals("", StringConverter.camelCaseToNormal(""));
    }

    @Test
    public void testCamelCaseToNormalNonEmpty() {
        assertEquals("Arctic Fox Viewpoint 1", StringConverter.camelCaseToNormal("arcticFoxViewpoint1"));
    }

    @Test
    public void testCamelCaseToNormalListEmpty() {
        List<String> list = new ArrayList<>();
        assertEquals(new ArrayList<>(), StringConverter.camelCaseToNormalList(list));
    }

    @Test
    public void testCamelCaseToNormalListNonEmpty() {
        List<String> list = new ArrayList<>(Arrays.asList("arcticFoxViewpoint1", "elephantOdyssey"));
        assertEquals(new ArrayList<>(Arrays.asList("Arctic Fox Viewpoint 1", "Elephant Odyssey")), StringConverter.camelCaseToNormalList(list));
    }


    @Test
    public void testDashedToNormalEmpty() {
        assertEquals("", StringConverter.dashedToNormal(""));
    }

    @Test
    public void testDashedToNormalNonEmpty() {
        assertEquals("Arctic Fox Viewpoint 1", StringConverter.dashedToNormal("arctic-fox-viewpoint-1"));
    }

    @Test
    public void testDashedToNormalListEmpty() {
        List<String> list = new ArrayList<>();
        assertEquals(new ArrayList<>(), StringConverter.dashedToNormalList(list));
    }

    @Test
    public void testDashedToNormalListNonEmpty() {
        List<String> list = new ArrayList<>(Arrays.asList("arctic-fox-viewpoint-1", "elephant-odyssey"));
        assertEquals(new ArrayList<>(Arrays.asList("Arctic Fox Viewpoint 1", "Elephant Odyssey")), StringConverter.dashedToNormalList(list));
    }
}
