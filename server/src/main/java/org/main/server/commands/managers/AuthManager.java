package org.main.server.commands.managers;

import org.main.server.network.JwtTokenManager;

public class AuthManager {
    public AuthManager() {
    }

    public String authenticate(String Token) {
        return JwtTokenManager.validateToken(Token);
    }
}
