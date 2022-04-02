package com.steepcliff.thinkboom.webSocket;

import com.steepcliff.thinkboom.webSocket.subscriber.BwRedisSubscriber;
import com.steepcliff.thinkboom.webSocket.subscriber.ShRedisSubscriber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
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
    public PatternTopic bwPatternTopic() {
        return new PatternTopic("BW");
    }

    @Bean
    public PatternTopic shPatternTopic() {
        return new PatternTopic("SH");
    }

    @Bean
    public RedisMessageListenerContainer RedisMessageListener(RedisConnectionFactory connectionFactory,
                                                              MessageListenerAdapter BwListenerAdapter,
                                                              MessageListenerAdapter ShListenerAdapter,
                                                              PatternTopic bwPatternTopic,
                                                              PatternTopic shPatternTopic) {
        log.info("brain writing messageListener start");
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        container.addMessageListener(BwListenerAdapter, bwPatternTopic);
        container.addMessageListener(ShListenerAdapter, shPatternTopic);
        return container;
    }

    // 이 곳으로 메시지가 던져짐.
    @Bean
    public MessageListenerAdapter BwListenerAdapter(BwRedisSubscriber subscriber) {

        log.info("BwListenerAdapter");
        return new MessageListenerAdapter(subscriber, "BwSendMessage");
    }

    @Bean
    public MessageListenerAdapter ShListenerAdapter(ShRedisSubscriber subscriber) {
        log.info("ShListenerAdapter");

        return new MessageListenerAdapter(subscriber, "ShSendMessage");
    }











//    @Bean
//    public RedisMessageListenerContainer BwRedisMessageListener(RedisConnectionFactory connectionFactory,
//                                                                MessageListenerAdapter BwListenerAdapter,
//                                                                ChannelTopic channelTopic) {
//        log.info("brain writing messageListener start");
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//
//        container.addMessageListener(BwListenerAdapter, channelTopic);
//        return container;
//    }
//
//    @Bean
//    public RedisMessageListenerContainer ShRedisMessageListener(RedisConnectionFactory connectionFactory,
//                                                                MessageListenerAdapter ShListenerAdapter,
////                                                                MessageListenerAdapter BwListenerAdapter,
//                                                                ChannelTopic channelTopic) {
//        log.info("six hat messageListener start");
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.addMessageListener(ShListenerAdapter, channelTopic);
////        container.addMessageListener(BwListenerAdapter, ChannelTopic);
//        return container;
//    }




}
