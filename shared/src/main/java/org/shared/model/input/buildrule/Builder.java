package org.shared.model.input.buildrule;

import org.shared.model.input.FieldStructure;

import java.io.IOException;
import java.util.Scanner;

public abstract class Builder<T> {
    String name = "BUILDER";

    public String getName() {
        return name;
    }

    public static void incorrectMessage() {
        System.out.println("[HOST] Incorrect input\n");
    }

    public static String ask(Scanner input, FieldStructure rule) {
        String formattedTask = String.format("Field: %s | %s [ %s ]:", rule.getName(), rule, rule.getDefaultValue());
        System.out.print(formattedTask);

        return input.nextLine();
    }

    public static String checkNullable(String field, FieldStructure rule) {
        if (field.isEmpty() && !rule.isNullable())
            field = rule.getDefaultValue();
        else return null; // Случай если строка "" и при этом можно иметь null

        return field;
    }

    public abstract T build(Scanner input) throws IOException;
}
