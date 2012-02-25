<%
String path = "";
%>
<nav class="breadcrumb">
	<ul>
		<li><a href="<%=request.getContextPath() %>">Home</a></li>
	<%for (String s : pages) {
		s = s.trim();
		
		if (s.length() == 0) {
			continue;
		}
		path += "/" + s; %>
		<li><a href="<%=request.getContextPath() %><%=path %>"><%=s %></a></li>
	<%} %>
	</ul>
</nav>