package com.example.eastcyclingclub;

import android.os.Looper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class ParticipantEventSelectAndRateUnitTest {

    private static final String EMPTY_SEARCH_QUERY = "";

    private static final int VALID_EVENT_RATING = 3;
    private static final int INVALID_EVENT_RATING = 6;
    private static final int NEGATIVE_EVENT_RATING = -3;

    // this is here for now until rating has been implemented
//
//
//    private static final int MINIMUM_EVENT_RATING = 1;
//    private static final int MAXIMUM_EVENT_RATING = 5;
//    public boolean validateEventRating(int rating){
//        return !(rating < MINIMUM_EVENT_RATING) && !(rating > MAXIMUM_EVENT_RATING);
//    }

    /* ==========================
     *  SEARCH FOR EVENTS TEST
     ========================= */
    @Test
    public void testEmptySearch(){
        ParticipantActivityEvents testedEvent = new ParticipantActivityEvents();
        boolean result = testedEvent.searchList(EMPTY_SEARCH_QUERY);
        assertFalse(result);
    }


    /* ==========================
     *     RATE EVENTS TESTS
     ========================= */

    @Test
    public void testNegativeEventRating(){
        ParticipantActivityEvents testedEvent = new ParticipantActivityEvents();
//        boolean result = testedEvent.validateEventRating(NEGATIVE_EVENT_RATING);
//        assertFalse(result);
    }


    @Test
    public void testInvalidEventRating(){
        ParticipantActivityEvents testedEvent = new ParticipantActivityEvents();
//        boolean result = testedEvent.validateEventRating(INVALID_EVENT_RATING);
//        assertFalse(result);
    }


    @Test
    public void testValidEventRating(){
        ParticipantActivityEvents testedEvent = new ParticipantActivityEvents();
//        boolean result = testedEvent.validateEventRating(VALID_EVENT_RATING);
//        assertTrue(result);
    }
}
