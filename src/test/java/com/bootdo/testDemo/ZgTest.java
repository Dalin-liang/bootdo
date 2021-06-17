package com.bootdo.testDemo;

import com.bootdo.data.receive.MsgService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZgTest {

    @Autowired
    private MsgService msgService;

    public static void main(String[] args) {
        ZgTest z = new ZgTest();
        z.getLearn();
    }
    @Test
    public void getLearn(){
//        LearnResource learnResource=learnService.selectByKey(1001L);
//        Assert.assertThat(learnResource.getAuthor(),is("嘟嘟MD独立博客"));
        System.out.println("------------");
        String sourceType = "9", sourceMenu = "specialSOSData";
        String msg= "[{\"repdate\":\"2020-11-04 19:14:53\",\"repphone\":\"18498833407\",\"repname\":\"试用设备\",\"sex\":\"\",\"lat_lon\":\"23.2763656,113.8188756\",\"eventdesc\":\"设备告警提示：系统识别到,【SOS（试用设备）】发生了告警，该设备位于广东省 广州市 增城区 荔乡路 靠近山水大厦小区\",\"eventaddr\":\"广东省 广州市 增城区 荔乡路 靠近山水大厦小区\"}]";
        msgService.saveMsg(msg, sourceType, sourceMenu);

        System.out.println("------------1");
        /*MQ插入接报信息:[{"repdate":"2020-11-04 19:14:53","repphone":"18498833407","repname":"试用设备","sex":"","lat_lon":"23.2763656,113.8188756","eventdesc":"设备告警提示：系统识别到,【SOS（试用设备）】发生了告警，该设备位于广东省 广州市 增城区 荔乡路 靠近山水大厦小区","eventaddr":"广东省 广州市 增城区 荔乡路 靠近山水大厦小区"}]
9
specialSOSData
msgService.saveMsg(msg,"9", SendSMSConfigType.specialSOSData);

package com.bootdo.data.receive;
saveMsg*/
    }
}