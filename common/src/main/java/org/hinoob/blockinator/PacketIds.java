package org.hinoob.blockinator;

public class PacketIds {

    public static class SERVER_TO_CLIENT {
        public static int AUTH_RESPONSE = 0;
        public static int WORLD_RESPONSE = 1;
        public static int STATUS_RESPONSE = 2;
        public static int PLAYERS = 3;

    }

    public static class CLIENT_TO_SERVER {
        public static int AUTH = 0;
        public static int JOIN_WORLD = 1;
        public static int POSITION = 2;
        public static int STATUS = 3;
    }
}
