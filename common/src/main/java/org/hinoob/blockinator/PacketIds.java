package org.hinoob.blockinator;

public class PacketIds {

    public static class SERVER_TO_CLIENT {
        public static int AUTH_RESPONSE = 0;
        public static int WORLD_RESPONSE = 1;
    }

    public static class CLIENT_TO_SERVER {
        public static int AUTH = 0;
        public static int FETCH_WORLD = 1;
    }
}
