package com.lq.maintenance.common;

import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ExampleMapper;

public interface GlobalBaseMapper<T> extends BaseMapper<T>, ExampleMapper<T> {

}
