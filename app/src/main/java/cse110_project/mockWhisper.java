package cse110_project;

import java.io.File;

public class mockWhisper implements WhisperAPI {

    @Override
    public String handleVoiceInput(File file) throws Exception{
        String Mockresult = "user response";
        return Mockresult;
    }
    
}
