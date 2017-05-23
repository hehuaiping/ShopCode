<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Language" content="zh-cn">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
		<LINK href="${pageContext.request.contextPath}/css/Style1.css" type="text/css" rel="stylesheet">
	</HEAD>
	
	<body>
		<!--  -->
		<form id="userAction_save_do" name="Form1" action="${pageContext.request.contextPath}/AdminUpdateProductServlet" method="post" enctype="multipart/form-data">
			
			
			
			<table cellSpacing="1" cellPadding="5" width="100%" align="center" bgColor="#eeeeee" style="border: 1px solid #8ba7e3" border="0">
				<tr>
					<td class="ta_01" align="center" bgColor="#afd1f3" colSpan="4"
						height="26">
						<strong><STRONG>编辑商品</STRONG>
						</strong>
						<!-- 隐藏域 商品ID -->
						<input type="hidden" value="${requestScope.prod.pid }" name="pid" />
					</td>
				</tr>
				

				<tr>
					
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						商品名称：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<input type="text" name="pname" value="${requestScope.prod.pname}" id="userAction_save_do_logonName" class="bg"/>
					</td>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						是否热门：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						
						<select name="is_hot">
							<c:if test="${requestScope.prod.is_hot==1}">
								<option value="1">是</option>
								<option value="0">否</option>
							</c:if>
							<c:if test="${requestScope.prod.is_hot==0}">
								<option value="0">否</option>
								<option value="1">是</option>
							</c:if>
							
						</select>
					</td>
				</tr>
				<tr>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						市场价格：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<input type="text" name="market_price" value="${requestScope.prod.market_price }" id="userAction_save_do_logonName" class="bg"/>
					</td>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						商城价格：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<input type="text" name="shop_price" value="${requestScope.prod.shop_price }" id="userAction_save_do_logonName" class="bg"/>
					</td>
				</tr>
				<tr>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						商品图片：
					</td>
					<td class="ta_01" bgColor="#ffffff" colspan="1">
						<input type="file" value="${requestScope.prod.pimage}" name="upload" />
					</td>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						商品库存：
					</td>
					<td class="ta_01" bgColor="#ffffff" colspan="1">
						<input type="text" value="${requestScope.prod.pnum }" name="pnum" />
					</td>
				</tr>
				<tr>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						所属分类：
					</td>
					<td class="ta_01" bgColor="#ffffff" colspan="3">
						<select id="category" name="cid">
							
						</select>
					</td>
				</tr>
				<tr>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						商品描述：
					</td>
					<td class="ta_01" bgColor="#ffffff" colspan="1">
						
						<textarea id="text" name="pdesc" rows="5" cols="30">
							${requestScope.prod.pdesc }
						</textarea>
					</td>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						商品原图片：
					</td>
					<td class="ta_01" bgColor="#ffffff" colspan="3">
						<img src="${requestScope.prod.pimage}" style="width:90px; height: 90px;" />
					</td>
				</tr>
				<tr>
					<td class="ta_01" style="WIDTH: 100%" align="center"
						bgColor="#f5fafe" colSpan="4">
						<button type="submit" id="userAction_save_do_submit"  value="确定" class="button_ok">
							&#30830;&#23450;
						</button>

						<FONT face="宋体">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</FONT>
						<button type="reset" value="重置" class="button_cancel">&#37325;&#32622;</button>

						<FONT face="宋体">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</FONT>
						<INPUT class="button_ok" type="button" onclick="history.go(-1)" value="返回"/>
						<span id="Label1"></span>
					</td>
				</tr>
			</table>
		</form>
	</body>
	<script>
		//页面加载完成时
		$(function(){
			var content = "";
			//发送Ajax请求,获取商品分类数据	
			$.post("${pageContext.request.contextPath}/AdminServlet?method=findAllCategory",function(data){
				//处理json数据
				for(var i=0;i<data.length;i++) {
					if(data[i].cid==${requestScope.prod.category.cid}) {
						content += "<option selected='selected' value='"+data[i].cid+"'>"+data[i].cname+"</option>";
					}else {
						content += "<option value='"+data[i].cid+"'>"+data[i].cname+"</option>";
					}
				}
				//将数据写入document中
				$("#category").html(content);
			},"json");
		});
		
	</script>
</HTML>