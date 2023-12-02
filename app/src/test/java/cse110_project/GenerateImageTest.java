package cse110_project;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class GenerateImageTest {
    private String url = "https://static.toiimg.com/thumb/83740315.cms?width=1200&height=900";

    @BeforeEach
    void setUp() {
        
    }

    @Test
    void validateURL(){
        try {
            URI uri = new URI(url);
            uri.toURL();
            assertTrue(true);
        } 
        catch (MalformedURLException e) {
            
        } 
        catch (URISyntaxException e) {
            
        }
    }
}
