package com.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.domain.BookOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookOrderDao extends BaseMapper<BookOrder> {
}
