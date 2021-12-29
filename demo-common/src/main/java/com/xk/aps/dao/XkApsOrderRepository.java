package com.xk.aps.dao;

import com.xk.aps.model.entity.XkApsOrderEntity;
import com.xk.framework.jpa.reposiotry.base.BaseRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* 描述：一键生成单表模块DAO接口
* @author xk
* @date 2021-12-28
*/
public interface XkApsOrderRepository extends JpaSpecificationExecutor<XkApsOrderEntity>, BaseRepository<XkApsOrderEntity, String>  {



}
