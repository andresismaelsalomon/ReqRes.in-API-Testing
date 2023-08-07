import org.junit.jupiter.api.Assertions;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SessionEndpointsTest {

    /*The Following test sends a POST request with a JSON of user(username,email,password) to create a session
    for that user, but it will return that the user is not found
     */
    @Test
    void postSessionTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://reqres.in/api/login"))
                .header("content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{ \"username\":\"name\", \"email\":\"mail\", \"password\":\"pass\" }")).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertTrue(response.body().contains("\"user not found\""));
    }

    //The Following test sends a POST request with a JSON of user(username,email,password) to logout.
    @Test
    void postLogoutTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://reqres.in/api/login"))
                .header("content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{ \"username\":\"name\", \"email\":\"mail\", \"password\":\"pass\" }")).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertTrue(response.body().contains("\"user not found\""));
    }
}
