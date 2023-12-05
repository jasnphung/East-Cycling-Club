package com.example.eastcyclingclub;

import android.os.Looper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class EditProfileUnitTest {

    public static final String INVALID_USERNAME = "";
    public static final String INVALID_PASSWORD = "";
    public static final String INVALID_EMAIL = "invalidEmail.com";
    public static final String EMPTY_EMAIL = "";

    public static final String VALID_USERNAME = "validUsername";
    public static final String VALID_PASSWORD = "validPassword";
    public static final String VALID_EMAIL = "validEmail@seg2105.com";


    @Test
    public void testAllValidCredentials() {

        ParticipantActivityEditProfile participantTester = new ParticipantActivityEditProfile();

        boolean result = participantTester.allCredentialsAreValid(VALID_USERNAME, VALID_EMAIL, VALID_PASSWORD);

        assertTrue(result);
    }



    @Test
    public void testOnlyValidUsername() {

        ParticipantActivityEditProfile participantTester = new ParticipantActivityEditProfile();

        boolean result = participantTester.allCredentialsAreValid(VALID_USERNAME, INVALID_EMAIL, INVALID_PASSWORD);

        assertFalse(result);

    }


    @Test
    public void testOnlyValidEmail() {

        ParticipantActivityEditProfile participantTester = new ParticipantActivityEditProfile();

        boolean result = participantTester.allCredentialsAreValid(INVALID_USERNAME, VALID_EMAIL, INVALID_PASSWORD);

        assertFalse(result);

    }


    @Test
    public void testOnlyValidPassword() {

        ParticipantActivityEditProfile participantTester = new ParticipantActivityEditProfile();

        boolean result = participantTester.allCredentialsAreValid(INVALID_USERNAME, INVALID_EMAIL, VALID_PASSWORD);

        assertFalse(result);

    }



    @Test
    public void testEmptyEmail() {

        ParticipantActivityEditProfile participantTester = new ParticipantActivityEditProfile();

        boolean result = participantTester.allCredentialsAreValid(VALID_USERNAME, EMPTY_EMAIL, INVALID_PASSWORD);

        assertFalse(result);

    }

    @Test
    public void testAllInvalidCredentials(){

        ParticipantActivityEditProfile participantTester = new ParticipantActivityEditProfile();

        boolean result = participantTester.allCredentialsAreValid(INVALID_USERNAME, INVALID_EMAIL, INVALID_PASSWORD);

        assertFalse(result);

    }

}