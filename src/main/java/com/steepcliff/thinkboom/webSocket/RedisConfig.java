package com.steepcliff.thinkboom.webSocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Slf4j
@Configuration
public class RedisConfig {

    // 어플리케이션에서 사용할 redisTemplate 설정
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));
        return redisTemplate;
    }

    @Bean
    public ChannelTopic ChannelTopic() {

        return new ChannelTopic("thinkBoom");
    }


    @Bean
    public RedisMessageListenerContainer BwRedisMessageListener(RedisConnectionFactory connectionFactory,
                                                                MessageListenerAdapter BwListenerAdapter,
                                                                ChannelTopic ChannelTopic) {
        log.info("brain writing messageListener start");
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(BwListenerAdapter, ChannelTopic);
        return container;
    }

    @Bean
    public RedisMessageListenerContainer ShRedisMessageListener(RedisConnectionFactory connectionFactory,
                                                                MessageListenerAdapter ShListenerAdapter,
//                                                                MessageListenerAdapter BwListenerAdapter,
                                                                ChannelTopic ChannelTopic) {
        log.info("six hat messageListener start");
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(ShListenerAdapter, ChannelTopic);
//        container.addMessageListener(BwListenerAdapter, ChannelTopic);
        return container;
    }



    // 이 곳으로 메시지가 던져짐.
    @Bean
    public MessageListenerAdapter BwListenerAdapter(RedisSubscriber subscriber) {
        ;
        log.info("BwListenerAdapter");
        return new MessageListenerAdapter(subscriber, "BwSendMessage");
    }

    @Bean
    public MessageListenerAdapter ShListenerAdapter(RedisSubscriber subscriber) {
        log.info("ShListenerAdapter");

        return new MessageListenerAdapter(subscriber, "ShSendMessage");
    }
}
