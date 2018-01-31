# 编码规范

### 1 命名约定

约定优于配置(convention over configuration)，此框架约定了很多编程规范，下面一一列举：

```
- service类，需要在叫名`service`的包下，并以`Service`结尾，如`UpmsSystemServiceImpl`

- controller类，需要在以`controller`结尾的包下，类名以Controller结尾，如`UpmsSystemController.java`，并继承`BaseController`

- spring task类，需要在叫名`task`的包下，并以`Task`结尾，如`TestTask.java`

- mapper.xml，需要在名叫`mapper`的包下，并以`Mapper.xml`结尾，如`UpmsSystemMapper.xml`

- mapper接口，需要在名叫`mapper`的包下，并以`Mapper`结尾，如`UpmsSystemMapper.java`

- model实体类，需要在名叫`model`的包下，命名规则为数据表转驼峰规则，如`UpmsSystem.java`

- spring配置文件，命名规则为`applicationContext-*.xml`

- 类名：首字母大写驼峰规则；方法名：首字母小写驼峰规则；常量：全大写；变量：首字母小写驼峰规则，尽量非缩写
- 数据表命名为：`子系统`_`表`，如`upms_system`

- springmvc配置加到对应模块的`springMVC-servlet.xml`文件里

- 配置文件放到`src/main/resources`目录下

- 静态资源文件放到`src/main/webapp/resources`目录下

- jsp文件，需要在`/WEB-INF/jsp`目录下

- `RequestMapping`和返回物理试图路径的url尽量写全路径，如：`@RequestMapping("/manage")`、`return "/manage/index"`

- `RequestMapping`指定method

- 模块命名为`项目`-`子项目`-`业务`，如`hdong-upms-web`

```

### 2 数据库字典：

需要在sys_dict表中维护，然后java层转换为枚举。java层只用枚举，前台只用枚举的name，不需要关心code。前台枚举转换公共方法：

`HdDict.getDictDesc(app,type,name);//获取字典对应的名称，一般用于表格中；返回String`

`HdDict.getDictArr(app,type);//获取一类型对应的字典集合，一般用于下拉框；返回[{name:'name',desc:'desc'}]`

下拉框通过$("#aaa").initSelectByDict('UPMS','SYSTEM_STATUS','ABNORMAL');来为select添加option

### 3 序列号

为了兼容不同的数据库以及数据库拆分，使用redis做序列号发生器，通过`SequenceUtil.getInt(TableName.class); `

`SequenceUtil.getLong(TableName.class);`来获取序列号，其中int的序列号会到达Integer.MAX_VALUE自动回滚到1重复使用。

### 4 错误代码

`UpmsResultConstant `

`UpmsResult`，错误代码都列在*ResultConstant枚举类中(可以一个大模块建一个枚举类),前台框架通过获取result.code判断成功失败，result.msg获取信息也可以把信息封装在result.data里面。

### 5 前后台校验框架：

前台使用jquery.validate, 原因好用简单。

| 序号   | 规则                 | 描述                                       |
| ---- | ------------------ | ---------------------------------------- |
| 1    | required:true      | 必须输入的字段。                                 |
| 2    | remote:"check.php" | 使用 ajax 方法调用 check.php 验证输入值。            |
| 3    | email:true         | 必须输入正确格式的电子邮件。                           |
| 4    | url:true           | 必须输入正确格式的网址。                             |
| 5    | date:true          | 必须输入正确格式的日期。日期校验 ie6 出错，慎用。              |
| 6    | dateISO:true       | 必须输入正确格式的日期（ISO），例如：2009-06-23，1998/01/22。只验证格式，不验证有效性。 |
| 7    | number:true        | 必须输入合法的数字（负数，小数）。                        |
| 8    | digits:true        | 必须输入整数。                                  |
| 9    | creditcard:        | 必须输入合法的信用卡号。                             |
| 10   | equalTo:"#field"   | 输入值必须和 #field 相同。                        |
| 11   | accept:            | 输入拥有合法后缀名的字符串（上传文件的后缀）。                  |
| 12   | maxlength:5        | 输入长度最多是 5 的字符串（汉字算一个字符）。                 |
| 13   | minlength:10       | 输入长度最小是 10 的字符串（汉字算一个字符）。                |
| 14   | rangelength:[5,10] | 输入长度必须介于 5 和 10 之间的字符串（汉字算一个字符）。         |
| 15   | range:[5,10]       | 输入值必须介于 5 和 10 之间。                       |
| 16   | max:5              | 输入值不能大于 5。                               |
| 17   | min:10             | 输入值不能小于 10。                              |
| 18   | minNumber:2        | 小数位数不能大于2位

后台使用hibernate 的 validator，demo如下：

    String validStr = ValidatorUtil.validateWithHtml(upmsSystem);
    if(StringUtils.isNotBlank(validStr)) {
    	return new UpmsResult(UpmsResultConstant.PARAM_VALID_ERROR, validStr);
    }
1. @Null   被注释的元素必须为 **null**  

2. @NotNull    被注释的元素必须不为 **null**  

3. @AssertTrue     被注释的元素必须为 **true**  

4. @AssertFalse    被注释的元素必须为 **false**  

5. @Min(value)     被注释的元素必须是一个数字，其值必须大于等于指定的最小值  

6. @Max(value)     被注释的元素必须是一个数字，其值必须小于等于指定的最大值  

7. @DecimalMin(value)  被注释的元素必须是一个数字，其值必须大于等于指定的最小值  

8. @DecimalMax(value)  被注释的元素必须是一个数字，其值必须小于等于指定的最大值  

9. @Size(max=, min=)   被注释的元素的大小必须在指定的范围内  

10. @Digits (integer, fraction)     被注释的元素必须是一个数字，其值必须在可接受的范围内  

11. @Past   被注释的元素必须是一个过去的日期  

12. @Future     被注释的元素必须是一个将来的日期  

13. @Pattern(regex=,flag=)  被注释的元素必须符合指定的正则表达式  

    Hibernate Validator 附加的 constraint  

14. @NotBlank(message =)   验证字符串非**null**，且长度必须大于0  

15. @Email  被注释的元素必须是电子邮箱地址  

16. @Length(min=,max=)  被注释的字符串的大小必须在指定的范围内  

17. @NotEmpty   被注释的字符串的必须非空  

18. @Range(min=,max=,message=)  被注释的元素必须在合适的范围内 
