package com.koral.vister.test;

import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @Description:
 * @Author: chejianyun
 * @Date: 2024/5/25
 */
@Component
  public class JdbcFooRepository extends FooRepository {
 
      public JdbcFooRepository(DataSource dataSource) {
          // ...
      }
  }