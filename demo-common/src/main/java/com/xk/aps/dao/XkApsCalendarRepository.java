package com.xk.aps.dao;

import com.xk.framework.jpa.reposiotry.base.BaseRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.xk.aps.model.entity.XkApsCalendarEntity;

/**
* 描述：一键生成单表模块DAO接口
* @author xk
* @date 2021-12-28
*/
public interface XkApsCalendarRepository extends JpaSpecificationExecutor<XkApsCalendarEntity>, BaseRepository<XkApsCalendarEntity, String>  {



}
