package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.model.hosp.HospitalSet;
import com.mapper.HospitalSetMapper;
import com.service.HospitalSetService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 创作时间：2021/4/25 19:57
 * 作者：张林
 * 需要我们来实现他的实现类，然后把mapper和我们的泛型类给他给他
 */
@Service
public class HospitalSetServiceImpl extends ServiceImpl<HospitalSetMapper, HospitalSet> implements HospitalSetService {
        @Resource
        private HospitalSetMapper hospitalSetMapper;
}
