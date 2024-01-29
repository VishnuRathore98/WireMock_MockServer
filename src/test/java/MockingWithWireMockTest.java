import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.Json;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class MockingWithWireMockTest {
    @BeforeAll
    static void setup(){
        WireMockServer wireMockServer = new WireMockServer();
        configureFor("localhost",8080);
        wireMockServer.start();
    }
    @Test
    void stubPost(){
        stubFor(post(urlPathEqualTo("/payments"))
            .withRequestBody(equalToJson("{" +
                "\"cardNumber:\":\"1111-1111-1111-1111\"," +
                "\"cardExpiryDate\":\"2024-02-21\"," +
                "\"ammount\":\"5000\"}"))
            .willReturn(okJson("{" +
                "\"paymentID\":\"4343\"," +
                "\"paymentResponseStatus\":\"SUCCESS\"}")));
    }
}
