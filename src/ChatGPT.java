import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.io.*;
import java.net.*;
import org.json.*;
public class ChatGPT {

    public String processInput(String API_ENDPOINT, String API_KEY, String MODEL)throws Exception{
        // Set request parameters
        String prompt = "";
        int maxTokens = 100;
        
        // for(int i = 1; i < args.length; i++)
        // {
        //     prompt += args[i];
        // }
        // int maxTokens = Integer.parseInt(args[0]);
        
        
        // Create a request body which you will pass into request object
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", MODEL);
        requestBody.put("prompt", prompt);
        requestBody.put("max_tokens", maxTokens);
        requestBody.put("temperature", 1.0);

        // Create the HTTP Client


        HttpClient client = HttpClient.newHttpClient();


        // Create the request object
        HttpRequest request = HttpRequest
        .newBuilder()
        .uri(URI.create(API_ENDPOINT))
        .header("Content-Type", "application/json")
        .header("Authorization", String.format("Bearer %s", API_KEY))
        .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
        .build();


        // Send the request and receive the response
        HttpResponse<String> response = client.send(
        request,
        HttpResponse.BodyHandlers.ofString()
        );

        // Process the response
        String responseBody = response.body();

        JSONObject responseJson = new JSONObject(responseBody);


        JSONArray choices = responseJson.getJSONArray("choices");
        String generatedText = choices.getJSONObject(0).getString("text");


        // System.out.println(generatedText);
        return generatedText;

    }

    // Helper method to write a parameter to the output stream in multipart form data format
    private static void writeParameterToOutputStream(OutputStream outputStream, String parameterName,
        String parameterValue, String boundary) throws IOException {
        outputStream.write(("--" + boundary + "\r\n").getBytes());
        outputStream.write(("Content-Disposition: form-data; name=\"" 
            + parameterName + "\"\r\n\r\n").getBytes());
        outputStream.write((parameterValue + "\r\n").getBytes());
    }

    // Helper method to write a file to the output stream in multipart form data format
    private static void writeFileToOutputStream(OutputStream outputStream, 
        File file,String boundary) throws IOException {
        outputStream.write(("--" + boundary + "\r\n").getBytes());
        outputStream.write(
        ("Content-Disposition: form-data; name=\"file\"; filename=\"" + 
            file.getName() + "\"\r\n").getBytes()
        );
        outputStream.write(("Content-Type: audio/mpeg\r\n\r\n").getBytes());
        
        
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        fileInputStream.close();
    }
    
    // Helper method to handle a successful response
    private static String handleSuccessResponse(HttpURLConnection connection)
    throws IOException, JSONException {
        BufferedReader in = new BufferedReader(
            new InputStreamReader(connection.getInputStream())
        );
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();


        JSONObject responseJson = new JSONObject(response.toString());


        String generatedText = responseJson.getString("text");


        // Print the transcription result
        // System.out.println("Transcription Result: " + generatedText);
        return generatedText;
    }

    // Helper method to handle an error response
    private static String handleErrorResponse(HttpURLConnection connection)
    throws IOException, JSONException {
        BufferedReader errorReader = new BufferedReader(
            new InputStreamReader(connection.getErrorStream())
        );
        String errorLine;
        StringBuilder errorResponse = new StringBuilder();
        while ((errorLine = errorReader.readLine()) != null) {
            errorResponse.append(errorLine);
        }
        errorReader.close();
        String errorResult = errorResponse.toString();
        // System.out.println("Error Result: " + errorResult);
        return errorResult;
    }

    public String handleVoiceInput(File file, String API_ENDPOINT, String TOKEN, String MODEL) throws IOException, URISyntaxException{
        URL url = new URI(API_ENDPOINT).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        
        
        // Set up request headers
        String boundary = "Boundary-" + System.currentTimeMillis();
        connection.setRequestProperty(
            "Content-Type",
            "multipart/form-data; boundary=" + boundary
        );
        connection.setRequestProperty("Authorization", "Bearer " + TOKEN);
        
        
        // Set up output stream to write request body
        OutputStream outputStream = connection.getOutputStream();
        
        
        // Write model parameter to request body
        writeParameterToOutputStream(outputStream, "model", MODEL, boundary);
        
        
        // Write file parameter to request body
        writeFileToOutputStream(outputStream, file, boundary);
        
        
        // Write closing boundary to request body
        outputStream.write(("\r\n--" + boundary + "--\r\n").getBytes());
        
        
        // Flush and close output stream
        outputStream.flush();
        outputStream.close();
        
        
        // Get response code
        int responseCode = connection.getResponseCode();
        
        String result;
        // Check response code and handle response accordingly
        if (responseCode == HttpURLConnection.HTTP_OK) {
            result = handleSuccessResponse(connection);
        } else {
            result = handleErrorResponse(connection);
        }
        

        // Disconnect connection
        connection.disconnect();

        return result;
    } 
}
