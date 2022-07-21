package http.common;

import lombok.Getter;
import util.Pair;

@Getter
public class Cookie {

    private String key;
    private String value;
    private Integer maxAge;

    public Cookie(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Cookie(Pair<String, String> pair) {
        this.key = pair.getKey();
        this.value = pair.getValue();
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

}
