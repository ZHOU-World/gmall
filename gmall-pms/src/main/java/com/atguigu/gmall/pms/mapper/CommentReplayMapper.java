package com.atguigu.gmall.pms.mapper;

import com.atguigu.gmall.pms.entity.CommentReplayEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品评价回复关系
 * 
 * @author Guan FuQing
 * @email moumouguan@gmaill.com
 * @date 2021-06-22 11:43:20
 */
@Mapper
public interface CommentReplayMapper extends BaseMapper<CommentReplayEntity> {
	
}
