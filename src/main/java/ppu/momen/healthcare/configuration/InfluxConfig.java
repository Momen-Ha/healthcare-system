package ppu.momen.healthcare.configuration;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class InfluxConfig {
    @Value("${spring.influxdb.url}")
    private String url;

    @Value("${spring.influxdb.token}")
    private String token;

    @Value("${spring.influxdb.org}")
    private String org;

    @Value("${spring.influxdb.bucket}")
    private String bucket;

    @Bean
    public InfluxDBClient influxDBClient() {
        return InfluxDBClientFactory.create(url, token.toCharArray(), org, bucket);
    }

    @Bean
    public WriteApiBlocking writeApi(InfluxDBClient client) {
        return client.getWriteApiBlocking();
    }
}
