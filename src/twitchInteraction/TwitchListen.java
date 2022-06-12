package twitchInteraction;

import java.net.*;

public class TwitchListen {
    public static int x_coordinates = 0;
    public static int y_coordinates = 0;

    WebSocket webSocket;
    public TwitchListen(String channelID) {
        try {
            // be aware : use heat-api.j38.net  NOT heat-ebs.j38.net
            webSocket = new WebSocket(new URI("wss://heat-api.j38.net/channel/"+channelID));
        }
        catch (URISyntaxException uriSyntaxException) {
            uriSyntaxException.printStackTrace();
        }
    }

    public void setActivated(boolean active) {
        webSocket.twitchConnect(active);
    }
}

