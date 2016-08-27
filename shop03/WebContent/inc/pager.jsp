<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %>
<pg:pager maxPageItems="15" items="${param.items}" export="curPage=pageNumber" url="${param.url}">
<c:forEach items="${param.params}" var="p">
	<pg:param name="${p}" />
</c:forEach>
	<pg:last>
		共${items }记录，共${pageNumber}页，
	</pg:last>
	当前第${curPage}页。
	<pg:first>
		<a href="${pageUrl}">首页</a>
	</pg:first>	
	<pg:prev>
		<a href="${pageUrl}">上一页</a>
	</pg:prev>
	<pg:pages>
	<%
		if(curPage==pageNumber){
	%>
		[${pageNumber}]
	<% 
		}else{
	%>
		<a href="${pageUrl}">${pageNumber}</a>
	<%		
		}
	%>	
	</pg:pages>
	<pg:next>
		<a href="${pageUrl}">下一页</a>
	</pg:next>
	<pg:last>
		<a href="${pageUrl}">尾页</a>
	</pg:last>
</pg:pager>