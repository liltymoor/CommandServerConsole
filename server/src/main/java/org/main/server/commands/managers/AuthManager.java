package org.main.server.commands.managers;

import org.main.server.commands.Command;
import org.main.server.commands.properties.NotAuthorizable;
import org.main.server.fs.CollectionIO;
import org.main.server.fs.UserAuthResult;
import org.main.server.network.JwtTokenManager;

public class AuthManager {
    public AuthManager() {
    }

    public String authenticate(String Token) {
        return JwtTokenManager.validateToken(Token);
    }
}
