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

class ResourceEndpointsTest {

    //The following test sends a GET request that returns a JSON with 7 resource items per_page starting from page one.
    @Test
    void getResourcesListTest() throws IOException, InterruptedException {
        String endpointAdaptedToURISyntax = "https://reqres.in/api/%7Bresource%7D?page=1&per_page=7";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(endpointAdaptedToURISyntax)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertTrue(response.body().contains("\"page\":1,\"per_page\":7"));
    }

    //The following test sends a GET request that returns a JSON with data matching id = 3
    @Test
    void getResourceByIdTest() throws IOException, InterruptedException {
        String endpointAdaptedToURISyntax = "https://reqres.in/api/%7Bresource%7D?id=3";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(endpointAdaptedToURISyntax)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertTrue(response.body().contains("\"id\":3"));
    }

    /*The following test sends a PUT request to update a resource with id 2 and returns a JSON with a String
      indicating time of update.
    */
    @Test
    void putResourceByIdTest() throws IOException, InterruptedException {
        String endpointAdaptedToURISyntax = "https://reqres.in/api/%7Bresource%7D/?id=2";

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

    //The following test sends a DELETE request.
    @Test
    void deleteResourceByIdTest() throws IOException, InterruptedException {
        String endpointAdaptedToURISyntax = "https://reqres.in/api/%7Bresource%7D/?id=4";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().DELETE()
                .uri(URI.create(endpointAdaptedToURISyntax)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //The status code of the response must be 204
        Assertions.assertEquals(204, response.statusCode());
    }

}
