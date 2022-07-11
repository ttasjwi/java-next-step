package tdd;

import java.util.Objects;

public class StringCalculator {

    public int add(String text) {
        if (Objects.isNull(text) || text.isBlank()) {
            return 0;
        }
        return Integer.parseInt(text);
    }
}
