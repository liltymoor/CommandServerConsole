package org.shared.model.input.buildrule;

import org.shared.model.input.FieldStructure;
import org.shared.network.User;

import java.io.IOException;
import java.util.Scanner;

public class UserBuilder extends Builder<User> {
    private String getUsername(Scanner input, FieldStructure struct) {
        String userInput = ask(input, struct);

        while (true) {
            if (!userInput.isEmpty()) return userInput;
            incorrectMessage();
            userInput = ask(input, struct);
        }

    }

    private String getPassword(Scanner input, FieldStructure struct) {
        String userInput = ask(input, struct);

        while (true) {
            if (!userInput.isEmpty()) return userInput;
            incorrectMessage();
            userInput = ask(input, struct);
        }

    }

    @Override
    public User build(Scanner input) throws IOException {
        return new User(
                getUsername(input, new FieldStructure("username", "").notNullable()),
                getPassword(input, new FieldStructure("password", "").notNullable())
        );
    }
}
