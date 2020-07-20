package com.ecjtu.hht.redis.pubsub;

import com.ecjtu.hht.redis.utils.RedisUtils;
import org.apache.logging.log4j.message.SimpleMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class RedisPubSub {
    private static final Logger logger = LoggerFactory.getLogger(RedisPubSub.class);

    @Autowired
    private RedisUtils redisUtils;

    private ChannelTopic topic = new ChannelTopic("/redis/pubsub");


    @Scheduled(initialDelay = 5000, fixedDelay = 10000)
    private void schedule() {
        logger.info("publish message");
        publish("hey you must go now!");
    }

    /**
     * 推送消息
     *
     * @param publisher
     * @param message
     */
    public void publish(String content) {
        logger.info("message send {} ", content);
        redisUtils.pub(topic.getTopic(), content);
    }

    @Component
    public static class MessageSubscriber {
        public void onMessage(String message, String pattern) {
            logger.info("topic {} received {} ", pattern, message);
        }
    }


    /**
     * 消息监听器，使用MessageAdapter可实现自动化解码及方法代理
     *
     * @return
     */
    @Bean
    public MessageListenerAdapter listener(MessageSubscriber subscriber) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(subscriber, "onMessage");
        adapter.setSerializer(new JdkSerializationRedisSerializer());
        adapter.afterPropertiesSet();
        return adapter;
    }

    /**
     * 将订阅器绑定到容器
     *
     * @param connectionFactory
     * @param listenerAdapter
     * @return
     */
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                                   MessageListenerAdapter listener) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listener, new PatternTopic("/redis/*"));
        return container;
    }
}