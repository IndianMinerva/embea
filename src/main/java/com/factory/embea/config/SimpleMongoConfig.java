package com.factory.embea.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class SimpleMongoConfig {
    /*@Value("${spring.data.mongodb.host}")
    private String mongoHost;

    @Value("${spring.data.mongodb.port}")
    private String mongoPort;

    @Value("${spring.data.mongodb.database}")
    private String mongoDatabase;*/

    @Bean
    public MongoClient mongo() {
        System.out.println("***************************************************************");
        //System.out.println("mongodb://" + mongoHost + ":" + mongoPort + "/" + mongoDatabase);
        System.out.println("***************************************************************");

        //ConnectionString connectionString = new ConnectionString("mongodb://" + mongoHost + ":" + mongoPort + "/" + mongoDatabase);
        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/policy");
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        //return new MongoTemplate(mongo(), mongoDatabase);
        return new MongoTemplate(mongo(), "policy");
    }
}