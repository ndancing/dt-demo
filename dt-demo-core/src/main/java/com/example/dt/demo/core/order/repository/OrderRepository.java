package com.example.dt.demo.core.order.repository;

import com.example.dt.demo.core.order.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface OrderRepository {

    @Insert(" insert into `order` (id,create_time,status,product_id,total_amount,count,user_id) " +
            " values ( #{id},#{createTime},#{status},#{productId},#{totalAmount},#{count},#{userId})")
    int save(Order order);

    @Update("update `order` set status = #{status} where id LIKE #{id}")
    int updateStatus(Order order);

    @Select("select * from `order`")
    List<Order> listAll();

    @Select("select id,create_time,status,product_id,total_amount,count,user_id from `order` where id =#{orderId}")
    Order findByOrderId(String orderId);
}
