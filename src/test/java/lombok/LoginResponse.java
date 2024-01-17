package lombok;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class LoginResponse {
    private String token = "QpwL5tke4Pnpja7X4";
}


