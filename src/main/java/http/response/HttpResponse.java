package http.response;

import exception.JwpException;
import http.HttpStatus;
import http.request.HttpRequest;
import lombok.Getter;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
public class HttpResponse {
    private ResponseHeader header;
    private byte[] body;

    public HttpResponse() {
    }

    public void body(HttpStatus httpStatus, byte[] data, String contentType) {
        this.body = data;
        this.header = ResponseHeader.of(httpStatus, contentType, data.length);
    }

    public void loadFile(HttpRequest request) {
        try {
            byte[] dataByte = FileIoUtils.loadFileFromClasspath(request.getFilePath());
            this.body = dataByte;
            this.header = ResponseHeader.of(HttpStatus.OK, request.getContentType(), dataByte.length);
        } catch (IOException | URISyntaxException e) {
            throw new JwpException("load file from classpath fail", e);
        }
    }

    public void redirect(String location) {
        this.body = null;
        this.header = ResponseHeader.of(HttpStatus.FOUND, "text/html", 0, location);
    }

    public void methodNotAllowed(HttpRequest request) {
        this.header = ResponseHeader.of(HttpStatus.METHOD_NOT_ALLOWED, request.getContentType(), 0);
    }

    public void notFound(HttpRequest request) {
        this.header = ResponseHeader.of(HttpStatus.NOT_FOUND, request.getContentType(), 0);
    }

    public void addCookie(String key, String value) {
        this.header.setCookie(key, value);
    }

    public String getContentType() {
        return this.header.getContentType();
    }

    public int getContentLength() {
        return this.header.getContentLength();
    }

    public int getStatusCode() {
        return this.header.getStatusCode();
    }

    public String getStatusName() {
        return this.header.getStatusName();
    }

    public Map<String, String> getCustomHeader() {
        return this.header.getCustomHeader();
    }

    public List<String> getCookies() {
        return this.header.getCookies();
    }

    public boolean headerIsNull() {
        return Objects.isNull(this.header);
    }
}
