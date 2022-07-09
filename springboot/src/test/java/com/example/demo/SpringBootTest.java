package com.example.demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.mapper.StationDetailMapper;
import com.example.demo.pojo.table.StationDetail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


@org.springframework.boot.test.context.SpringBootTest
public class SpringBootTest {

    @Autowired
    private StationDetailMapper stationDetailMapper;


    @Test
    public void test()
    {
        List<String> lineNameList = new ArrayList<>();
        QueryWrapper<StationDetail> queryMapper = new QueryWrapper<>();
        List<StationDetail> stationDetailList = stationDetailMapper.selectList(queryMapper.orderByAsc("lineName"));
        for (StationDetail stationDetail : stationDetailList)
        {
            if (lineNameList.contains(stationDetail.getLinename()))
                continue;
            lineNameList.add(stationDetail.getLinename());
        }

        System.out.print(lineNameList);
    }
}
