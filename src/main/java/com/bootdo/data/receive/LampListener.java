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
public class LampListener {
	@Autowired
	private MsgService msgService;

	@RabbitListener(queues =RabbitMqConfig.QUEUE_LampAlarmDoor)
	public void LampAlarmDoorListen(Message message, Channel channel) throws IOException {
		System.out.println("收到LampAlarmDoor数据");
		String msg=new String(message.getBody(),"utf-8");
		msgService.saveMsg(msg,"11", SendSMSConfigType.lampAlarmDoor);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}
	
	@RabbitListener(queues =RabbitMqConfig.QUEUE_LampAlarmPhaseLoss)
	public void LampAlarmPhaseLossListen(Message message, Channel channel) throws IOException {
		System.out.println("收到LampAlarmPhaseLoss数据");
		String msg=new String(message.getBody(),"utf-8");
		msgService.saveMsg(msg,"11", SendSMSConfigType.lampAlarmPhaseLoss);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}
	
	@RabbitListener(queues =RabbitMqConfig.QUEUE_LampAlarmPower)
	public void LampAlarmPowerListen(Message message, Channel channel) throws IOException {
		String msg=new String(message.getBody(),"utf-8");
		msgService.saveMsg(msg,"11", SendSMSConfigType.lampAlarmPower);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}
	
	@RabbitListener(queues =RabbitMqConfig.QUEUE_LampAlarmTransFormer)
	public void LampAlarmTransFormerListen(Message message, Channel channel) throws IOException {
		System.out.println("收到QUEUE_LampAlarmTransFormer数据");
		String msg=new String(message.getBody(),"utf-8");
		msgService.saveMsg(msg,"11", SendSMSConfigType.lampAlarmTransFormer);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}
	
	@RabbitListener(queues =RabbitMqConfig.QUEUE_LampAlarmWaterenter)
	public void LampAlarmWaterenterListen(Message message, Channel channel) throws IOException {
		System.out.println("收到LampQUEUE_LampAlarmWaterenterAlarmDoor数据");
		String msg=new String(message.getBody(),"utf-8");
		msgService.saveMsg(msg,"11", SendSMSConfigType.lampAlarmWaterenter);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}
	
	@RabbitListener(queues =RabbitMqConfig.QUEUE_LampAlarmZoneInfrared)
	public void LampAlarmZoneInfraredListen(Message message, Channel channel) throws IOException {
		System.out.println("收到QUEUE_LampAlarmZoneInfrared数据");
		String msg=new String(message.getBody(),"utf-8");
		msgService.saveMsg(msg,"11", SendSMSConfigType.lampAlarmZoneInfrared);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}
	
	@RabbitListener(queues =RabbitMqConfig.QUEUE_LampElectricalPhase)
	public void LampElectricalPhaseListen(Message message, Channel channel) throws IOException {
		System.out.println("收到QUEUE_LampElectricalPhase数据");
		String msg=new String(message.getBody(),"utf-8");
		msgService.saveMsg(msg,"11", SendSMSConfigType.lampElectricalPhase);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}
	
	@RabbitListener(queues =RabbitMqConfig.QUEUE_LampPathLine)
	public void LampPathLineListen(Message message, Channel channel) throws IOException {
		System.out.println("收到QUEUE_LampPathLine数据");
		String msg=new String(message.getBody(),"utf-8");
		msgService.saveMsg(msg,"11", SendSMSConfigType.lampPathLine);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}
}
