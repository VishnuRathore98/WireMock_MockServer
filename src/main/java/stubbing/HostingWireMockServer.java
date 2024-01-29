package stubbing;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.Json;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class HostingWireMockServer {

    public static void main(String[] args) {

//      Initializing WireMockServer
        WireMockServer wireMockServer = new WireMockServer();
//      Setup server configuration
        configureFor("localhost",8080);
//      Start the server
        wireMockServer.start();
//      What we have to return
        stubFor(any((anyUrl())).willReturn(ok("Hello mocking!!")));

//        stubPostForSpecificRequestBody();
//        This will cover all the cases even for defined specific Request body.
//        stubPostForAnyRequestBody();

//        stubGetForSpecificRequestURL();
//        This will cover all the cases even for defined specific Request url.
//        stubGetForAnyRequestURL();
    }

    public static void stubPostForSpecificRequestBody(){

//      When we pass the following post request, with the defined json body:
        stubFor(post(urlPathEqualTo("/payments")).atPriority(1)
            .withRequestBody(equalToJson("{" +
                "\"cardNumber:\":\"1111-1111-1111-1111\"," +
                "\"cardExpiryDate\":\"2024-02-21\"," +
                "\"amount\":\"5000\"" +
                "}"))
//      We will get the following response:
            .willReturn(okJson("{" +
                "\"paymentID\":\"4343\"," +
                "\"paymentResponseStatus\":\"SUCCESS\"}")));
    }
    public static void stubGetForSpecificRequestURL(){
//      By setting priority we could alter the program flow now this will be checked first as it has higher priority.
        stubFor(get(urlPathEqualTo("/fraudCheck/1111-1111-1111-1112")).atPriority(1)
            .willReturn(okJson("{" +
                "\"blackListed:\":\"true\"}")));
    }

//  In this post method we can pass any data in the request body, and we will get status SUCCESS for all.
//  Superset of specific Request Body. Until unless we set priority.
    public static void stubPostForAnyRequestBody(){
        stubFor(post(urlPathEqualTo("/payments")).atPriority(2)
            .withRequestBody(
                matchingJsonPath("cardNumber"))
            .withRequestBody(
                matchingJsonPath("cardExpiryDate"))
            .withRequestBody(
                matchingJsonPath("amount"))
            .willReturn(
                okJson("{\"paymentResponseStatus\":\"SUCCESS\"}")));
    }

//  In this get method we can pass any data in the request url as a parameter, and it will return false for all.
//  Superset of specific Request url. Until unless we set priority.
    public static void stubGetForAnyRequestURL(){
//      Using regex for url path matching.
        stubFor(get(urlPathMatching("/fraudCheck/.*")).atPriority(2)
            .willReturn(okJson("{\"blackListed\":\"false\"}")));
    }
}