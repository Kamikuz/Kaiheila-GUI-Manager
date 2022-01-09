package cn.kamikuz.kaiheiguimanager.service.bilibli;

import cn.kamikuz.kaiheiguimanager.utils.io.http.HttpRequest;
import cn.kamikuz.kaiheiguimanager.utils.io.ws.WSClient;

import java.io.IOException;
import java.net.URI;

public class BWSClient {
    public static final String GATEWAY_ROOT = "https://api.live.bilibili.com/room/v1/Danmu/getConf?room_id=%d&platform=pc&player=web";
    Gateway gateway;
    WSClient wsClient;

    public boolean init(int roomId){
        if (roomId <= 0) {
            return false;
        }
        if (gateway == null) {
            try {
                gateway = HttpRequest.GET(String.format(GATEWAY_ROOT, roomId)).getJson(Gateway.class);
            } catch (IOException e) {
                gateway = null;
                e.printStackTrace();
                return false;
            }
            if (gateway.code != 0) {
                gateway = null;
                return false;
            }
        }
        return true;
    }

    public void establishConnection() {
        if (gateway != null) {
            for (Gateway.HostServer hostServer : gateway.data.host_server_list) {
                wsClient = new WSClient(URI.create(String.format("ws://%s:%d/sub", hostServer.host, hostServer.ws_port)));
                wsClient.connect();
            }
        }
    }
}
