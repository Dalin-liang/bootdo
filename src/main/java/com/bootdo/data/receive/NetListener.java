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
public class NetListener {
	@Autowired
	private MsgService msgService;

	@RabbitListener(queues =RabbitMqConfig.QUEUE_NetEventCrosslineInfo)
	public void NetEventCrosslineInfoListen(Message message, Channel channel) throws IOException {
		System.out.println("收到NetEventCrosslineInfo数据");
		String msg=new String(message.getBody(),"utf-8");
		msgService.saveMsg(msg,"7", SendSMSConfigType.netEventCrosslineInfo);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}
	
	@RabbitListener(queues =RabbitMqConfig.QUEUE_NetEventLeftInfo)
	public void NetEventLeftInfoListen(Message message, Channel channel) throws IOException {
		System.out.println("收到NetEventLeftInfo数据");
		String msg=new String(message.getBody(),"utf-8");
		msgService.saveMsg(msg,"7", SendSMSConfigType.netEventLeftInfo);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}
	
	@RabbitListener(queues =RabbitMqConfig.QUEUE_NetEventParkingDetectionInfo)
	public void NetEventParkingDetectionInfoListen(Message message, Channel channel) throws IOException {
		System.out.println("收到NetEventParkingDetectionInfo数据");
		String msg=new String(message.getBody(),"utf-8");
		msgService.saveMsg(msg,"7", SendSMSConfigType.netEventParkingDetectionInfo);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}
	
	@RabbitListener(queues =RabbitMqConfig.QUEUE_NetEventRioterInfo)
	public void NetEventRioterInfoListen(Message message, Channel channel) throws IOException {
		System.out.println("收到NetEventRioterInfo数据");
		String msg=new String(message.getBody(),"utf-8");
		msgService.saveMsg(msg,"7", SendSMSConfigType.netEventRioterInfo);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}
	
	@RabbitListener(queues =RabbitMqConfig.QUEUE_NetEventWanderInfo)
	public void NetEventWanderInfoListen(Message message, Channel channel) throws IOException {
		System.out.println("收到NetEventWanderInfo数据");
		String msg=new String(message.getBody(),"utf-8");
		msgService.saveMsg(msg,"7", SendSMSConfigType.netEventWanderInfo);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}
}
