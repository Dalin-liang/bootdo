package com.bootdo.data.receive;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.bootdo.data.config.SendSMSConfigType;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bootdo.data.config.RabbitMqConfig;
import com.rabbitmq.client.Channel;

@Component
public class FireListener {
	@Autowired
	private MsgService msgService;


	
	@RabbitListener(queues =RabbitMqConfig.QUEUE_FireBure)
	public void FireBureListen(Message message, Channel channel) throws IOException {
		System.out.println("收到QUEUE_FireBure数据");
		String msg=new String(message.getBody(),"utf-8");

		msgService.saveMsg(msg,"6",SendSMSConfigType.fireBure);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

	}
	@RabbitListener(queues =RabbitMqConfig.QUEUE_FireElectricDistinguish)
	public void FireElectricDistinguishListen(Message message, Channel channel) throws IOException {
		System.out.println("收到QUEUE_FireElectricDistinguish数据");
		String msg=new String(message.getBody(),"utf-8");

		msgService.saveMsg(msg,"20",SendSMSConfigType.fireElectricDistinguish);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

	}
	@RabbitListener(queues =RabbitMqConfig.QUEUE_FireElectricEarlyWarning)
	public void FireElectricEarlyWarningListen(Message message, Channel channel) throws IOException {
		System.out.println("收到QUEUE_FireElectricEarlyWarning数据");
		String msg=new String(message.getBody(),"utf-8");

		msgService.saveMsg(msg,"17",SendSMSConfigType.fireElectricEarlyWarning);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

	}
	@RabbitListener(queues =RabbitMqConfig.QUEUE_FireElectricError)
	public void FireElectricErrorListen(Message message, Channel channel) throws IOException {
		System.out.println("收到QUEUE_FireElectricError数据");
		String msg=new String(message.getBody(),"utf-8");

		msgService.saveMsg(msg,"17",SendSMSConfigType.fireElectricError);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

	}
	@RabbitListener(queues =RabbitMqConfig.QUEUE_FireElectricNow)
	public void FireElectricNowListen(Message message, Channel channel) throws IOException {
		System.out.println("收到QUEUE_FireElectricNow数据");
		String msg=new String(message.getBody(),"utf-8");

		msgService.saveMsg(msg,"17",SendSMSConfigType.fireElectricNow);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

	}
	@RabbitListener(queues =RabbitMqConfig.QUEUE_FireElectricWarning)
	public void FireElectricWarningListen(Message message, Channel channel) throws IOException {
		System.out.println("收到FireElectricWarning数据");
		String msg=new String(message.getBody(),"utf-8");

		msgService.saveMsg(msg,"17",SendSMSConfigType.fireElectricWarning);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

	}
	@RabbitListener(queues =RabbitMqConfig.QUEUE_FireSmoke)
	public void FireSmokeListen(Message message, Channel channel) throws IOException {
		System.out.println("收到FireSmoke数据");
		String msg=new String(message.getBody(),"utf-8");

		msgService.saveMsg(msg,"18",SendSMSConfigType.fireSmoke);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

	}
	@RabbitListener(queues =RabbitMqConfig.QUEUE_FireSmokeRelay)
	public void FireSmokeRelayListen(Message message, Channel channel) throws IOException {
		System.out.println("收到FireSmokeRelay数据");
		String msg=new String(message.getBody(),"utf-8");

		msgService.saveMsg(msg,"6",SendSMSConfigType.fireSmokeRelay);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

	}
	@RabbitListener(queues =RabbitMqConfig.QUEUE_FireSmokeRelayWarm)
	public void FireSmokeRelayWarmListen(Message message, Channel channel) throws IOException {
		System.out.println("收到FireSmokeRelayWarm数据");
		String msg=new String(message.getBody(),"utf-8");

		msgService.saveMsg(msg,"6",SendSMSConfigType.fireSmokeRelayWarm);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

	}
	@RabbitListener(queues =RabbitMqConfig.QUEUE_FireUserMessage)
	public void FireUserMessageListen(Message message, Channel channel) throws IOException {
		System.out.println("收到FireUserMessage数据");
		String msg=new String(message.getBody(),"utf-8");

		msgService.saveMsg(msg,"6",SendSMSConfigType.fireUserMessage);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

	}
	@RabbitListener(queues =RabbitMqConfig.QUEUE_FireUserMessageFc)
	public void FireUserMessageFcListen(Message message, Channel channel) throws IOException {
		System.out.println("收到FireUserMessageFc数据");
		String msg=new String(message.getBody(),"utf-8");

		msgService.saveMsg(msg,"6",SendSMSConfigType.fireUserMessageFc);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

	}
	@RabbitListener(queues =RabbitMqConfig.QUEUE_FireUserMessageFcSensor)
	public void FireUserMessageFcSensorListen(Message message, Channel channel) throws IOException {
		System.out.println("收到QUEUE_FireBure数据");
		String msg=new String(message.getBody(),"utf-8");

		msgService.saveMsg(msg,"6",SendSMSConfigType.fireUserMessageFcSensor);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

	}
	@RabbitListener(queues =RabbitMqConfig.QUEUE_FireWaterCycle)
	public void FireWaterCycleListen(Message message, Channel channel) throws IOException {
		System.out.println("收到FireWaterCycle数据");
		String msg=new String(message.getBody(),"utf-8");

		msgService.saveMsg(msg,"6",SendSMSConfigType.fireWaterCycle);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

	}
	@RabbitListener(queues =RabbitMqConfig.QUEUE_FireWaterCycleV2)
	public void FireWaterCycleV2Listen(Message message, Channel channel) throws IOException {
		System.out.println("收到FireWaterCycleV2数据");
		String msg=new String(message.getBody(),"utf-8");

		msgService.saveMsg(msg,"16",SendSMSConfigType.fireWaterCycleV2);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

	}
	@RabbitListener(queues =RabbitMqConfig.QUEUE_FireWaterNowSmr1000)
	public void FireWaterNowSmr1000Listen(Message message, Channel channel) throws IOException {
		System.out.println("收到FireWaterNowSmr1000数据");
		String msg=new String(message.getBody(),"utf-8");

		msgService.saveMsg(msg,"6",SendSMSConfigType.fireWaterNowSmr1000);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

	}
	@RabbitListener(queues =RabbitMqConfig.QUEUE_FireWaterWarm)
	public void FireWaterWarmListen(Message message, Channel channel) throws IOException {
		System.out.println("收到FireWaterWarm数据");
		String msg=new String(message.getBody(),"utf-8");

		msgService.saveMsg(msg,"6",SendSMSConfigType.fireWaterWarm);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

	}
	@RabbitListener(queues =RabbitMqConfig.QUEUE_FireWaterWarmSmr1000)
	public void FireWaterWarmSmr1000Listen(Message message, Channel channel) throws IOException {
		System.out.println("收到FireWaterWarmSmr1000数据");
		String msg=new String(message.getBody(),"utf-8");

		msgService.saveMsg(msg,"6",SendSMSConfigType.fireWaterWarmSmr1000);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

	}
	@RabbitListener(queues =RabbitMqConfig.QUEUE_FireWaterWarmV2)
	public void FireWaterWarmV2Listen(Message message, Channel channel) throws IOException {
		System.out.println("收到FireWaterWarmV2数据");
		String msg=new String(message.getBody(),"utf-8");

		msgService.saveMsg(msg,"21",SendSMSConfigType.fireWaterWarmV2);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

	}

}
