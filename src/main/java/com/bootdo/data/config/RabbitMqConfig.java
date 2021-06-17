package com.bootdo.data.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * topic交换机模式
 * 
 * @author 
 * @version 创建时间：
 *
 */
@Configuration
public  class RabbitMqConfig {
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

	/**
	 *  回滚
	 */
	@Value("${rabbitmq.bigDataCenterPush.publisherReturns}")
	private boolean publisherReturns;

	/**
	 *  手动确认
	 */
	@Value("${rabbitmq.bigDataCenterPush.publisherConfirms}")
	private boolean publisherConfirms;

    /**
     *  交换机名称
     */
    @Value("${rabbitmq.bigDataCenterPush.exchangeName}")
    private String TOPIC_EXCHANGE_NAME;

    @Value("${rabbitmq.bigDataCenterPush.exchangeType}")
    private String exchangeType;

    @Value("${rabbitmq.bigDataCenterPush.automaticRecoveryEnabled}")
    private boolean automaticRecoveryEnabled;
	@Value("${rabbitmq.bigDataCenterPush.networkRecoveryInterval}")
	private Long networkRecoveryInterval;

	@Value("${rabbitmq.bigDataCenterPush.specialPopulationSOS.queueName}")
	private String queueName_SOS;
	@Value("${rabbitmq.bigDataCenterPush.specialPopulationSOS.routingKey}")
	private String queueRoutingKey_SOS;
    
	/**
	 * 定义bindingKey,模糊匹配
	 */
//    public  static final String routingKey_SOS= "event.specialPopulationSOS.*.*";
    
    public static final  String routingKey_BLOOD= "event.specialPopulationBloodPressure.*.*";
    
    public static final  String routingKey_CDA= "event.crowdDensityAlarm.*.*";
    
    public static final  String routingKey_CDS= "event.crowdDensityStatistic.*.*";
    
    public static final  String routingKey_PFS= "event.passengerflowStatistics.*.*";

    /**
     *  燃气系统数据路由key
     */
    public static final  String routingKey_FireBure="event.FireBure.*.*";
    
    /**
     *  电器识别数据路由key
     */
    public static final  String routingKey_FireElectricDistinguish="event.FireElectricDistinguish.*.*";
    
    /**
     *  电气识别系统预警数据路由key
     */
    public static final  String routingKey_FireElectricEarlyWarning="event.FireElectricEarlyWarning.*.*";
    
    /**
     *  电气识别系统故障数据路由key
     */
    public static final  String routingKey_FireElectricError="event.FireElectricError.*.*";
    
    /**
     *  电气识别系统实时数据路由key
     */
    public static  final String routingKey_FireElectricNow="event.FireElectricNow.*.*";
    
    /**
     *  电气识别系统报警数据路由key
     */
    public static final  String routingKey_FireElectricWarning="event.FireElectricWarning.*.*";
    
    /**
     *  烟温一体机数据路由key
     */
    public  static final String routingKey_FireSmoke="event.FireSmoke.*.*";
    
    /**
     *  烟感中继主机中继数据路由key
     */
    public static final  String routingKey_FireSmokeRelay="event.FireSmokeRelay.*.*";
    
    /**
     *  中继管理的烟感报警数据路由key
     */
    public static final  String routingKey_FireSmokeRelayWarm="event.FireSmokeRelayWarm.*.*";
    
    /**
     *  用户信息传输装置数据路由key
     */
    public static final  String routingKey_FireUserMessage="event.FireUserMessage.*.*";
    
    /**
     *  用户信息传输装置-管理的火灾报警控制器数据路由key
     */
    public static final  String routingKey_FireUserMessageFc="event.FireUserMessageFc.*.*";
    
    /**
     *  用户信息传输装置-管理的火灾报警控制器-管理的传感器数据路由key
     */
    public static final  String routingKey_FireUserMessageFcSensor="event.FireUserMessageFcSensor.*.*";
    
    /**
     *  消防用水系统SMR1210周期数据路由key
     */
    public static final  String routingKey_FireWaterCycle="event.FireWaterCycle.*.*";
    
    /**
     *  消防用水系统SMR1210-V2周期数据路由key
     */
    public static final  String routingKey_FireWaterCycleV2="event.FireWaterCycleV2.*.*";
    
    /**
     *  消防用水系统SMR1000实时数据路由key
     */
    public static final  String routingKey_FireWaterNowSmr1000="event.FireWaterNowSmr1000.*.*";
    
    /**
     *  消防用水系统SMR1210报警数据路由key
     */
    public static final  String routingKey_FireWaterWarm="event.FireWaterWarm.*.*";
    
    /**
     *  消防用水系统SMR1000报警数据路由key
     */
    public static final  String routingKey_FireWaterWarmSmr1000="event.FireWaterWarmSmr1000.*.*";
    
    /**
     *  消防用水系统SMR1210-V2报警数据路由key
     */
    public static final  String routingKey_FireWaterWarmV2="event.FireWaterWarmV2.*.*";

    //--------lamp routingkey---------
    
    public static final  String routingKey_LampAlarmDoor="event.LampAlarmDoor.*.*";
    public static final  String routingKey_LampAlarmPhaseLoss="event.LampAlarmPhaseLoss.*.*";
    public static final  String routingKey_LampAlarmPower="event.LampAlarmPower.*.*";
    public static final  String routingKey_LampAlarmTransFormer="event.LampAlarmTransFormer.*.*";
    public static final  String routingKey_LampAlarmWaterenter="event.LampAlarmWaterenter.*.*";
    public static final  String routingKey_LampAlarmZoneInfrared="event.LampAlarmZoneInfrared.*.*";
    public static final  String routingKey_LampElectricalPhase="event.LampElectricalPhase.*.*";
    public static final  String routingKey_LampPathLine="event.LampPathLine.*.*";
    
    //-------------st------------
    
    public static final  String routingKey_StPptnR="event.StPptnR.*.*";
    public static final  String routingKey_StRiverR="event.StRiverR.*.*";
    public static final  String routingKey_StRsvrR="event.StRsvrR.*.*";
    public static final  String routingKey_StSdsjR="event.StSdsjR.*.*";
    public static final  String routingKey_StSsgkR="event.StSsgkR.*.*";
    public static final  String routingKey_StSsswR="event.StSsswR.*.*";
    public static final  String routingKey_StSsylR="event.StSsylR.*.*";
    
    //special
    public static final  String routingKey_SpecialPopulationBloodPressure="event.SpecialPopulationBloodPressure.*.*";
    public static final  String routingKey_SpecialPopulationHeartRate="event.SpecialPopulationHeartRate.*.*";
    public static final  String routingKey_SpecialPopulationSleep="event.SpecialPopulationSleep.*.*";
    public static final  String routingKey_SpecialPopulationWalk="event.SpecialPopulationWalk.*.*";
    public static final  String routingKey_SpecialPopulationFall="event.SpecialPopulationFall.*.*";
    
    //net
    public static final  String routingKey_NetEventCrosslineInfo="event.NetEventCrosslineInfo.*.*";
    public static final  String routingKey_NetEventLeftInfo="event.NetEventLeftInfo.*.*";
    public static final  String routingKey_NetEventParkingDetectionInfo="event.NetEventParkingDetectionInfo.*.*";
    public static final  String routingKey_NetEventRioterInfo="event.NetEventRioterInfo.*.*";
    public static final  String routingKey_NetEventWanderInfo="event.NetEventWanderInfo.*.*";

    //---------------healthy routing-key
	//身高体重routing-key
	public final static String ROUTING_KEY_HEALTHY_HEIGHTWEIGHT = "event.healthyHeightWeightData.*.*";
	//身体成分routing-key
	public final static String ROUTING_KEY_HEALTHY_BODYCOMPOSITION = "event.healthyBodyCompositionData.*.*";
	//血压routing_key
	public final static String ROUTING_KEY_HEALTHY_BLOODPRESSURE = "event.healthyBloodPressureData.*.*";
	//血氧routing_key
	public final static String ROUTING_KEY_HEALTHY_BLOODOXYGEN = "event.healthyBloodOxygenData.*.*";
	//体温routing_key
	public final static String ROUTING_KEY_HEALTHY_TEMPERATURE = "event.healthyTemperatureData.*.*";
	//心电routing_key
	public final static String ROUTING_KEY_HEALTHY_ECG = "event.healthyEcgData.*.*";
	//血糖routing_key
	public final static String ROUTING_KEY_HEALTHY_BLOODSUGAR = "event.healthyBloodSugarData.*.*";
	//血脂routing_key
	public final static String ROUTING_KEY_HEALTHY_BLOODFAT = "event.healthyBloodFatData.*.*";
    
	/**
	 * special queue
	 */
//    public  static final String QUEUE_SOS="specialPopulationSOS";
    
    public  static final String QUEUE_BLOOD="specialPopulationBloodPressure";
    
	/**
	 * pf queue
	 */
    public  static final String QUEUE_CDA="crowdDensityAlarm";
    
    public  static final String QUEUE_CDS="crowdDensityStatistic";
    
    public  static final String QUEUE_PFS="passengerFlowStatistics";
    
    /**
     * fire queue
     */
    /**
     *  燃气系统数据队列
     */
    public static final  String QUEUE_FireBure="FireBure";
    /**
     *  电器识别数据队列
     */
    public static final  String QUEUE_FireElectricDistinguish="FireElectricDistinguish";
    /**
     *  电气识别系统预警数据队列
     */
    public static final  String QUEUE_FireElectricEarlyWarning="FireElectricEarlyWarning";
    
    /**
     *  电气识别系统故障数据队列
     */
    public static final  String QUEUE_FireElectricError="FireElectricError";
    
    /**
     *  电气识别系统实时数据队列
     */
    public static final  String QUEUE_FireElectricNow="FireElectricNow";
    
    /**
     *  电气识别系统报警数据队列
     */
    public static final  String QUEUE_FireElectricWarning="FireElectricWarning";
    
    /**
     *  烟温一体机数据队列
     */
    public static final  String QUEUE_FireSmoke="FireSmoke";
    
    /**
     *  烟感中继主机中继数据队列
     */
    public static final  String QUEUE_FireSmokeRelay="FireSmokeRelay";
    
    /**
     *  中继管理的烟感报警数据队列
     */
    public static final  String QUEUE_FireSmokeRelayWarm="FireSmokeRelayWarm";
    
    /**
     *  用户信息传输装置数据队列
     */
    public static  final String QUEUE_FireUserMessage="FireUserMessage";
    
    /**
     *  用户信息传输装置-管理的火灾报警控制器数据队列
     */
    public static final  String QUEUE_FireUserMessageFc="FireUserMessageFc";
    
    /**
     *  用户信息传输装置-管理的火灾报警控制器-管理的传感器数据队列
     */
    public static final  String QUEUE_FireUserMessageFcSensor="FireUserMessageFcSensor";
    
    /**
     *  消防用水系统SMR1210周期数据队列
     */
    public static final  String QUEUE_FireWaterCycle="FireWaterCycle";
    
    /**
     *  消防用水系统SMR1210-V2周期数据队列
     */
    public static  final String QUEUE_FireWaterCycleV2="FireWaterCycleV2";
    
    /**
     *  消防用水系统SMR1000实时数据队列
     */
    public static final  String QUEUE_FireWaterNowSmr1000="FireWaterNowSmr1000";
    
    /**
     *  消防用水系统SMR1210报警数据队列
     */
    public static final  String QUEUE_FireWaterWarm="FireWaterWarm";
    
    /**
     *  消防用水系统SMR1000报警数据队列
     */
    public static final  String QUEUE_FireWaterWarmSmr1000="FireWaterWarmSmr1000";
    
    /**
     *  消防用水系统SMR1210-V2报警数据队列
     */
    public static final  String QUEUE_FireWaterWarmV2="FireWaterWarmV2";
    
    
  //-----------lamp
    
    public static final  String QUEUE_LampAlarmDoor="LampAlarmDoor";
    public static final  String QUEUE_LampAlarmPhaseLoss="LampAlarmPhaseLoss";
    public static final  String QUEUE_LampAlarmPower="LampAlarmPower";
    public static final  String QUEUE_LampAlarmTransFormer="LampAlarmTransFormer";
    public static final  String QUEUE_LampAlarmWaterenter="LampAlarmWaterenter";
    public static final  String QUEUE_LampAlarmZoneInfrared="LampAlarmZoneInfrared";
    public static final  String QUEUE_LampElectricalPhase="LampElectricalPhase";
    public static final  String QUEUE_LampPathLine="LampPathLine";

  //------------lamp
    
  //------------st
    public static final  String QUEUE_StPptnR="StPptnR";
    public static final  String QUEUE_StRiverR="StRiverR";
    public static final  String QUEUE_StRsvrR="StRsvrR";
    public static final  String QUEUE_StSdsjR="StSdsjR";
    public static final  String QUEUE_StSsgkR="StSsgkR";
    public static final  String QUEUE_StSsswR="StSsswR";
    public static final  String QUEUE_StSsylR="StSsylR";
   //------------st
    
    //------------special
    public static final  String QUEUE_SpecialPopulationBloodPressure="SpecialPopulationBloodPressure";
    public static final  String QUEUE_SpecialPopulationHeartRate="SpecialPopulationHeartRate";
    public static final  String QUEUE_SpecialPopulationSleep="SpecialPopulationSleep";
    public static final  String QUEUE_SpecialPopulationWalk="SpecialPopulationWalk";
    public static final  String QUEUE_SpecialPopulationFall="SpecialPopulationFall";
    
    
    //------------special
    
    //------------net
    public static final  String QUEUE_NetEventCrosslineInfo="NetEventCrosslineInfo";
    public static final  String QUEUE_NetEventLeftInfo="NetEventLeftInfo";
    public static final  String QUEUE_NetEventParkingDetectionInfo="NetEventParkingDetectionInfo";
    public static final  String QUEUE_NetEventRioterInfo="NetEventRioterInfo";
    public static final  String QUEUE_NetEventWanderInfo="NetEventWanderInfo";

    //------------net


	//----------------healthy queue
	//身高体重queue
	public final static String QUEUE_HEALTHY_HEIGHTWEIGHT = "healthyHeightWeightData";
	//身体成分queue
	public final static String QUEUE_HEALTHY_BODYCOMPOSITION = "healthyBodyCompositionData";
	//血压queue
	public final static String QUEUE_HEALTHY_BLOODPRESSURE = "healthyBloodPressureData";
	//血氧queue
	public final static String QUEUE_HEALTHY_BLOODOXYGEN = "healthyBloodOxygenData";
	//体温queue
	public final static String QUEUE_HEALTHY_TEMPERATURE = "healthyTemperatureData";
	//心电queue
	public final static String QUEUE_HEALTHY_ECG = "healthyEcgData";
	//血糖queue
	public final static String QUEUE_HEALTHY_BLOODSUGAR = "healthyBloodSugarData";
	//血脂queue
	public final static String QUEUE_HEALTHY_BLOODFAT = "healthyBloodFatData";


	/**
	 * 构建topic类型交换机
	 *
	 * @return
	 */
	@Bean
	public TopicExchange topicExchange() {
		return new TopicExchange(TOPIC_EXCHANGE_NAME, true, false);
	}

	/**
	 * 构建序列
	 *
	 * @return
	 */


	@Bean
	public Queue BloodQueue() {
		// 支持持久化
		return new Queue(QUEUE_BLOOD, true);
	}

	@Bean
	public Queue CDAQueue() {
		// 支持持久化
		return new Queue(QUEUE_CDA, true);
	}

	@Bean
	public Queue CDSQueue() {
		// 支持持久化
		return new Queue(QUEUE_CDS, true);
	}

	@Bean
	public Queue PFSQueue() {
		// 支持持久化
		return new Queue(QUEUE_PFS, true);
	}
	//-----------fire-----------------
	@Bean
	public Queue FireBureQueue() {
		// 支持持久化
		return new Queue(QUEUE_FireBure, true);
	}
	@Bean
	public Queue FireElectricDistinguishQueue() {
		// 支持持久化
		return new Queue(QUEUE_FireElectricDistinguish, true);
	}
	@Bean
	public Queue FireElectricEarlyWarningQueue() {
		// 支持持久化
		return new Queue(QUEUE_FireElectricEarlyWarning, true);
	}
	@Bean
	public Queue FireElectricErrorQueue() {
		// 支持持久化
		return new Queue(QUEUE_FireElectricError, true);
	}
	@Bean
	public Queue FireElectricNowQueue() {
		// 支持持久化
		return new Queue(QUEUE_FireElectricNow, true);
	}
	@Bean
	public Queue FireElectricWarningQueue() {
		// 支持持久化
		return new Queue(QUEUE_FireElectricWarning, true);
	}
	@Bean
	public Queue FireSmokeQueue() {
		// 支持持久化
		return new Queue(QUEUE_FireSmoke  , true);
	}
	@Bean
	public Queue FireSmokeRelayQueue() {
		// 支持持久化
		return new Queue(QUEUE_FireSmokeRelay  , true);
	}
	@Bean
	public Queue FireSmokeRelayWarmQueue() {
		// 支持持久化
		return new Queue(QUEUE_FireSmokeRelayWarm  , true);
	}
	@Bean
	public Queue FireUserMessageQueue() {
		// 支持持久化
		return new Queue(QUEUE_FireUserMessage, true);
	}
	@Bean
	public Queue FireUserMessageFcQueue() {
		// 支持持久化
		return new Queue(QUEUE_FireUserMessageFc  , true);
	}
	@Bean
	public Queue FireUserMessageFcSensorQueue() {
		// 支持持久化
		return new Queue(QUEUE_FireUserMessageFcSensor, true);
	}
	@Bean
	public Queue FireWaterCycleQueue() {
		// 支持持久化
		return new Queue(QUEUE_FireWaterCycle  , true);
	}
	@Bean
	public Queue FireWaterCycleV2Queue() {
		// 支持持久化
		return new Queue(QUEUE_FireWaterCycleV2  , true);
	}
	@Bean
	public Queue FireWaterNowSmr1000Queue() {
		// 支持持久化
		return new Queue(QUEUE_FireWaterNowSmr1000  , true);
	}
	@Bean
	public Queue FireWaterWarmQueue() {
		// 支持持久化
		return new Queue(QUEUE_FireWaterWarm  , true);
	}
	@Bean
	public Queue FireWaterWarmSmr1000Queue() {
		// 支持持久化
		return new Queue(QUEUE_FireWaterWarmSmr1000  , true);
	}
	@Bean
	public Queue FireWaterWarmV2Queue() {
		// 支持持久化
		return new Queue(QUEUE_FireWaterWarmV2 , true);
	}
//-------------fire

//--lamp
	@Bean
	public Queue LampAlarmDoorQueue() {
		// 支持持久化
		return new Queue(QUEUE_LampAlarmDoor , true);
	}
	@Bean
	public Queue LampAlarmPhaseLossQueue() {
		// 支持持久化
		return new Queue(QUEUE_LampAlarmPhaseLoss , true);
	}
	@Bean
	public Queue LampAlarmPowerQueue() {
		// 支持持久化
		return new Queue(QUEUE_LampAlarmPower , true);
	}
	@Bean
	public Queue LampAlarmTransFormerQueue() {
		// 支持持久化
		return new Queue(QUEUE_LampAlarmTransFormer , true);
	}
	@Bean
	public Queue LampAlarmWaterenterQueue() {
		// 支持持久化
		return new Queue(QUEUE_LampAlarmWaterenter , true);
	}
	@Bean
	public Queue LampAlarmZoneInfraredQueue() {
		// 支持持久化
		return new Queue(QUEUE_LampAlarmZoneInfrared , true);
	}
	@Bean
	public Queue LampElectricalPhaseQueue() {
		// 支持持久化
		return new Queue(QUEUE_LampElectricalPhase , true);
	}
	@Bean
	public Queue LampPathLineQueue() {
		// 支持持久化
		return new Queue(QUEUE_LampPathLine , true);
	}

//--lamp

//-------st
	@Bean
	public Queue StPptnRQueue() {
		// 支持持久化
		return new Queue(QUEUE_StPptnR , true);
	}
	@Bean
	public Queue StRiverRQueue() {
		// 支持持久化
		return new Queue(QUEUE_StRiverR , true);
	}
	@Bean
	public Queue StRsvrRQueue() {
		// 支持持久化
		return new Queue(QUEUE_StRsvrR , true);
	}
	@Bean
	public Queue StSdsjRQueue() {
		// 支持持久化
		return new Queue(QUEUE_StSdsjR , true);
	}
	@Bean
	public Queue StSsgkRQueue() {
		// 支持持久化
		return new Queue(QUEUE_StSsgkR , true);
	}
	@Bean
	public Queue StSsswRQueue() {
		// 支持持久化
		return new Queue(QUEUE_StSsswR , true);
	}
	@Bean
	public Queue StSsylRQueue() {
		// 支持持久化
		return new Queue(QUEUE_StSsylR , true);
	}

//-------st

//-------special
	@Bean
	public Queue SpecialPopulationBloodPressureQueue() {
		// 支持持久化
		return new Queue(QUEUE_SpecialPopulationBloodPressure , true);
	}
	@Bean
	public Queue SpecialPopulationHeartRateQueue() {
		// 支持持久化
		return new Queue(QUEUE_SpecialPopulationHeartRate , true);
	}
	@Bean
	public Queue SpecialPopulationSleepQueue() {
		// 支持持久化
		return new Queue(QUEUE_SpecialPopulationSleep , true);
	}
	@Bean
	public Queue SpecialPopulationWalkQueue() {
		// 支持持久化
		return new Queue(QUEUE_SpecialPopulationWalk , true);
	}
	@Bean
	public Queue SpecialPopulationFallQueue() {
		// 支持持久化
		return new Queue(QUEUE_SpecialPopulationFall , true);
	}
// ---net
	@Bean
	public Queue NetEventCrosslineInfoQueue() {
		// 支持持久化
		return new Queue(QUEUE_NetEventCrosslineInfo , true);
	}
	@Bean
	public Queue NetEventLeftInfoQueue() {
		// 支持持久化
		return new Queue(QUEUE_NetEventLeftInfo , true);
	}
	@Bean
	public Queue NetEventParkingDetectionInfoQueue() {
		// 支持持久化
		return new Queue(QUEUE_NetEventParkingDetectionInfo , true);
	}
	@Bean
	public Queue NetEventRioterInfoQueue() {
		// 支持持久化
		return new Queue(QUEUE_NetEventRioterInfo , true);
	}
	@Bean
	public Queue NetEventWanderInfoQueue() {
		// 支持持久化
		return new Queue(QUEUE_NetEventWanderInfo , true);
	}

//--healthy
	@Bean
	public Queue healthyHeightWeightQueue(){
		return new Queue(QUEUE_HEALTHY_HEIGHTWEIGHT, true);
	}
	@Bean
	public Queue healthyBodyCompositionQueue(){
		return new Queue(QUEUE_HEALTHY_BODYCOMPOSITION, true);
	}
	@Bean
	public Queue healthyBloodPressureQueue(){
		return new Queue(QUEUE_HEALTHY_BLOODPRESSURE, true);
	}
	@Bean
	public Queue healthyBloodOxygenQueue(){
		return new Queue(QUEUE_HEALTHY_BLOODOXYGEN, true);
	}
	@Bean
	public Queue healthyTemperatureQueue(){
		return new Queue(QUEUE_HEALTHY_TEMPERATURE, true);
	}
	@Bean
	public Queue healthyEcgQueue(){
		return new Queue(QUEUE_HEALTHY_ECG, true);
	}
	@Bean
	public Queue healthyBloodSugarQueue(){
		return new Queue(QUEUE_HEALTHY_BLOODSUGAR, true);
	}
	@Bean
	public Queue healthyBloodFatQueue(){
		return new Queue(QUEUE_HEALTHY_BLOODFAT, true);
	}


	/**
	 * 绑定交交换机和队列
	 * topic_queue1的bindingKey为：*.orange.*
	 * topic_queue2的bindingKey有两个：*.*.rabbit和lazy.#
	 *
	 * @return
	 */


	@Bean
	public Binding BloodBinding() {
		return BindingBuilder.bind(BloodQueue()).to(topicExchange()).with(routingKey_BLOOD);
	}

	@Bean
	public Binding CDABinding() {
		return BindingBuilder.bind(CDAQueue()).to(topicExchange()).with(routingKey_CDA);
	}

	@Bean
	public Binding CDSBinding() {
		return BindingBuilder.bind(CDSQueue()).to(topicExchange()).with(routingKey_CDS);
	}

	@Bean
	public Binding PFSBinding() {
		return BindingBuilder.bind(PFSQueue()).to(topicExchange()).with(routingKey_PFS);
	}
//-------fire----------
	@Bean
	public Binding FireBureBinding() {
		return BindingBuilder.bind(FireBureQueue()).to(topicExchange()).with(routingKey_FireBure);
	}
	@Bean
	public Binding FireElectricDistinguishBinding() {
		return BindingBuilder.bind(FireElectricDistinguishQueue()).to(topicExchange()).with(routingKey_FireElectricDistinguish);
	}
	@Bean
	public Binding FireElectricEarlyWarningBinding() {
		return BindingBuilder.bind(FireElectricEarlyWarningQueue()).to(topicExchange()).with(routingKey_FireElectricEarlyWarning);
	}
	@Bean
	public Binding FireElectricErrorBinding() {
		return BindingBuilder.bind(FireElectricErrorQueue()).to(topicExchange()).with(routingKey_FireElectricError);
	}
	@Bean
	public Binding FireElectricNowBinding() {
		return BindingBuilder.bind(FireElectricNowQueue()).to(topicExchange()).with(routingKey_FireElectricNow);
	}
	@Bean
	public Binding FireElectricWarningBinding() {
		return BindingBuilder.bind(FireElectricWarningQueue()).to(topicExchange()).with(routingKey_FireElectricWarning);
	}
	@Bean
	public Binding FireSmokeBinding() {
		return BindingBuilder.bind(FireSmokeQueue()).to(topicExchange()).with(routingKey_FireSmoke);
	}
	@Bean
	public Binding FireSmokeRelayBinding() {
		return BindingBuilder.bind(FireSmokeRelayQueue()).to(topicExchange()).with(routingKey_FireSmokeRelay);
	}
	@Bean
	public Binding FireSmokeRelayWarmBinding() {
		return BindingBuilder.bind(FireSmokeRelayWarmQueue()).to(topicExchange()).with(routingKey_FireSmokeRelayWarm);
	}
	@Bean
	public Binding FireUserMessageBinding() {
		return BindingBuilder.bind(FireUserMessageQueue()).to(topicExchange()).with(routingKey_FireUserMessage);
	}
	@Bean
	public Binding FireUserMessageFcBinding() {
		return BindingBuilder.bind(FireUserMessageFcQueue()).to(topicExchange()).with(routingKey_FireUserMessageFc);
	}
	@Bean
	public Binding FireUserMessageFcSensorBinding() {
		return BindingBuilder.bind(FireUserMessageFcSensorQueue()).to(topicExchange()).with(routingKey_FireUserMessageFcSensor);
	}
	@Bean
	public Binding FireWaterCycleBinding() {
		return BindingBuilder.bind(FireWaterCycleQueue()).to(topicExchange()).with(routingKey_FireWaterCycle);
	}
	@Bean
	public Binding FireWaterCycleV2Binding() {
		return BindingBuilder.bind(FireWaterCycleV2Queue()).to(topicExchange()).with(routingKey_FireWaterCycleV2);
	}
	@Bean
	public Binding FireWaterNowSmr1000Binding() {
		return BindingBuilder.bind(FireWaterNowSmr1000Queue()).to(topicExchange()).with(routingKey_FireWaterNowSmr1000);
	}
	@Bean
	public Binding FireWaterWarmBinding() {
		return BindingBuilder.bind(FireWaterWarmQueue()).to(topicExchange()).with(routingKey_FireWaterWarm);
	}
	@Bean
	public Binding FireWaterWarmSmr1000Binding() {
		return BindingBuilder.bind(FireWaterWarmSmr1000Queue()).to(topicExchange()).with(routingKey_FireWaterWarmSmr1000);
	}
	@Bean
	public Binding FireWaterWarmV2Binding() {
		return BindingBuilder.bind(FireWaterWarmV2Queue()).to(topicExchange()).with(routingKey_FireWaterWarmV2);
	}
//---fire-------

//---lamp-------
	@Bean
	public Binding LampAlarmDoorBinding() {
		return BindingBuilder.bind(LampAlarmDoorQueue()).to(topicExchange()).with(routingKey_LampAlarmDoor);
	}
	@Bean
	public Binding LampAlarmPhaseLossBinding() {
		return BindingBuilder.bind(LampAlarmPhaseLossQueue()).to(topicExchange()).with(routingKey_LampAlarmPhaseLoss);
	}
	@Bean
	public Binding LampAlarmPowerBinding() {
		return BindingBuilder.bind(LampAlarmPowerQueue()).to(topicExchange()).with(routingKey_LampAlarmPower);
	}
	@Bean
	public Binding LampAlarmTransFormerBinding() {
		return BindingBuilder.bind(LampAlarmTransFormerQueue()).to(topicExchange()).with(routingKey_LampAlarmTransFormer);
	}
	@Bean
	public Binding LampAlarmWaterenterBinding() {
		return BindingBuilder.bind(LampAlarmWaterenterQueue()).to(topicExchange()).with(routingKey_LampAlarmWaterenter);
	}
	@Bean
	public Binding LampAlarmZoneInfraredBinding() {
		return BindingBuilder.bind(LampAlarmZoneInfraredQueue()).to(topicExchange()).with(routingKey_LampAlarmZoneInfrared);
	}
	@Bean
	public Binding LampPathLineBinding() {
		return BindingBuilder.bind(LampPathLineQueue()).to(topicExchange()).with(routingKey_LampPathLine);
	}


//---lamp-------

//----st--------
	@Bean
	public Binding StPptnRBinding() {
		return BindingBuilder.bind(StPptnRQueue()).to(topicExchange()).with(routingKey_StPptnR);
	}
	@Bean
	public Binding StSsylRBinding() {
		return BindingBuilder.bind(StSsylRQueue()).to(topicExchange()).with(routingKey_StSsylR);
	}
	@Bean
	public Binding StSsswRBinding() {
		return BindingBuilder.bind(StSsswRQueue()).to(topicExchange()).with(routingKey_StSsswR);
	}
	@Bean
	public Binding StRiverRBinding() {
		return BindingBuilder.bind(StRiverRQueue()).to(topicExchange()).with(routingKey_StRiverR);
	}
	@Bean
	public Binding StRsvrRBinding() {
		return BindingBuilder.bind(StRsvrRQueue()).to(topicExchange()).with(routingKey_StRsvrR);
	}
	@Bean
	public Binding StSdsjRBinding() {
		return BindingBuilder.bind(StSdsjRQueue()).to(topicExchange()).with(routingKey_StSdsjR);
	}
	@Bean
	public Binding StSsgkRBinding() {
		return BindingBuilder.bind(StSsgkRQueue()).to(topicExchange()).with(routingKey_StSsgkR);
	}

//----st--------

//------special
	@Bean
	public Binding SpecialPopulationBloodPressureBinding() {
		return BindingBuilder.bind(SpecialPopulationBloodPressureQueue()).to(topicExchange()).with(routingKey_SpecialPopulationBloodPressure);
	}
	@Bean
	public Binding SpecialPopulationHeartRateBinding() {
		return BindingBuilder.bind(SpecialPopulationHeartRateQueue()).to(topicExchange()).with(routingKey_SpecialPopulationHeartRate);
	}
	@Bean
	public Binding SpecialPopulationSleepBinding() {
		return BindingBuilder.bind(SpecialPopulationSleepQueue()).to(topicExchange()).with(routingKey_SpecialPopulationSleep);
	}
	@Bean
	public Binding SpecialPopulationWalkBinding() {
		return BindingBuilder.bind(SpecialPopulationWalkQueue()).to(topicExchange()).with(routingKey_SpecialPopulationWalk);
	}
	@Bean
	public Binding SpecialPopulationFallBinding() {
		return BindingBuilder.bind(SpecialPopulationFallQueue()).to(topicExchange()).with(routingKey_SpecialPopulationFall);
	}

//-----net
	@Bean
	public Binding NetEventCrosslineInfoBinding() {
		return BindingBuilder.bind(NetEventCrosslineInfoQueue()).to(topicExchange()).with(routingKey_NetEventCrosslineInfo);
	}
	@Bean
	public Binding NetEventLeftInfoBinding() {
		return BindingBuilder.bind(NetEventLeftInfoQueue()).to(topicExchange()).with(routingKey_NetEventLeftInfo);
	}
	@Bean
	public Binding NetEventParkingDetectionInfoBinding() {
		return BindingBuilder.bind(NetEventParkingDetectionInfoQueue()).to(topicExchange()).with(routingKey_NetEventParkingDetectionInfo);
	}
	@Bean
	public Binding NetEventRioterInfoBinding() {
		return BindingBuilder.bind(NetEventRioterInfoQueue()).to(topicExchange()).with(routingKey_NetEventRioterInfo);
	}
	@Bean
	public Binding NetEventWanderInfoBinding() {
		return BindingBuilder.bind(NetEventWanderInfoQueue()).to(topicExchange()).with(routingKey_NetEventWanderInfo);
	}

//----healthy
	@Bean
	public Binding healthyHeightWeightBinding(){
		return BindingBuilder.bind(healthyHeightWeightQueue()).to(topicExchange()).with(ROUTING_KEY_HEALTHY_HEIGHTWEIGHT);
	}
	@Bean
	public Binding healthyBodyCompositionBinding(){
		return BindingBuilder.bind(healthyBodyCompositionQueue()).to(topicExchange()).with(ROUTING_KEY_HEALTHY_BODYCOMPOSITION);
	}
	@Bean
	public Binding healthyBloodPressureBinding(){
		return BindingBuilder.bind(healthyBloodPressureQueue()).to(topicExchange()).with(ROUTING_KEY_HEALTHY_BLOODPRESSURE);
	}
	@Bean
	public Binding healthyBloodOxygenBinding(){
		return BindingBuilder.bind(healthyBloodOxygenQueue()).to(topicExchange()).with(ROUTING_KEY_HEALTHY_BLOODOXYGEN);
	}
	@Bean
	public Binding healthyTemperatureBinding(){
		return BindingBuilder.bind(healthyTemperatureQueue()).to(topicExchange()).with(ROUTING_KEY_HEALTHY_TEMPERATURE);
	}
	@Bean
	public Binding healthyEcgBinding(){
		return BindingBuilder.bind(healthyEcgQueue()).to(topicExchange()).with(ROUTING_KEY_HEALTHY_ECG);
	}
	@Bean
	public Binding healthyBloodSugarBinding(){
		return BindingBuilder.bind(healthyBloodSugarQueue()).to(topicExchange()).with(ROUTING_KEY_HEALTHY_BLOODSUGAR);
	}
	@Bean
	public Binding healthyBloodFatBinding(){
		return BindingBuilder.bind(healthyBloodFatQueue()).to(topicExchange()).with(ROUTING_KEY_HEALTHY_BLOODFAT);
	}
	/**
	 * 配置
	 * 
	 * @return
	 */
	@Bean
    public ConnectionFactory firstConnectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);

		connectionFactory.setPublisherReturns(publisherReturns);
		connectionFactory.setPublisherConfirms(publisherConfirms);
        return connectionFactory;
    }

	/**
	 * 实例化操作模板
	 * 
	 * @param connectionFactory
	 * @return
	 */
	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		return new RabbitTemplate(connectionFactory);
	}

	@Bean
	public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		//开启手动 ack
		factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
		return factory;
	}


	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getVirtualHost() {
		return virtualHost;
	}

	public void setVirtualHost(String virtualHost) {
		this.virtualHost = virtualHost;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTOPIC_EXCHANGE_NAME() {
		return TOPIC_EXCHANGE_NAME;
	}

	public void setTOPIC_EXCHANGE_NAME(String TOPIC_EXCHANGE_NAME) {
		this.TOPIC_EXCHANGE_NAME = TOPIC_EXCHANGE_NAME;
	}

	public String getExchangeType() {
		return exchangeType;
	}

	public void setExchangeType(String exchangeType) {
		this.exchangeType = exchangeType;
	}

	public boolean getAutomaticRecoveryEnabled() {
		return automaticRecoveryEnabled;
	}

	public void setAutomaticRecoveryEnabled(boolean automaticRecoveryEnabled) {
		this.automaticRecoveryEnabled = automaticRecoveryEnabled;
	}

	public Long getNetworkRecoveryInterval() {
		return networkRecoveryInterval;
	}

	public void setNetworkRecoveryInterval(Long networkRecoveryInterval) {
		this.networkRecoveryInterval = networkRecoveryInterval;
	}

	public String getQueueName_SOS() {
		return queueName_SOS;
	}

	public void setQueueName_SOS(String queueName_SOS) {
		this.queueName_SOS = queueName_SOS;
	}

	public String getQueueRoutingKey_SOS() {
		return queueRoutingKey_SOS;
	}

	public void setQueueRoutingKey_SOS(String queueRoutingKey_SOS) {
		this.queueRoutingKey_SOS = queueRoutingKey_SOS;
	}
}

