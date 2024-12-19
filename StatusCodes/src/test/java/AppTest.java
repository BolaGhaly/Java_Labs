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

public class AppTest {
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
    public void problem1Test() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/client-side-err"))
                .GET()
                .build();

        HttpResponse<Void> response = webClient.send(request, HttpResponse.BodyHandlers.discarding());

        int statusCode = response.statusCode();
        Assert.assertTrue(
                "Response status code should be in the 400 range (client-side error)",
                statusCode >= 400 && statusCode < 500
        );
    }
}
