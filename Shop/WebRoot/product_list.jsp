<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
	width: 100%;
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


	<div class="row" style="width: 1210px; margin: 0 auto;">
		<div class="col-md-12">
			<ol class="breadcrumb">
				<li><a href="#">首页</a></li>
			</ol>
		</div>
	
		<c:forEach items="${requestScope.page.list }" var="prod">
			
			<div class="col-md-2" style="height: 250px">
				<a href="${pageContext.request.contextPath}/ProductServlet?method=productInfo&pid=${prod.pid}&cid=${cid}&thisPage=${page.thisPage}"> <img src=" ${pageContext.request.contextPath}/${prod.pimage}"
					width="170" height="170" style="display: inline-block;">
				<!-- <a href="${pageContext.request.contextPath}/ProductInfoServlet?pid=${prod.pid}&cid=${cid}&thisPage=${page.thisPage}"> <img src=" ${pageContext.request.contextPath}/${prod.pimage}"
					width="170" height="170" style="display: inline-block;">
				</a> -->
				<p>
					<a href="product_info.html" style='color: green'>${prod.pname}</a>
				</p>
				<p>
					<font color="#FF0000">商城价：&yen;${prod.shop_price }</font>
				</p>
			</div>
		</c:forEach>
	
	</div>

	<!--分页 -->
	<div style="width: 380px; margin: 0 auto; margin-top: 50px;">
		<ul class="pagination" style="text-align: center; margin-top: 10px;">
			
    	<!-- 分页逻辑开始 -->
    
    <!-- 总页数小于10,显示所有页码 -->
    <c:if test="${page.countPage<=10 }">
    	<c:set var="begin" value="1" scope="page"></c:set>
    	<c:set var="end" value="${page.countPage }" scope="page"></c:set>
    </c:if>
     <c:if test="${page==null }">
     	<script>
     		//window.location.href="${pageContext.request.contextPath}/servlet/PageStuServlet?thispage=${param.thispage}";
     	</script>
    	<c:set var="begin" value="1" scope="page"></c:set>
    	<c:set var="end" value="10" scope="page"></c:set>
    </c:if>
    <!-- 总页数大于10,再跟据逻辑分页 -->
    <c:if test="${page.countPage>10 }">
    	<c:choose>
    		<c:when test="${page.thisPage<=5 }">
    			<c:set var="begin" value="1" scope="page"></c:set>
    			<c:set var="end" value="10" scope="page"></c:set>
    		</c:when>
    	
    		<c:when test="${page.thisPage>=page.countPage-5 }">
    			<c:set var="begin" value="${page.countPage-9 }" scope="page"></c:set>
    			<c:set var="end" value="${page.countPage }" scope="page"></c:set>
    		</c:when>
    		<c:otherwise>
    			<c:set var="begin" value="${page.thisPage-5 }" scope="page"></c:set>
    			<c:set var="end" value="${page.thisPage+5}" scope="page"></c:set>
    		</c:otherwise>
    	</c:choose>
    </c:if>
    
    <!--上一页 -->
    <c:if test="${page.thisPage==1 }">
    	 <li class="disabled"><a href="javascript:void(0);" aria-label="Previous"><span
					aria-hidden="true">上一页</span></a></li>
    </c:if>
    <c:if test="${page.thisPage!=1 }">
    	<li class=""><a href="${pageContext.request.contextPath }/ProductServlet?method=productListByCid&thisPage=${page.thisPage-1}&cid=${requestScope.cid}" aria-label="Previous"><span
					aria-hidden="true">上一页</span></a></li>
    </c:if>
	    <!-- 打印页码 -->
	    <c:forEach begin="${begin }" end="${end }" step="1" var="i" >
	    	<c:if test="${i==page.thisPage }">
	    		<li class="active"><a  href="${pageContext.request.contextPath }/ProductServlet?method=productListByCid&thisPage=${i}&cid=${requestScope.cid}">${i }</a></li>
	    	</c:if>
	    	<c:if test="${i!=page.thisPage }">
	    		<li><a href="${pageContext.request.contextPath }/ProductServlet?method=productListByCid&thisPage=${i}&cid=${requestScope.cid}">${i }</a></li>
	    	</c:if>
	    </c:forEach>
    <!-- 下一页 -->
    <c:if test="${page.thisPage==page.countPage }">
    	 <li class="disabled"><a href="javascript:void(0);" aria-label="Previous"><span
					aria-hidden="true">下一页</span></a></li>
    </c:if>
   <c:if test="${page.thisPage!=page.countPage }">
    	 <li><a href="${pageContext.request.contextPath }/ProductServlet?method=productListByCid&thisPage=${page.thisPage+1}&cid=${requestScope.cid}" aria-label="Next"> <span aria-hidden="true">下一页</span>
			</a></li>
    </c:if>
    
    <!-- 分页逻辑结束 -->
			
		</ul>
	</div>
	<!-- 分页结束 -->

	<!--商品浏览记录-->
	<div
		style="width: 1210px; margin: 0 auto; padding: 0 9px; border: 1px solid #ddd; border-top: 2px solid #999; height: 246px;">

		<h4 style="width: 50%; float: left; font: 14px/30px 微软雅黑">浏览记录</h4>
		<div style="width: 50%; float: right; text-align: right;">
			<a href="">more</a>
		</div>
		<div style="clear: both;"></div>

		<div style="overflow: hidden; width:1210px;height: 140px">

			<ul style="list-style: none;">
				<c:forEach items="${requestScope.historyList }" var="prod" >
					<a href="${pageContext.request.contextPath}/ProductServlet?method=productInfo&pid=${prod.pid}&cid=${cid}&thisPage=${page.thisPage}"><li
					style="width: 150px; height: 216; float: left; margin: 0 8px 0 0; padding: 0 18px 15px; text-align: center;"><img
					src="${pageContext.request.contextPath}/${prod.pimage } " width="130px" height="130px" /></li></a>
				</c:forEach>
			</ul>

		</div>
	</div>


	<!-- 引入footer.jsp -->
	<jsp:include page="/footer.jsp"></jsp:include>

</body>

</html>