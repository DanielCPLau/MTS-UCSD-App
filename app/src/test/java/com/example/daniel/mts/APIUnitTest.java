package com.example.daniel.mts;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import com.example.daniel.mts.RemoteFetch;

import java.util.ArrayList;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class APIUnitTest {
    @Mock
    private RemoteFetch rf = new RemoteFetch();


    @Test
    public void checkStopFav() throws Exception {
        ArrayList<Stop> stops = rf.getStopsNearLoc(32.877571, -117.236009, 50);
    }

    @Test
    public void checkStops() throws Exception {
        ArrayList<Stop> stops = rf.getStopsNearLoc(32.877571, -117.236009, 50);

        assertEquals(4, stops.size());
        assertEquals(stops.get(0).favorite, false);
        assertEquals(stops.get(1).favorite, false);
        assertEquals(stops.get(2).favorite, false);
        assertEquals(stops.get(3).favorite, false);
    }

    @Test
    public void latLonStops() throws Exception {
        ArrayList<Stop> stops = rf.getStopsNearLoc(32.877571, -117.236009, 50);
        assertEquals((int)stops.get(0).lat, 0);
        assertEquals((int)stops.get(1).lat, 0);
    }

    @Test
    public void checkIfUCSD() throws Exception {
        ArrayList<Stop> stops = rf.getStopsNearLoc(32.877571, -117.236009, 50);
        assertEquals(stops.get(0).isUCSDShuttleStop, false);
        assertEquals(stops.get(1).isUCSDShuttleStop, false);
        assertEquals(stops.get(2).isUCSDShuttleStop, false);
        assertEquals(stops.get(3).isUCSDShuttleStop, false);
    }

    @Test
    public void checkIfHandicap() throws Exception {
        ArrayList<Stop> stops = rf.getStopsNearLoc(32.877571, -117.236009, 50);
        assertEquals(stops.get(0).wheelchairBoarding, false);
        assertEquals(stops.get(1).wheelchairBoarding, false);
        assertEquals(stops.get(2).wheelchairBoarding, false);
        assertEquals(stops.get(3).wheelchairBoarding, false);
    }



}