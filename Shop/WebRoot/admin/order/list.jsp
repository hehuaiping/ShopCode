<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Language" content="zh-cn">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="${pageContext.request.contextPath}/css/Style1.css" rel="stylesheet" type="text/css" />
		<script language="javascript" src="${pageContext.request.contextPath}/js/public.js"></script>
		
		<!-- 弹出层插件 -->
		<link href="${pageContext.request.contextPath}/css/popup_layer.css" type="text/css" rel="stylesheet"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/popup_layer.js"></script>		
		<!-- 调用插件弹出层的方法 -->
		<script type="text/javascript">
			$(function(){
				//弹出层插件调用
				new PopupLayer({
					trigger:".clickedElement",
					popupBlk:"#showDiv",
					closeBtn:"#closeBtn",
					useOverlay:true
				});
				
			});
			
			//Ajax异步请求数据
			function findOrderItem(oid) {
				//清理上次数据
				$("#showDivTab").html("");
				$("#shodDivOid").html("");
				
				$.post("${pageContext.request.contextPath}/AdminServlet?method=findOrderItemByOid",
				{"oid":oid},
				function(data){
					//将json数据解析 
					/*
					[{"pimage":"products/1/c_0001.jpg","shop_price":1299.0,"pname":"小米 4c 标准版","subtotal":1299.0,"count":1},
					{"pimage":"products/1/c_0013.jpg","shop_price":1799.0,"pname":"努比亚（nubia）My 布拉格","subtotal":1799.0,"count":1}]
					*/
					//遍历data数据
					var content = "<tr id='showTableTitle'>" +
							"<th width='20%'>图片</th>" +
							"<th width='25%'>商品</th>" +
							"<th width='20%'>价格</th>" +
							"<th width='15%'>数量</th>" +
							"<th width='20%'>小计</th>" +
						"</tr>";
					for(var i =0;i<data.length;i++) {
						content += "<tr style='text-align: center;'><td><img src='${pageContext.request.contextPath}/"+data[i].pimage+"' width='70' height='60'></td><td><a target='_blank'>"+data[i].pname+"</a></td><td>￥"+data[i].shop_price+"</td><td>"+data[i].count+"</td><td><span class='subtotal'>￥"+data[i].subtotal+"</span></td></tr> ";
					}
					
					//将html代码写入document
					$("#showDivTab").append(content);
					//订单编号
					$("#shodDivOid").html(oid);
				},"json");
			}
			
		</script>
		
	</HEAD>
	<body>
	
		<form id="Form1" name="Form1" action="${pageContext.request.contextPath}/user/list.jsp" method="post">
			<table cellSpacing="1" cellPadding="0" width="100%" align="center" bgColor="#f5fafe" border="0">
				<TBODY>
					<tr>
						<td class="ta_01" align="center" bgColor="#afd1f3">
							<strong>订单列表</strong>
						</TD>
					</tr>
					
					<tr>
						<td class="ta_01" align="center" bgColor="#f5fafe">
							<table cellspacing="0" cellpadding="1" rules="all"
								bordercolor="gray" border="1" id="DataGrid1"
								style="BORDER-RIGHT: gray 1px solid; BORDER-TOP: gray 1px solid; BORDER-LEFT: gray 1px solid; WIDTH: 100%; WORD-BREAK: break-all; BORDER-BOTTOM: gray 1px solid; BORDER-COLLAPSE: collapse; BACKGROUND-COLOR: #f5fafe; WORD-WRAP: break-word">
								<tr
									style="FONT-WEIGHT: bold; FONT-SIZE: 12pt; HEIGHT: 25px; BACKGROUND-COLOR: #afd1f3">

									<td align="center" width="10%">
										序号
									</td>
									<td align="center" width="10%">
										订单编号
									</td>
									<td align="center" width="5s%">
										订单金额
									</td>
									<td align="center" width="5%">
										收货人
									</td>
									<td align="center" width="5%">
										订单状态
									</td>
									<td align="center" width="10%">
										订单时间
									</td>
									<td align="center" width="10%">
										联系方式
									</td>
									<td align="center" width="10%">
										订单详情
									</td>
								</tr>
								<%int i = 0; %>
								<c:forEach items="${sessionScope.ordersList }" var="order">
									<%i++; pageContext.setAttribute("i", i);%>
									<input type="hidden" value="${i}" >
									<tr onmouseover="this.style.backgroundColor = 'white'"
									onmouseout="this.style.backgroundColor = '#F5FAFE';">
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="10%">
										${i} 
									</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="20%">
										${order.oid }
									</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="10%">
										${order.total }
									</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="10%">
										${order.name }
									</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="5%">
										<c:if test="${order.state==0 }">未支付</c:if>
										<c:if test="${order.state==1 }">已支付</c:if>
									</td>		
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="14%">
										${order.ordertime }
									</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="10%">
										${order.telephone }
									</td>
									<td align="center" style="HEIGHT: 22px">
										<input type="button" value="订单详情" class="clickedElement" onclick="findOrderItem('${order.oid}')"/>
									</td>
									</tr>
								</c:forEach>
								
							</table>
						</td>
					</tr>
					
				</TBODY>
			</table>
		</form>
		
		<!-- 弹出层 HaoHao added -->
        <div id="showDiv" class="blk" style="display:none;">
            <div class="main">
                <h2>订单编号：<span id="shodDivOid"  style="font-size: 13px;color: #999;"></span></h2>
                <a href="javascript:void(0);" id="closeBtn" class="closeBtn">关闭</a>
               <!-- <div id="loading" style="padding-top:30px;text-align: center;">
                	<img alt=""  style="border: 10px"  src="${pageContext.request.contextPath }/img/timg3.gif">
                </div>  --> 
				<div style="padding:20px;">
					<table id="showDivTab" style="width:100%">
						
					</table>
				</div>
            </div>
            
        </div>
		
	</body>
</HTML>

