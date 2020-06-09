package http;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class HttpSessions {
    private Map<String, HttpSession> httpSessionMap;

    public HttpSessions() {
        this.httpSessionMap = new HashMap<>();
    }

    public void addSession(String key, HttpSession session) {
        this.httpSessionMap.put(key, session);
    }

    public HttpSession getSession(String key) {
        return this.httpSessionMap.get(key);
    }

    public boolean containsKey(String key) {
        return this.httpSessionMap.containsKey(key);
    }
}
