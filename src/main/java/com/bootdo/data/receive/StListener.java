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
public class StListener {
	@Autowired
	private MsgService msgService;
	
	@RabbitListener(queues =RabbitMqConfig.QUEUE_StPptnR)
	public void StPptnRListen(Message message, Channel channel) throws IOException {
		System.out.println("收到QUEUE_StPptnR数据");
		String msg=new String(message.getBody(),"utf-8");
		msgService.saveMsg(msg,"8", SendSMSConfigType.stPptnR);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}
	
	@RabbitListener(queues =RabbitMqConfig.QUEUE_StRiverR)
	public void StRiverRListen(Message message, Channel channel) throws IOException {
		System.out.println("收到QUEUE_StRiverR数据");
		String msg=new String(message.getBody(),"utf-8");
		msgService.saveMsg(msg,"8", SendSMSConfigType.stRiverR);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}
	
	@RabbitListener(queues =RabbitMqConfig.QUEUE_StRsvrR)
	public void StRsvrRListen(Message message, Channel channel) throws IOException {
		System.out.println("收到QUEUE_StRsvrR数据");
		String msg=new String(message.getBody(),"utf-8");
		msgService.saveMsg(msg,"8", SendSMSConfigType.stRsvrR);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}
	
	@RabbitListener(queues =RabbitMqConfig.QUEUE_StSdsjR)
	public void StSdsjRListen(Message message, Channel channel) throws IOException {
		System.out.println("收到QUEUE_StSdsjR数据");
		String msg=new String(message.getBody(),"utf-8");
		msgService.saveMsg(msg,"8", SendSMSConfigType.stSdsjR);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}
	
	@RabbitListener(queues =RabbitMqConfig.QUEUE_StSsgkR)
	public void StSsgkRListen(Message message, Channel channel) throws IOException {
		System.out.println("收到QUEUE_StSsgkR数据");
		String msg=new String(message.getBody(),"utf-8");
		msgService.saveMsg(msg,"8", SendSMSConfigType.stSsgkR);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}
	
	@RabbitListener(queues =RabbitMqConfig.QUEUE_StSsswR)
	public void StSsswRListen(Message message, Channel channel) throws IOException {
		System.out.println("收到QUEUE_StSsswR数据");
		String msg=new String(message.getBody(),"utf-8");
		msgService.saveMsg(msg,"8", SendSMSConfigType.stSsswR);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}
	
	@RabbitListener(queues =RabbitMqConfig.QUEUE_StSsylR)
	public void StSsylRListen(Message message, Channel channel) throws IOException {
		System.out.println("收到QUEUE_StSsylR数据");
		String msg=new String(message.getBody(),"utf-8");
		msgService.saveMsg(msg,"8", SendSMSConfigType.stSsylR);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}
	
	
}
