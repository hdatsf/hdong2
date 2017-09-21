# 盯市编码规范

### 1 数据库字典：

需要在sys_dict表中维护，然后java层转换为枚举。java层只用枚举，前台只用枚举的name，不需要关心code。前台枚举转换公共方法：

`HdDict.getDictDesc(app,type,name);//获取字典对应的名称，一般用于表格中；返回String`

`HdDict.getDictArr(app,type);//获取一类型对应的字典集合，一般用于下拉框；返回[{name:'name',desc:'desc'}]`

下拉框通过$("#aaa").initSelectByDict('UPMS','SYSTEM_STATUS','ABNORMAL');来为select添加option

### 2 序列号

为了兼容不同的数据库以及数据库拆分，使用redis做序列号发生器，通过`SequenceUtil.getInt(TableName.class); `

`SequenceUtil.getLong(TableName.class);`来获取序列号，其中int的序列号会到达Integer.MAX_VALUE自动回滚到1重复使用。

### 3 错误代码

`UpmsResultConstant `

`UpmsResult`，错误代码都列在*ResultConstant枚举类中(可以一个大模块建一个枚举类),前台框架通过获取result.code判断成功失败，result.msg获取信息也可以把信息封装在result.data里面。

### 4 前台校验框架：

使用jquery.validate, 原因好用简单。

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