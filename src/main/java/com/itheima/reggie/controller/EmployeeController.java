package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.service.IEmployeeService;
import com.itheima.reggie.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    /*
    * */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest servletRequest, @RequestBody Employee emp) {
        //取得密码后给密码进行加密
        String password = emp.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        // 创建基于 Lambda 表达式的查询构造器对象，并与 Employee 实体类型关联
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        // 添加相等条件：比较 Employee 实体对象的用户名属性与 emp 对象的用户名值是否相等
        queryWrapper.eq(Employee::getUsername,emp.getUsername());//规定了比较的条件
        // 调用 employeeService 的 getOne 方法执行查询，并将结果赋值给 aEmployee 变量
        Employee one = this.employeeService.getOne(queryWrapper);
        //有没有用户
        if (one == null) {
            return R.error("用户不存在");
        }
        //密码对不对
        if (!one.getPassword().equals(password)) {
            return R.error("密码不正确");
        }
        //账户有无被禁用
        if (one.getStatus() == 0) {
            return R.error("账户已经被禁用");
        }
        //返回成功
        //错误：return R.success("登陆成功");
        //具体来说，用户的ID可能是一个整数或字符串，
        // 它的值通常是从数据库中获取的。
        // 这个ID可以代表用户在系统中的唯一身份标识，相当于一个特定用户的标签。
        servletRequest.getSession().setAttribute("employee",one.getId());
        return R.success(one);

//        //加密密码
//        String password = DigestUtils.md5DigestAsHex(emp.getPassword().getBytes());
//        // 创建基于 Lambda 表达式的查询构造器对象，并与 Employee 实体类型关联
//        LambdaQueryWrapper<Employee> queryWrapper = Wrappers.lambdaQuery(new Employee());
//        // 添加相等条件：比较 Employee 实体对象的用户名属性与 emp 对象的用户名值是否相等
//        queryWrapper.eq(Employee::getUsername,emp.getUsername());
//        // 调用 employeeService 的 getOne 方法执行查询，并将结果赋值给 aEmployee 变量
//        Employee aEmployee = this.employeeService.getOne(queryWrapper);
//        //Employee aEmployee = this.employeeService.login(employee.getUsername());
//        if(aEmployee == null){
//            return R.error("用户名不匹配");
//        }
//        if (!aEmployee.getPassword().equals(password)){
//            return R.error("密码错误");
//        }
//        if (aEmployee.getStatus() == 0) {
//            return R.error("该账户已禁用");
//        }
//        servletRequest.getSession().setAttribute("employee",aEmployee.getId());
//        return R.success(aEmployee);
    }


    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest servletRequest){
        servletRequest.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }
}
