package cse110_project;

import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.util.*;

import org.json.JSONObject;

public class WhisperRequestHandler implements HttpHandler {
    private final String outputFilename = "server_audio.wav";

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        String response = "There was an internal error";
        int responseCode = 500;

        if (method.equals("POST")) {
            InputStream is = httpExchange.getRequestBody();
            String contentType = httpExchange.getRequestHeaders().getFirst("Content-Type");
            String boundary = parseBoundary(contentType);

            // Parse the multipart request
            parseMultipart(is, boundary);

            Whisper whisper = new Whisper();
            String output = "There was an internal error";
            try {
                output = whisper.handleVoiceInput(new File(outputFilename));
            } catch (Exception e) {
                System.err.println("Failed to do whisper: " + e);
                e.printStackTrace();
            }

            responseCode = 200;
            JSONObject outputObj = new JSONObject();
            outputObj.put("text", output);
            response = outputObj.toString();
        } else {
            System.out.println("Erroneous request");
        }

        httpExchange.sendResponseHeaders(responseCode, response.length());
        OutputStream outStream = httpExchange.getResponseBody();
        outStream.write(response.getBytes());
        outStream.close();
    }

    private String parseBoundary(String contentType) {
        if (contentType != null && contentType.contains("boundary=")) {
            return contentType.split("boundary=")[1];
        }
        return null;
    }

    private void parseMultipart(InputStream is, String boundary) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(is);
        FileOutputStream fileOutputStream = new FileOutputStream(outputFilename);

        byte[] boundaryBytes = ("\r\n--" + boundary).getBytes("UTF-8");
        byte[] buffer = new byte[4096];
        int bytesRead;
        boolean startWriting = false;

        while ((bytesRead = bis.read(buffer)) != -1) {
            if (!startWriting) {
                // Check if this chunk contains the start of the file data
                int fileStartIndex = indexOf(buffer, "\r\n\r\n".getBytes("UTF-8"));
                if (fileStartIndex != -1) {
                    // Start writing from the end of the header
                    fileOutputStream.write(buffer, fileStartIndex + 4, bytesRead - fileStartIndex - 4);
                    startWriting = true;
                }
            } else {
                // Look for boundary in the buffer
                int boundaryIndex = indexOf(buffer, boundaryBytes);
                if (boundaryIndex >= 0) {
                    // Write data before the boundary
                    fileOutputStream.write(buffer, 0, boundaryIndex);
                    break;
                } else {
                    fileOutputStream.write(buffer, 0, bytesRead);
                }
            }
        }

        fileOutputStream.close();

    }

    private int indexOf(byte[] buffer, byte[] pattern) {
        for (int i = 0; i < buffer.length - pattern.length; i++) {
            boolean found = true;
            for (int j = 0; j < pattern.length; j++) {
                if (buffer[i + j] != pattern[j]) {
                    found = false;
                    break;
                }
            }
            if (found)
                return i;
        }
        return -1;
    }

}
