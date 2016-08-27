<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品添加</title>
<script type="text/javascript" src="<%=request.getContextPath() %>/xhEditor/jquery/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/xhEditor/xheditor-1.2.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/xhEditor/xheditor_lang/zh-cn.js"></script>
<script type="text/javascript">
	$(function(){
		$("#content").xheditor({
			tools:'full',
			emots:{msn:{name:'MSN',count:40,width:22,height:22,line:8},
				   pidgin:{name:'PIDGIN',width:22,height:25,line:8,list:{smile:'微笑',cute:'可爱',wink:'眨眼',
					laugh:'大笑',victory:'胜利',sad:'伤心',cry:'哭泣',angry:'生气',shout:'大骂',curse:'诅咒',devil:'魔鬼',
					blush:'害羞',tongue:'吐舌头',envy:'羡慕',cool:'耍酷',kiss:'吻',shocked:'惊讶',sweat:'汗',sick:'生病',
					bye:'再见',tired:'累',sleepy:'睡了',question:'疑问',rose:'玫瑰',gift:'礼物',coffee:'咖啡',music:'音乐',
					soccer:'足球',good:'赞同',bad:'反对',love:'心',brokenheart:'伤心'}},				
				   ipb:{name:'IPB',width:20,height:25,line:8,list:{smile:'微笑',joyful:'开心',laugh:'笑',
							biglaugh:'大笑',w00t:'欢呼',wub:'欢喜',depres:'沮丧',sad:'悲伤',cry:'哭泣',angry:'生气',devil:'魔鬼',
							blush:'脸红',kiss:'吻',surprised:'惊讶',wondering:'疑惑',unsure:'不确定',tongue:'吐舌头',cool:'耍酷',
							blink:'眨眼',whistling:'吹口哨',glare:'轻视',pinch:'捏',sideways:'侧身',sleep:'睡了',sick:'生病',
							ninja:'忍者',bandit:'强盗',police:'警察',angel:'天使',magician:'魔法师',alien:'外星人',heart:'心动'}}
			}
		});
	});
</script>
</head>
<body>
${param.img}
<jsp:include page="inc.jsp"/>
<form action="goods.do" method="post" enctype="multipart/form-data">
<input type="hidden" name="method" value="add"/>
<table width="600" class="thin-border" align="center">
	<tr>
	<td>商品名称：</td><td><input type="text" name="name" value="${param.name }"/><span class="errorContainer">${errors.name}</span></td>
	</tr>
	<tr>
	<td>商品价格：</td><td><input type="text" name="price" value="${param.price }"/><span class="errorContainer">${errors.price}</span></td>
	</tr>
	<tr>
	<td>商品库存：</td><td><input type="text" name="stock" value="${param.stock }"/><span class="errorContainer">${errors.stock}</span></td>
	</tr>
	<tr>
	<td>商品类别：</td>
	<td>
	<select name="cid">
		<option value="">请选择商品类别</option>
		<c:forEach items="${cs }" var="c">
			<c:if test="${c.id eq param.cid }">
				<option value="${c.id }" selected="selected">${c.name }</option>
			</c:if>
			<c:if test="${c.id ne param.cid }">
				<option value="${c.id }">${c.name }</option>
			</c:if>
		</c:forEach>
	</select>
	<span class="errorContainer">${errors.cid}</span></td>
	</tr>
	<tr>
	<td>商品图片：</td><td><input type="file" name="img" value="${param.img}"/><span class="errorContainer">${errors.img}</span></td>
	</tr>
	<tr>
	<td colspan="2">商品介绍：</td>
	</tr>
	<tr>
	<td colspan="2"><textarea cols="90" rows="15" name="intro" id="content">${param.intro }</textarea></td>
	</tr>
	<tr>
	<td colspan="2">
		<input type="submit" value="添加"/><input type="reset"/>
	</td>
	</tr>
</table>
</form>
</body>
</html>