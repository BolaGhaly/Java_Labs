
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AppTest {

    Javalin app = JavalinSingleton.getInstance();
    HttpClient webClient;
    ObjectMapper objectMapper;

    @Before
    public void beforeEach() throws InterruptedException {
        webClient = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();
        app.start(9001);
    }

    @After
    public void afterEach() {
        app.stop();
    }

    private String sendPostRequest(String url, String jsonBody) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
        Assert.assertFalse("Response body must not be empty", response.body().isEmpty());
        return response.body();
    }

    @Test
    public void prob1a() throws IOException, InterruptedException {
        String requestBody = """
        {
            "songName": "Let it be",
            "artistName": "Beatles"
        }
        """;

        String responseBody = sendPostRequest("http://localhost:9001/echo", requestBody);

        Song expected = new Song("Let it be", "Beatles");
        Song actual = objectMapper.readValue(responseBody, Song.class);

        Assert.assertEquals("Response body should match the request body", expected, actual);
    }

    @Test
    public void prob1b() throws IOException, InterruptedException {
        String requestBody = """
        {
            "songName": "Paint it Black",
            "artistName": "Rolling Stones"
        }
        """;
        String responseBody = sendPostRequest("http://localhost:9001/echo", requestBody);

        Song expected = new Song("Paint it Black", "Rolling Stones");
        Song actual = objectMapper.readValue(responseBody, Song.class);

        Assert.assertEquals("Response body should match the request body", expected, actual);
    }

    @Test
    public void prob2a() throws IOException, InterruptedException {
        String requestBody = """
        {
            "songName": "Paint it Black",
            "artistName": "Rolling Stones"
        }
        """;
        String responseBody = sendPostRequest("http://localhost:9001/changeartisttobeatles", requestBody);

        Song expected = new Song("Paint it Black", "Beatles");
        Song actual = objectMapper.readValue(responseBody, Song.class);

        Assert.assertEquals("Response body should match the request body", expected, actual);
    }
}
