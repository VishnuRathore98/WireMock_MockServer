package recording;

import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;

public class RecordingWithWireMockServer {
    public static void main(String[] args) {
        WireMockServer wireMockServer = new WireMockServer();
        configureFor("localhost",8080);
        wireMockServer.start();
        wireMockServer.startRecording("https://www.google.com/");
//        wireMockServer.stopRecording();
//        wireMockServer.stop();
    }
}
