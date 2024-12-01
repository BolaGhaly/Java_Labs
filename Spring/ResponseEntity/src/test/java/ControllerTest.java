import Lab.Controller.Controller;
import Lab.Model.Sample;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ControllerTest {

    @Test
    public void testLab1() {
        Controller controller = new Controller();
        ResponseEntity response = controller.lab1();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Bad Request", response.getBody());
    }

    @Test
    public void testLab2() {
        Controller controller = new Controller();
        Sample sample = new Sample();
        ResponseEntity response = controller.lab2(sample);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(sample, response.getBody());
        assertEquals("100", response.getHeaders().get("content-length").get(0));
    }
}
