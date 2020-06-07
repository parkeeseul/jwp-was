package http.controller.user;

import http.HttpStatus;
import http.TestConstant;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.ResponseHeader;
import model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.UserData;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class UserLoginControllerTest {
    private HttpRequest request;
    private HttpResponse response;
    private User user;

    @BeforeEach
    void setUp() throws IOException {
        request = HttpRequest.getInstance(new ByteArrayInputStream(TestConstant.USER_LOGIN_REQUEST.getBytes()));
        response = new HttpResponse();
        user = new User("seul", "test", "Eeseul Park", "seul");
        UserData.save(user);
    }

    @Test
    void doPost_logInSuccess() {
        user = new User("seul", "test", "Eeseul Park", "seul");
        UserData.save(user);

        LoginController controller = new LoginController();
        controller.doPost(request, response);

        ResponseHeader responseHeader = response.getHeader();

        assertThat(responseHeader.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(responseHeader.getContentType()).isEqualTo("text/html");
        assertThat(responseHeader.getCustomHeader().get("Set-Cookie")).isEqualTo("logined=true; Path=/");
    }

    @Test
    void doPost_logInFail() {
        user = new User("seul", "test123", "Eeseul Park", "seul");
        UserData.save(user);

        LoginController controller = new LoginController();
        controller.doPost(request, response);

        ResponseHeader responseHeader = response.getHeader();

        assertThat(responseHeader.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(responseHeader.getContentType()).isEqualTo("text/html");
        assertThat(responseHeader.getCustomHeader().get("Set-Cookie")).isEqualTo("logined=false; Path=/");
    }
}
