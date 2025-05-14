package ppu.momen.healthcare.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ReadConcern;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class MongoConfig {

    private final Environment env;

    public MongoConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public MongoClientSettings mongoClientSettings() {
        return MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(
                        env.getProperty("spring.data.mongodb.uri")))
                .writeConcern(WriteConcern.MAJORITY)
                .readConcern(ReadConcern.MAJORITY)
                .build();
    }

    @Bean
    public MongoClient mongoClient(MongoClientSettings settings) {
        return MongoClients.create(settings);
    }
}

