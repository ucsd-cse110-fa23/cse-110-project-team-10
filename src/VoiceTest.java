import org.junit.*;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

public class VoiceTest {
    @Test
    public void displayOutputTest(){
        Whisper mockWhisper = mock(Whisper.class);

        String expected = "I hate myself";
    }
}
