<%
String[] steps = (request.getParameter("p") == null ? "" : request.getParameter("p")).split("/");
String path = "";
%>
<nav class="breadcrumb">
	<ul>
		<li><a href="<%=request.getContextPath() %>">Home</a></li>
	<%for (String s : steps) {
		path += "/" + s; %>
		<li><a href="<%=request.getContextPath() %><%=path %>"><%=s %></a></li>
	<%} %>
	</ul>
</nav>