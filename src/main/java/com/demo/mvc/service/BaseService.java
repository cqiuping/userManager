package com.demo.mvc.service;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2017/8/13.
 */
public class BaseService {
    @Autowired
    protected SqlSessionFactory sqlSessionFactory;


}
