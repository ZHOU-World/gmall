package com.atguigu.gmall.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gmall.common.bean.PageResultVo;
import com.atguigu.gmall.common.bean.PageParamVo;
import com.atguigu.gmall.sms.entity.SeckillSkuEntity;

import java.util.Map;

/**
 * 秒杀活动商品关联
 *
 * @author Guan FuQing
 * @email moumouguan@gmaill.com
 * @date 2021-06-22 11:45:41
 */
public interface SeckillSkuService extends IService<SeckillSkuEntity> {

    PageResultVo queryPage(PageParamVo paramVo);
}

