package cse110_project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

public class VoiceTest {
    WhisperAPI api;
    @BeforeEach
    public void setup(){
        api = new mockWhisper();
    }

    @Test
    public void displayTest() throws Exception{
        String expected = ":)";
        assertEquals(expected, api.handleVoiceInput(null));
    }
    
    
}

