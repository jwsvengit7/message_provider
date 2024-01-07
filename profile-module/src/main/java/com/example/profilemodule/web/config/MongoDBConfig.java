package com.example.profilemodule.web.config;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.example.profilemodule.domain.repository")
public class MongoDBConfig extends AbstractMongoClientConfiguration {
    @Value("${spring.data.mongodb.uri}")
    private String dataUrl;
    @Value("${spring.data.mongodb.database}")
    private String DBName;

    @Override
    protected String getDatabaseName() {
        return DBName;
    }

    @Override
    @Bean
    public MongoClient mongoClient() {
       return MongoClients.create(dataUrl);
    }
}
