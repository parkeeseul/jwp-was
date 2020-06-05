package http;

import lombok.Getter;

@Getter
public enum HttpStatus {
    OK(200, "OK"),
    FOUND(302, "Found");

    private int code;
    private String name;

    HttpStatus(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
