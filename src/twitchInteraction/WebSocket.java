package twitchInteraction;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

public class WebSocket extends WebSocketClient {
    public WebSocket(URI serverURI) {
        super(serverURI);
    }

    public void twitchConnect(boolean listen) {
        if (listen) {
            connect();
        }
        if (!listen && !this.isClosed()) {
            close();
        }
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        send("connect");
    }

    @Override
    public void onMessage(String message) {
        JSONObject jsonObject = new JSONObject(message);

        if (jsonObject.keySet().contains("x")) {
            int x = (int) (jsonObject.getFloat("x") * 1680);
            int y = (int) (jsonObject.getFloat("y") * 1050);
            TwitchListen.x_coordinates = x;
            TwitchListen.y_coordinates = y;
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
}
