# 盯市编码规范

### 1 数据库字典：

需要在sys_dict表中维护，然后java层转换为枚举。java层只用枚举，前台只用枚举的name，不需要关心code。前台枚举转换公共方法：

`HdDict.getDictDesc(app,type,name);//获取字典对应的名称，一般用于表格中；返回String`

`HdDict.getDictArr(app,type);//获取一类型对应的字典集合，一般用于下拉框；返回[{name:'name',desc:'desc'}]`