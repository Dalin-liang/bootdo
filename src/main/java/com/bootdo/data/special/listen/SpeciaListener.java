package com.bootdo.data.special.listen;

import com.bootdo.data.config.RabbitMqConfig;
import com.bootdo.data.config.SendSMSConfigType;
import com.bootdo.data.receive.MsgService;
import com.bootdo.support.service.ReceiveinfoService;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import com.rabbitmq.client.ShutdownSignalException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeoutException;

/**
 * @author tanhitao
 * @Classname 
 * @Description 特殊人群
 * @Date 2019/10/30 0004 上午 10:15
 */
@Component
public class SpeciaListener implements ApplicationRunner, DisposableBean {
    /**
     *  消息队列配置数据
     */
    @Autowired
    private RabbitMqConfig rabbitMqConfig;
	private static final Logger logger = LoggerFactory.getLogger(SpeciaListener.class);

 
    @Autowired
	private ReceiveinfoService receiveinfoService;
    @Autowired
	private MsgService msgService;
    private Thread thread;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
          
        	speicialData();
        } catch (Exception  e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("关闭特殊人群警监听器");
        thread.interrupt();
    }
    
    public void speicialData() throws IOException, TimeoutException, ShutdownSignalException, ConsumerCancelledException, InterruptedException, UnsupportedEncodingException {
    	ConnectionFactory connectionFactory = new ConnectionFactory() ;  
        
        connectionFactory.setHost(rabbitMqConfig.getHost());
        connectionFactory.setPort(rabbitMqConfig.getPort());
		connectionFactory.setVirtualHost(rabbitMqConfig.getVirtualHost());
        connectionFactory.setUsername(rabbitMqConfig.getUsername());
        connectionFactory.setPassword(rabbitMqConfig.getPassword());

        connectionFactory.setAutomaticRecoveryEnabled(rabbitMqConfig.getAutomaticRecoveryEnabled());
        connectionFactory.setNetworkRecoveryInterval(rabbitMqConfig.getNetworkRecoveryInterval());
        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();
		//4 声明
		String exchangeName = rabbitMqConfig.getTOPIC_EXCHANGE_NAME();
		String exchangeType = rabbitMqConfig.getExchangeType();
		String queueName = rabbitMqConfig.getQueueName_SOS();
		String routingKey = rabbitMqConfig.getQueueRoutingKey_SOS();
		// 1 声明交换机 
		channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);
		// 2 声明队列
		channel.queueDeclare(queueName, false, false, false, null);
		// 3 建立交换机和队列的绑定关系:
		channel.queueBind(queueName, exchangeName, routingKey);
		
        //durable 是否持久化消息
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //参数：队列名称、是否自动ACK、Consumer
        channel.basicConsume(queueName, true, consumer);  
        //循环获取消息  
        while(true){  
            //获取消息，如果没有消息，这一步将会一直阻塞  
            Delivery delivery = consumer.nextDelivery();  
            String msg = new String(delivery.getBody(),"utf-8");
			logger.warn("recive speicialSOSData：{}", msg);

//			msg = new String(message.getBody(),"utf-8");

            System.out.println("***********收到消息***********：" + msg);  
            if(StringUtils.isNotEmpty(msg)) {
                try {
					logger.info ("spcialRabbitMQ",msg);
					System.out.println("收到消息：" + msg);  
					msgService.saveMsg(msg,"9", SendSMSConfigType.specialSOSData);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error ("spcialRabbitMQ error",e);

					e.printStackTrace();
				}
            }
    
    }
    }
	private String getCode(){
		String date =  new SimpleDateFormat("yyyyMMdd").format(new Date());
		StringBuilder str=new StringBuilder();
		str.append(date);
		Random random=new Random();
		int len = 16-date.length();
		for(int i=0;i<len;i++){
		    str.append(random.nextInt(10));
		}
		return str.toString();
	}
    
}
