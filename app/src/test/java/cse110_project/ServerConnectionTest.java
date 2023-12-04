package cse110_project;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class ServerConnectionTest {
    static boolean serverRunning = true;
    @Test
    public void detectsWhenServerDown() {
        ServerConnectionSituation badRequest = () -> {
            throw new Exception("Couldn't connect");
        };
        ServerConnectionSituation goodRequest = () -> {
            int x = 5 + 10;
            if(!serverRunning) throw new Exception("Server isn't running!");
        };

        assertFalse(App.detectServerError(badRequest));

        serverRunning = true;
        assertTrue(App.detectServerError(goodRequest));
        serverRunning = false;
        assertFalse(App.detectServerError(goodRequest));
    }
}
