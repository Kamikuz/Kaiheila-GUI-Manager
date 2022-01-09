package cn.kamikuz.kaiheiguimanager.service.bilibli;

import java.util.Arrays;

public class Gateway {
  int code;
  String message;
  String msg;
  Data data;

  public static class Data {
    float refresh_row_factor;
    int refresh_rate;
    int max_delay;
    int port;
    String host;
    HostServer[] host_server_list;
    Server[] server_list;
    String token;

    @Override
    public String toString() {
      return "Data{" +
          "refresh_row_factor=" + refresh_row_factor +
          ", refresh_rate=" + refresh_rate +
          ", max_delay=" + max_delay +
          ", port=" + port +
          ", host='" + host + '\'' +
          ", host_server_list=" + Arrays.toString(host_server_list) +
          ", server_list=" + Arrays.toString(server_list) +
          ", token='" + token + '\'' +
          '}';
    }
  }

  public static class HostServer {
    String host;
    int port;
    int wss_port;
    int ws_port;

    @Override
    public String toString() {
      return "HostServer{" +
          "host='" + host + '\'' +
          ", port=" + port +
          ", wss_port=" + wss_port +
          ", ws_port=" + ws_port +
          '}';
    }
  }

  public static class Server {
    String host;
    int port;

    @Override
    public String toString() {
      return "Server{" +
          "host='" + host + '\'' +
          ", port=" + port +
          '}';
    }
  }
}


