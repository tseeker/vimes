<%
String path = "";
if (current.length() > 0) {
%>
<nav class="breadcrumb">
	<ul>
		<li><a href="<%=request.getContextPath() %>/wiki">Home</a></li>
	<%for (String s : pages) {
		s = s.trim();
		
		if (s.length() == 0) {
			continue;
		}
		path += "/" + s; %>
		<li>
			<a href="<%=request.getContextPath() %><%=path %>">
				<%=s %>
			</a>
			<%if ("source".equalsIgnoreCase((String) request.getAttribute("contentClass"))) {%>
				(source)
			<%} %>
		</li>
	<%} %>
	</ul>
</nav>
<%} %>