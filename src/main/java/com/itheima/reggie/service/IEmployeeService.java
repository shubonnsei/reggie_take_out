package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.entity.Employee;

public interface IEmployeeService extends IService<Employee> {

    Employee login(String username);
}
