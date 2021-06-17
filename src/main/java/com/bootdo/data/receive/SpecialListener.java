package com.bootdo.data.receive;

import java.io.IOException;

import com.bootdo.data.config.SendSMSConfigType;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bootdo.data.config.RabbitMqConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

@Component
public class SpecialListener {
	@Autowired
	private MsgService msgService;
	
	/*@RabbitListener(queues =RabbitMqConfig.QUEUE_SpecialPopulationBloodPressure)
	public void SpecialPopulationBloodPressureRListen(Message message, Channel channel) throws IOException {
		System.out.println("收到QUEUE_SpecialPopulationBloodPressureR数据");
		String msg=new String(message.getBody(),"utf-8");
		msgService.saveMsg(msg,"9");
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}*/
	
	@RabbitListener(queues =RabbitMqConfig.QUEUE_SpecialPopulationHeartRate)
	public void SpecialPopulationHeartRateListen(Message message, Channel channel) throws IOException {
		System.out.println("收到QUEUE_SpecialPopulationHeartRate数据");
		String msg=new String(message.getBody(),"utf-8");
		msgService.saveMsg(msg,"9", SendSMSConfigType.specialPopulationHeartRate);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}
	
	@RabbitListener(queues =RabbitMqConfig.QUEUE_SpecialPopulationSleep)
	public void SpecialPopulationSleepListen(Message message, Channel channel) throws IOException {
		System.out.println("收到QUEUE_SpecialPopulationSleep数据");
		String msg=new String(message.getBody(),"utf-8");
		msgService.saveMsg(msg,"9", SendSMSConfigType.specialPopulationSleep);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}
	
	@RabbitListener(queues =RabbitMqConfig.QUEUE_SpecialPopulationWalk)
	public void SpecialPopulationWalkListen(Message message, Channel channel) throws IOException {
		System.out.println("收到QUEUE_SpecialPopulationWalk数据");
		String msg=new String(message.getBody(),"utf-8");
		msgService.saveMsg(msg,"9", SendSMSConfigType.specialPopulationWalk);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}
	
	@RabbitListener(queues =RabbitMqConfig.QUEUE_SpecialPopulationFall)
	public void SpecialPopulationFallListen(Message message, Channel channel) throws IOException {
		System.out.println("收到QUEUE_SpecialPopulationFall数据");
		String msg=new String(message.getBody(),"utf-8");
		msgService.saveMsg(msg,"9", SendSMSConfigType.specialPopulationFall);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}
	
}
