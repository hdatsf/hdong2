# 前台UI帮助文档

### 1日期控件：

用的github.com/smalot/bootstrap-datetimepicker。系统已经对他进行了简单的封装，规定了格式为yyyy-mm-dd hh:ii:ss的日期格式，区分了datetime、date、time三种控件。

常用用法：

	<!--日期时间框，带删除选择按钮，推荐-->
	<label for="datetime" class="col-md-3 control-label">DateTime</label>
	  <div class="col-md-7">
	    <div class="input-group date" id="datetimediv">
	      <input id="datetime" name="datetime" class="form-control" type="text" value="" readonly>
	      <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
	      <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
	    </div>
	  </div>
	</div>
	<!--日期框，没有按钮-->
	<div class="form-group">
		<label for="date" class="col-md-3 control-label">Date Picking</label>
		<div class="col-md-6">
			<input id="date" name="date" class="form-control" type="text" value="">
		</div>
	</div>
	<div class="form-group">
	  <label for="time" class="col-md-3 control-label">Time Picking</label>
	  <div class="col-md-6">
	  	<input id="time" name="time" class="form-control" type="text" value="">
	  </div>
	</div>
	<script>
	$('#datetimediv').hdDatetimePicker();
	$('#date').hdDatePicker();
	$('#time').hdTimePicker();
	</script>
### 2多选框

	<div class="col-md-8">
	  <select id="roleId" name="roleId" multiple="multiple">
	    <c:forEach var="upmsRole" items="${upmsRoles}">
	    	<option value="${upmsRole.id}" ${upmsRole.selected}>${upmsRole.name}</option>
	    </c:forEach>
	  </select>
	</div>
	<script>
	$('#userRoleDialog #roleId').multiselect({
	  maxHeight: 250,
	  enableFiltering: true
	});
	</script>
### 3 单选字典框

	<div class="col-md-9">
	  <select id="sex" name="sex" class="form-control">
	  </select>
	</div>
	<script>
	HdDict.initSelect("UPMS","USER_SEX",$("#createDialog #sex"),"MALE");
	</script>
### 4 form表单校验

```
$('#createForm').validate();
if (!$('#createForm').valid()){
	return;
}
```

### 5 ajax请求

	$.ajax({
		type : 'post',
		url : '${basePath}/manage/user/create',
		data : $('#createForm').serialize(),
		success : function(result) {
			if (result.code == 1) {
				$.hdConfirm({
					type : 'blue',
					content : '保存成功！',
					autoClose : 'confirm|3000',
					buttons : {
						confirm : {
							text : '确认',
							action : function() {
								HdDialog.close(true);
							}
						}
					}
				});
			} else {
				//该函数会自动判断何种方式显示错误信息
				$.hdErrorConfirm(result);
			}
		}
	});
6 表格常用函数

```{field: &#39;startTimestamp&#39;, title: &#39;创建时间&#39;, sortable: true, align: &#39;center&#39;,formatter:function(value,row,index){return new Date(value).format(&#39;yyyy-MM-dd hh:mm:ss&#39;);}},
//毫秒转时间
{field: 'startTimestamp', title: '创建时间', formatter:function(value,row,index){return new Date(value).format('yyyy-MM-dd hh:mm:ss');}},
			
//数据字典转换
{field: 'sex', title: '性别', formatter:function(name){return HdDict.getDictDesc('UPMS','USER_SEX',name);}},
			
```

​			