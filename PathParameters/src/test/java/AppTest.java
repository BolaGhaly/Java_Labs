
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
    HttpClient client = HttpClient.newHttpClient();

    @Before
    public void beforeEach() throws InterruptedException {
        app.start(9001);
    }

    @After
    public void afterEach() {
        app.stop();
    }

    private String sendGetRequest(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    @Test
    public void problem1aTest() throws Exception {
        String expectedResult = "Kevin";
        String actualResult = sendGetRequest("http://localhost:9001/firstname/Kevin");

        if (actualResult.isEmpty()) {
            Assert.fail("No response from server");
        }

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void problem1bTest() throws Exception {
        String expectedResult = "Sam";
        String actualResult = sendGetRequest("http://localhost:9001/firstname/Sam");

        if (actualResult.isEmpty()) {
            Assert.fail("No response from server");
        }

        Assert.assertEquals(expectedResult, actualResult);
    }
}
