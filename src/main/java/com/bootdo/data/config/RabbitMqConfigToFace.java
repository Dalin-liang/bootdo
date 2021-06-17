package com.bootdo.data.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfigToFace {

    @Value("${rabbitmq.bigDataCenterPush.host}")
    private String host;

    /**
     *  主机端口
     */
    @Value("${rabbitmq.bigDataCenterPush.port}")
    private int port;

    /**
     *  虚拟机IP
     */
    @Value("${rabbitmq.bigDataCenterPush.virtualHost}")
    private String virtualHost;

    /**
     *  用户名
     */
    @Value("${rabbitmq.bigDataCenterPush.username}")
    private String username;

    /**
     *  密码
     */
    @Value("${rabbitmq.bigDataCenterPush.password}")
    private String password;

    public static final String ExchangeName_FACE= "big_data_center_face";

    public static final String QUEUE_FACE= "faceInfoData";
    public static final String routingKey_FACE= "faceInfoData.*.*";



    /**
     * 构建topic类型交换机
     *
     * @return
     */
    @Bean
    public TopicExchange topicExchangeFACE() {
        return new TopicExchange(ExchangeName_FACE, true, false);
    }

    /**
     * 构建序列
     *
     * @return
     */
    @Bean
    public Queue faceDataQueue() {
        // 支持持久化
        return new Queue(QUEUE_FACE, true);
    }

    /**
     * 绑定交交换机和队列
     * topic_queue1的bindingKey为：*.orange.*
     * topic_queue2的bindingKey有两个：*.*.rabbit和lazy.#
     *
     * @return
     */
    @Bean
    public Binding faceDataBinding() {
        return BindingBuilder.bind(faceDataQueue()).to(topicExchangeFACE()).with(routingKey_FACE);
    }

}
