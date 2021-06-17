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
public class PfListener {

	@Autowired
	private MsgService msgService;
	
	@RabbitListener(queues =RabbitMqConfig.QUEUE_CDA)
	public void CdaListen(Message message, Channel channel) throws IOException {
		System.out.println("收到人群密度分析任务报警数据"+message);
		String msg=new String(message.getBody(),"utf-8");
		msgService.saveMsg(msg,"10", SendSMSConfigType.pfCda);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}
	
	@RabbitListener(queues =RabbitMqConfig.QUEUE_CDS)
	public void CdsListen(Message message, Channel channel) throws IOException {
		System.out.println("收到人群密度定时上报数据"+message);
		String msg=new String(message.getBody(),"utf-8");
		msgService.saveMsg(msg,"10", SendSMSConfigType.pfCds);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}
	
	@RabbitListener(queues =RabbitMqConfig.QUEUE_PFS)
	public void PfsListen(Message message, Channel channel) throws IOException {
		System.out.println("收到上报通道客流量数据"+message);
		String msg=new String(message.getBody(),"utf-8");
		msgService.saveMsg(msg,"10", SendSMSConfigType.pfPfs);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}
}
