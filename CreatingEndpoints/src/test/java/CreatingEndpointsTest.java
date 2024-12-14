
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.javalin.Javalin;

public class CreatingEndpointsTest {
    Javalin app = JavalinSingleton.getInstance();
    HttpClient webClient;

    @Before
    public void beforeEach() throws InterruptedException {
        webClient = HttpClient.newHttpClient();
        app.start(9001);
    }

    @After
    public void afterEach() {
        app.stop();
    }

    @Test
    public void shouldAnswerWithTrue() throws IOException, InterruptedException {
        String expectedResult = "Hello World";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/hello"))
                .GET()
                .build();

        HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
        Assert.assertFalse("Response body must not be empty", response.body().isEmpty());
        Assert.assertEquals("Response body should match 'Hello World'", expectedResult, response.body());
    }
}
