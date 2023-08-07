import org.junit.jupiter.api.Assertions;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

class UsersEndpointsTest {

    //The following test sends a GET request that returns a JSON with 3 users per_page starting from page 2.
    @Test
    void getUsersListTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://reqres.in/api/users?page=2&per_page=3")).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertTrue(response.body().contains("\"page\":2,\"per_page\":3"));
    }

    //The following test sends a GET request that returns a JSON with user data matching id 3
    @Test
    void getUserByIdTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://reqres.in/api/users/?id=3")).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertTrue(response.body().contains("\"first_name\":\"Emma\""));
    }

    /*The following test sends a POST request with data to create a user(username,email,mail)
     * and returns a JSON with a String indicating that Only defined users can succeed registration
     */
    @Test
    void postUserTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://reqres.in/api/register"))
                .header("content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{ \"username\":\"name\", \"email\":\"mail\", \"password\":\"pass\" }")).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertTrue(response.body().contains("\"error\":\"Note: Only defined users succeed registration\""));
    }

    /*The following test sends a PUT request with NO data to update a user with id = 3
     * and returns a JSON with a String indicating the time of the update
     */
    @Test
    void putUserByIdTest() throws IOException, InterruptedException {
        String endpointAdaptedToURISyntax = "https://reqres.in/api/users/?id=3";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().PUT(HttpRequest.BodyPublishers.noBody()).uri(URI.create(endpointAdaptedToURISyntax)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //Both Expected and actual results must be passed to a StringBuilder to leave time as HH-mm
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        StringBuilder expected = new StringBuilder();
        expected.append("{\"updatedAt\":\"");
        expected.append(LocalDateTime.now(ZoneOffset.UTC).format(formatter));
        //With that format there is no T in the expected result. Since the actual result has it I'll add it manually.
        expected.replace(24, 25, "T");
        expected.append("\"}");

        StringBuilder actual = new StringBuilder();
        actual.append(response.body());
        actual.replace(30, actual.length() - 2, "");

        Assertions.assertEquals(expected.toString(), actual.toString());
    }

    //The following test sends a DELETE request with id = 3 to delete a user with that id
    @Test
    void deleteUserById() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .DELETE().uri(URI.create("https://reqres.in/api/users/?id=3")).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //Again The status code of the delete response must be 204
        Assertions.assertEquals(204, response.statusCode());
    }

}
