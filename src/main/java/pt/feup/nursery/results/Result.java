package pt.feup.nursery.results;

import java.util.Objects;

public class Result<String, E extends Throwable> {
    private final String value;
    private final E error;

    private Result(String value, E error) {
        this.value = value;
        this.error = error;
    }

    public static <String, E extends Throwable> Result<String, E> failure(E error) {
        return new Result<>(null, Objects.requireNonNull(error));
    }

    public static <String, E extends Throwable> Result<String, E> success(String value) {
        return new Result<>(Objects.requireNonNull(value), null);
    }

    public String getValue() {
        return value;
    }

    public E getError() {
        return error;
    }
}
