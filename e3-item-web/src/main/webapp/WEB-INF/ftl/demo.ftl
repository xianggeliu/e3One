<html>
	<head>
		<title>英雄留步</title>
	</head>
	<body>
		学生信息:<br>
		学号 : ${student.id}<br>
		姓名 : ${student.name}<br>
		性别 : ${student.sex}<br>
		住址 : ${student.address} <br>
		
		<table border="1">
			
			<tr>
				<th>索引号</th>
				<th>学号</th>
				<th>姓名</th>
				<th>性别</th>
				<th>家庭住址</th>
			</tr>
			<#list list as student> 
				<#if student_index % 2 == 0>
				<tr bgcolor="yellow">
				<#else>
				<tr bgcolor="green">
				</#if>
					<td>${student_index}</td>
					<td>${student.id}</td>
					<td>${student.name}</td>
					<td>${student.sex}</td>
					<td>${student.address}</td>
				</tr>
			</#list>
		</table>
		<br>
		日期格式 : ${date?date} <br>
		时间格式 : ${date?time} <br>
		日期时间格式 : ${date?datetime} <br>
	 	自定义格式 : ${date?string("yyyy/MM/dd HH:mm:ss")}
		<br>
		null值的显示 : ${vl!}
		<#if vl??>
			这是个故事
		<#else>
			这不是个故事
		</#if>
		<br>
		这是个引用啊 啊 :
			<#include 'test.ftl'>


	</body>
</html>