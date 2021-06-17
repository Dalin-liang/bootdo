package com.bootdo.data.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfigToAqi {
	
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

    public static final String ExchangeName_AQI= "big_data_center_aqi";
    
    public static final String QUEUE_aqiData= "aqiData";
    public static final String routingKey_aqiData= "aqi.aqiData.*.*";
    
    public static final String QUEUE_aqiDayData= "aqiDayData";
    public static final String routingKey_aqiDayData= "aqi.aqiDayData.*.*";
    
    public static final String QUEUE_aqiHourData= "aqiHourData";
    public static final String routingKey_aqiHourData= "aqi.aqiHourData.*.*";
    
    
    /**
	 * 构建topic类型交换机
	 * 
	 * @return
	 */
	@Bean
	public TopicExchange topicExchangeAQI() {
		return new TopicExchange(ExchangeName_AQI, true, false);
	}

	/**
	 * 构建序列
	 * 
	 * @return
	 */
	@Bean
	public Queue aqiDataQueue() {
		// 支持持久化
		return new Queue(QUEUE_aqiData, true);
	}
	@Bean
	public Queue aqiDayDataQueue() {
		// 支持持久化
		return new Queue(QUEUE_aqiDayData, true);
	}
	@Bean
	public Queue aqiHourDataQueue() {
		// 支持持久化
		return new Queue(QUEUE_aqiHourData, true);
	}
	
	/**
	 * 绑定交交换机和队列
	 * topic_queue1的bindingKey为：*.orange.*
	 * topic_queue2的bindingKey有两个：*.*.rabbit和lazy.#
	 * 
	 * @return
	 */
	@Bean
	public Binding AqiDataBinding() {
		return BindingBuilder.bind(aqiDataQueue()).to(topicExchangeAQI()).with(routingKey_aqiData);
	}
	@Bean
	public Binding AqiDayDataBinding() {
		return BindingBuilder.bind(aqiDayDataQueue()).to(topicExchangeAQI()).with(routingKey_aqiDayData);
	}
	@Bean
	public Binding AqiHourDataBinding() {
		return BindingBuilder.bind(aqiHourDataQueue()).to(topicExchangeAQI()).with(routingKey_aqiHourData);
	}
	

}
