<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<!-- 登录 注册 购物车... -->
<div class="container-fluid">
	<div class="col-md-4">
		<img src="img/logo2.png" />
	</div>
	<div class="col-md-5">
		<img src="img/header.png" />
	</div>
	<div class="col-md-3" style="padding-top:20px">
		<ol class="list-inline">
			<c:if test="${sessionScope.user==null}">
				<li>尊敬的游客您暂未登录,请</li>
				<li><a href="login.jsp">登录</a></li>
				<li><a href="register.jsp">注册</a></li>
			</c:if>
			<c:if test="${sessionScope.user!=null}">
				<li>尊敬的${user.name}</li>
				<li><a href="${pageContext.request.contextPath}/servlet/LoginOutServlet">注销</a></li>
				<li><a href="cart.jsp">购物车</a></li>
				<li><a href="${pageContext.request.contextPath}/OrderServlet?method=allOrder">我的订单</a></li>
			</c:if>
		</ol>
		
	</div>
</div>

<!-- 导航条 -->
<div class="container-fluid">
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand active" href="${pageContext.request.contextPath}/index">首页</a>
			</div>

			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav" id="categoryUl">
					
				</ul>
				<form class="navbar-form navbar-right" role="search">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Search">
					</div>
					<button type="submit" class="btn btn-default">Submit</button>
				</form>
			</div>
		</div>
		<script type="text/javascript">
		//发送Ajax请求
		$(function(){
			var content = ""; //拼接Html代码
			$.post("${pageContext.request.contextPath}/servlet/CategoryServlet",function(data){
				//处理json数据  [{cid:"xxx",cname:"sss"},{...}]
				for(var i = 0; i < data.length;i++) {
					content += "<li><a href='${pageContext.request.contextPath}/ProductServlet?method=productListByCid&cid="+data[i].cid+"'>"+data[i].cname+"</a></li>"; 
				}
				//将html代码嵌入<ul>标签下
				$("#categoryUl").html(content);
			},"json");
		});
</script>
	</nav>
</div>
