<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>	
<!DOCTYPE html>
<html>

<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>会员登录</title>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="css/style.css" type="text/css" />

<style>
body {
	margin-top: 20px;
	margin: 0 auto;
}

.carousel-inner .item img {
	width: 100%;
	height: 300px;
}
</style>
</head>

<body>


	<!-- 引入header.jsp -->
	<jsp:include page="/header.jsp"></jsp:include>

	<div class="container">
		<div class="row">
			<div style="margin: 0 auto; margin-top: 10px; width: 950px;">
				<strong>我的订单</strong>
				<table class="table table-bordered">
					<c:forEach items="${ordersList }" var="orders">
						<tbody>
							<tr class="success">
							<th colspan="2">订单编号:${orders.oid}</th>
							<th colspan="0">订单金额:<font color="green">${orders.total}</font></th>
							<th colspan="1">支付状态:
								<c:if test="${orders.state==0 }">
									<a href="${pageContext.request.contextPath}/order_info.jsp?orders=orders"><font color="#e4393c">还未支付</font></a>
								</c:if>
								<c:if test="${orders.state==1 }">
									<font color="green">已支付</font>
								</c:if>
							</th>
								<th colspan="0">操作:
								<c:if test="${orders.state==0 }">
									<a href="${pageContext.request.contextPath}/OrderServlet?method=deleteOrder&oid=${orders.oid}">取消订单</a>
								</c:if>
								<c:if test="${orders.state==1 }">
									<font color="green">无操作</font>
								</c:if>
								
								</th>
					 	    </tr>
							<tr class="warning">
							<th>图片</th>
							<th>商品</th>
							<th>价格</th>
							<th>数量</th>
							<th>小计</th>
							</tr>
						<c:forEach items="${orders.orderItem }" var="orderItem" >
							<tr class="active">
							<td width="20%"><input type="hidden" name="id"
							value="22"> <img src="${orderItem.product.pimage}" width="70"
							height="60"></td>
							<td width="15%"><a href="#">${orderItem.product.pname}</a></td>
							<td width="20%">￥${orderItem.product.shop_price}</td>
							<td width="15%">${orderItem.count }</td>
							<td width="15%"><span class="subtotal">￥${orderItem.subtotal }</span></td>
							</tr>
						</c:forEach>
					</tbody>
					
				</c:forEach>
						
				</table>
			</div>
		</div>
		<div style="text-align: center;">
			<ul class="pagination">
				<li class="disabled"><a href="#" aria-label="Previous"><span
						aria-hidden="true">&laquo;</span></a></li>
				<li class="active"><a href="#">1</a></li>
				<li><a href="#">2</a></li>
				<li><a href="#">3</a></li>
				<li><a href="#">4</a></li>
				<li><a href="#">5</a></li>
				<li><a href="#">6</a></li>
				<li><a href="#">7</a></li>
				<li><a href="#">8</a></li>
				<li><a href="#">9</a></li>
				<li><a href="#" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
			</ul>
		</div>
	</div>

	<!-- 引入footer.jsp -->
	<jsp:include page="/footer.jsp"></jsp:include>
	
</body>

</html>