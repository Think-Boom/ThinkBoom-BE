package com.sparta.sixhat.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


/**
 * 로컬 환경일경우 내장 레디스가 실행됩니다.
 */
//@Profile("local")//로컬환경에서만 사용하도록 지정, 나중에 배포할때 바꿔줘야하는 부분인듯3.34.99.231
@Profile("13.209.77.146")//로컬환경에서만 사용하도록 지정, 나중에 배포할때 바꿔줘야하는 부분인듯3.34.99.231
@Configuration

public class EmbeddedRedisConfig {

    @Value("${spring.redis.port}")
    private int redisPort;

    private RedisServer redisServer;

    @PostConstruct
    public void redisServer() {
        //redisServer = new RedisServer(redisPort);
        //redisServer.start();
        redisServer = RedisServer.builder()
                .port(redisPort)
                .setting("maxmemory 128M")
                .build();
        try {
            redisServer.start();
        }catch (Exception e){

        }
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }
}
