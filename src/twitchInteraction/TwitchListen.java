package twitchInteraction;

import java.net.URI;
import java.net.*;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import org.json.*;

public class TwitchListen extends WebSocketClient {
    public TwitchListen(URI serverURI) {
        super(serverURI);
    }
    public static int x_coordinates = 0;
    public static int y_coordinates = 0;

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        send("connect");
        System.out.println("connected");
    }

    @Override
    public void onMessage(String message) {
        JSONObject jsonObject = new JSONObject(message);

        if (jsonObject.keySet().contains("x")) {
            int x = (int)(jsonObject.getFloat("x")*1680);
            int y = (int)(jsonObject.getFloat("y")*1050);
            x_coordinates = x;
            y_coordinates = y;
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        // The codes are documented in class org.java_websocket.framing.CloseFrame
        System.out.println(
                "Connection closed by " + (remote ? "remote peer" : "us") + " Code: " + code + " Reason: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    // be aware : use heat-api.j38.net  NOT heat-ebs.j38.net
    public static void twitchListen(boolean listen) {
        try {
            TwitchListen clientWebSocket = new TwitchListen(new URI(
                    "wss://heat-api.j38.net/channel/462818643"));

            if (listen) {
                clientWebSocket.connect();
            }
            if (!listen && !clientWebSocket.isClosed()) {
                clientWebSocket.close();
            }
        }
        catch (URISyntaxException uriSyntaxException) {
            System.out.println(uriSyntaxException);
        }
    }
}