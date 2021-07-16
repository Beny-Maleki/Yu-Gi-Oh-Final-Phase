package client.network;

public class NetworkConfiguration {
    private static int port;
    private static final String HOST;


    //default val
    static {
        port = 7755;
        HOST = "localhost";
    }


    public static int getPort() {
        return port;
    }

    private static void setPort(String port) {
        NetworkConfiguration.port = Integer.parseInt(port);

    }

    public static String getHost() {
        return HOST;
    }
}
