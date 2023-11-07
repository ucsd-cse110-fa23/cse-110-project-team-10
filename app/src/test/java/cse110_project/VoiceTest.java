package cse110_project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

public class VoiceTest {
    WhisperAPI api;
    Footer footer;
    @BeforeEach
    public void setup(){
        api = new mockWhisper();
        footer = new Footer();
    }

    /**
     * Mock test for capturing and displaying user's response
     */
    @Test
    public void displayTest() throws Exception{
        String expected = "user response";
        assertEquals(expected, api.handleVoiceInput(null));
    }

    /**
     * Test toggle for recording
     */
    @Test
    public void testRecordingStatus() throws Exception{
        assertFalse(footer.getRecordStatus());
        footer.toggleRecord();
        assertTrue(footer.getRecordStatus());
    }
    
    
}

