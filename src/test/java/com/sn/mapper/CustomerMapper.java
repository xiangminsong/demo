package com.sn.mapper;

import com.sn.po.Customer;
import org.apache.ibatis.annotations.Select;

public interface CustomerMapper {
//    @Select("SELECT * FROM customer WHERE cust_id = #{custId}")
    Customer queryById(Long custId);
    
    int deleteById(Long custId);
}