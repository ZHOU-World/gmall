package com.atguigu.gmall.oms.mapper;

import com.atguigu.gmall.oms.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author Guan FuQing
 * @email moumouguan@gmaill.com
 * @date 2021-06-22 11:44:57
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderEntity> {
	
}
