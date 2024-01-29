package timeoutdelays;

import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class Timeout_Delay_Wiremock {
    public static void main(String[] args) {
        WireMockServer wireMockServer = new WireMockServer();
        configureFor("localhost",8080);
        wireMockServer.start();

//        /fixedDelay
        fixedDelay();

//        /randomDelay
        randomDelay();

//        /logNormalRandomDelay
        logNormalRandomDelay();

//        /chunkedDribbleDelay
        chunkedDribbleDelay();
    }

//  Introduce delays to simulate slow responses. This helps test how your application handles timeouts.
//  This will cause the response to be delayed by 2secs.
    static void fixedDelay(){
        stubFor(get(urlPathEqualTo("/fixedDelay"))
            .willReturn(ok("Better late than never...")
                .withFixedDelay(2000)));
    }

//  Simulate variability in response times, useful for testing how your application handles unpredictable delays.
    static void randomDelay(){
        stubFor(get(urlPathEqualTo("/randomDelay"))
            .willReturn(aResponse()
                .withUniformRandomDelay(1000, 5000)  // Random delay between 1 and 5 seconds
                .withStatus(200)
                .withBody("Random delayed response")));

    }

//  Simulate delays with a log-normal distribution, useful for testing scenarios where there is a wide range of response times.
    static void logNormalRandomDelay(){
        stubFor(get(urlPathEqualTo("/logNormalRandomDelay"))
            .willReturn(aResponse()
                .withLogNormalRandomDelay(2000, 1.0)  // Median delay of 3 seconds, scale parameter of 1.0
                .withStatus(200)
                .withBody("Log-normal delayed response")));
    }

//  For simulating testing of how your application handles responses that are delivered in chunks over time.
    static void chunkedDribbleDelay(){
        stubFor(get(urlPathEqualTo("/chunkedDribbleDelay"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("{\"chunk\": \"data1\",\"chunk\": \"data2\",\"chunk\": \"data3\",\"chunk\": \"data4\",\"chunk\": \"data5\",\"chunk\": \"data6\",\"chunk\": \"data7\",\"chunk\": \"data8\"}")
                .withChunkedDribbleDelay(5, 500)  // 1-second delay between each chunk, with a random delay of up to 500 milliseconds

            ));

        // Now, when your application makes a GET request to "/api/stream", it will receive the response in chunks with delays.
    }
}
