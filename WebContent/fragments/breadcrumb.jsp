<%
String[] steps = (request.getParameter("p") == null ? "" : request.getParameter("p")).split("/");
String path = "";
%>
<nav class="breadcrumb">
	<ul>
	<%for (String s : steps) {
		path += "/" + s; %>
		<li><a href="<%=request.getContextPath() %><%=path %>"><%=s %></a></li>
	<%} %>
	</ul>
</nav>