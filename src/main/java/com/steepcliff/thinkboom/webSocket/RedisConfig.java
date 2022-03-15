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

    // BrainWriting을 위한 Topic 설정
    @Bean
    public ChannelTopic BwChannelTopic() {
        return new ChannelTopic("BwMessageResponseDto");
    }

    // SixHat을 위한 Topic 설정
    @Bean
    public ChannelTopic ShChannelTopic() {
        return new ChannelTopic("");
    }

    @Bean
    public RedisMessageListenerContainer BwRedisMessageListener(RedisConnectionFactory connectionFactory,
                                                                MessageListenerAdapter BwListenerAdapter,
                                                                ChannelTopic BwChannelTopic) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(BwListenerAdapter, BwChannelTopic);
        return container;
    }

    @Bean
    public RedisMessageListenerContainer ShRedisMessageListener(RedisConnectionFactory connectionFactory,
                                                                MessageListenerAdapter ShListenerAdapter,
                                                                ChannelTopic ShChannelTopic) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(ShListenerAdapter, ShChannelTopic);
        return container;
    }



    // 이 곳으로 메시지가 던져짐.
    @Bean
    public MessageListenerAdapter BwListenerAdapter(RedisSubscriber subscriber) {
        return new MessageListenerAdapter(subscriber, "BwSendMessage");
    }

    @Bean
    public MessageListenerAdapter ShListenerAdapter(RedisSubscriber subscriber) {
        return new MessageListenerAdapter(subscriber, "");
    }
}
