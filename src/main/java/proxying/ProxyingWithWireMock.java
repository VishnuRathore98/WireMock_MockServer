package proxying;

import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class ProxyingWithWireMock {
    public static void main(String[] args) {
        WireMockServer wireMockServer = new WireMockServer();
        configureFor("localhost",8080);
        wireMockServer.start();
        stubFor(any(anyUrl()).willReturn(aResponse().proxiedFrom("https://www.google.com/")));
    }
}
