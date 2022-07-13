package com.example.demo.service;

public interface CongestionResultService
{
    // 根据congestion表中的内容计算拥堵指数，并插入到该表中
    void insertCongestionResult();
}
