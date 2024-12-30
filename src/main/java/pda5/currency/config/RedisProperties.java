package pda5.currency.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Getter
@PropertySource("application.yml")
public class RedisProperties {
    @Value("${redis.port}")
    private int port;
    @Value("${redis.host}")
    private String host;
}
