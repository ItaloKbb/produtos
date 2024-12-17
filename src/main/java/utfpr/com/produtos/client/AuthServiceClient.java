package utfpr.com.produtos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "auth-service", url = "http://localhost:9090/api")
public interface AuthServiceClient {
    @PostMapping("/users/validate")
    Boolean validateBasicAuth(@RequestHeader("Authorization") String basicAuthHeader);
}
