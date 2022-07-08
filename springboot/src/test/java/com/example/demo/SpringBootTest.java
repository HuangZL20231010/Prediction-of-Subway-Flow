package com.example.demo;

import com.example.demo.service.impl.StationDetailServiceImpl;
import com.example.demo.util.TimeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.boot.test.context.SpringBootTest
public class SpringBootTest {

   @Autowired
   private StationDetailServiceImpl a;
    @Test
    public void test()
    {
      /*  List<StationDetail> StationDetails=a.selectByLineName("1");
        StationDetails.forEach(item->{
            if("1".equals(item)){
                System.out.println(item);
            }
});*/
        TimeUtil a=new TimeUtil();
        a.setTime("2022/7/7 18:04");
        a.toApproachTime();
        System.out.println(a.getTime());

    }
}
