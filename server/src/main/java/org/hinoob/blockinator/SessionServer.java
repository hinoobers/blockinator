package org.hinoob.blockinator;

import java.util.HashMap;
import java.util.Map;

public class SessionServer {

    private final Map<String, Integer> sessions = new HashMap<>(); // TOKEN - Account id

    public String generate(int accountId) {
        String token = generateToken();
        sessions.put(token, accountId);
        return token;
    }

    public int getAccountId(String token) {
        return sessions.get(token);
    }

    private String generateToken() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder token = new StringBuilder();
        for(int i = 0; i < 64; i++) {
            token.append(chars.charAt((int) (Math.random() * chars.length())));
        }
        return token.toString();
    }
}
