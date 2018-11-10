package com.sysco.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

/**
 * rabbitmq 配置
 * Created by steadyjack on 2017/12/01.
 */
@Configuration
public class RabbitmqConfig {

    private final static Logger log = LoggerFactory.getLogger("mqLog");

    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Autowired
    private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;

    /**vendor激活队列配置**/

    @Bean
    public TopicExchange registerTopicExchange(){
        return new TopicExchange("vendor.exchange");
    }

    @Bean
    public Binding activationBinding(){
        return BindingBuilder.bind(activationQueue()).to(registerTopicExchange()).with("vendor.*");
    }

    @Bean
    public Queue activationQueue(){
        Map<String, Object> params = new HashMap<>();
        params.put("x-message-ttl",20000);
        params.put("x-dead-letter-exchange","vendor.delay.exchange");
        params.put("x-dead-letter-routing-key","vendor.delay");
        return new Queue("vendor.activation.queue", true,false,false,params);
    }

    /**vendor激活**/

    /**延迟队列配置**/

    @Bean
    public Queue vendorDelayQueue(){
        return new Queue("vendor.delay.queue", true);
    }

    @Bean
    public DirectExchange vendorDelayExchange(){
        return new DirectExchange("vendor.delay.exchange");
    }

    @Bean
    public Binding vendorDelayBinding(){
        return BindingBuilder.bind(vendorDelayQueue()).to(vendorDelayExchange()).with("vendor.delay");
    }

    /**延迟队列配置**/

    /**email队列配置**/

    @Bean
    public Binding emailBinding(){
        return BindingBuilder.bind(emailQueue()).to(registerTopicExchange()).with("vendor.*");
    }

    @Bean
    public Queue emailQueue(){
        return new Queue("vendor.send.email.queue",true);
    }

    /**email队列配置**/

    /**
     * 单一消费者
     * @return
     */
    @Bean(name = "singleListenerContainer")
    public SimpleRabbitListenerContainerFactory listenerContainer(){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(1);
        factory.setPrefetchCount(1);
        factory.setTxSize(1);
        return factory;
    }

    /**
     * 多个消费者
     * @return
     */
    @Bean(name = "multiListenerContainer")
    public SimpleRabbitListenerContainerFactory multiListenerContainer(){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factoryConfigurer.configure(factory,connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.NONE);
        return factory;
    }


    @Bean
    public RabbitTemplate rabbitTemplate(){
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) ->
                log.info("消息发送成功:correlationData({}),ack({}),cause({})",correlationData,ack,cause));
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) ->
                log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}",exchange,routingKey,replyCode,replyText,message));
        return rabbitTemplate;
    }

}
