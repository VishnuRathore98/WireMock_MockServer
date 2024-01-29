package errors;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.http.Fault;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class Error_Fault_Simulation_WireMock {
    public static void main(String[] args) {
        WireMockServer wireMockServer = new WireMockServer();
        configureFor("localhost",8080);
        wireMockServer.start();

//        /serverError
        serverErrorResponse();
//        /malformedResponse
        malformedResponse();
//       /customStatusCode
        customStatusCodes();
//        /emptyResponse
        emptyResponse();
//        /randomResponse
        randomResponse();
//        /connectionReset
        connectionReset();

    }

//  Return a server error response with HTTP status code 500 to simulate server error or unexpected situations.
    static void serverErrorResponse(){

        stubFor(get(urlPathEqualTo("/serverError")).willReturn(serverError()));
    }

//  Generate a response with a format that does not match the expected structure.
    static void malformedResponse(){
        stubFor(post(urlPathEqualTo("/malformedResponse")).willReturn(aResponse().withFault(Fault.MALFORMED_RESPONSE_CHUNK)));
    }

//  Return responses with custom HTTP status codes to simulate non-standard or unexpected situations.
    static void customStatusCodes(){
        stubFor(get(urlPathEqualTo("/customStatusCode"))
            .willReturn(aResponse()
                .withStatus(418)  // Teapot status code, for example
                .withBody("I'm a teapot")));
    }

//  Simulate a situation where the server returns an empty response.
    static void emptyResponse(){
        stubFor(get(urlPathEqualTo("/emptyResponse"))
            .willReturn(aResponse()
                .withStatus(200)  // OK status code
                .withBody("")));
    }

//  Create a stub that returns random responses to test how your application handles unpredictability.
    static void randomResponse(){
        stubFor(get(urlPathEqualTo("/randomResponse"))
            .willReturn(aResponse()
                .withStatus(200)
                .withBody("Incomplete response")
                .withFault(Fault.RANDOM_DATA_THEN_CLOSE)));
    }

//  Simulate a situation where the server resets the connection unexpectedly.
    static void connectionReset(){
        stubFor(get(urlPathEqualTo("/connectionReset"))
            .willReturn(aResponse()
                .withStatus(200)
                .withBody("Connection reset")
                .withFault(Fault.CONNECTION_RESET_BY_PEER)));

    }
}
