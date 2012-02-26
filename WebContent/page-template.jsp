<%
String titre = request.getParameter("p") == null ? "" : request.getParameter("p");
String contenu = (String) request.getAttribute("contenu");
contenu = contenu == null ? "" : contenu;
String[] pages = titre.split("/");
String current = pages[pages.length - 1];

String cp = request.getContextPath();
%>

<%@include file="fragments/header.jsp" %>

<article id="content">
	<header class="page-header">
		<nav class="page-nav">
			<ul>
				<li><a href="<%=cp %>/wiki/<%=titre %>" class="<%=request.getRequestURI().startsWith(cp + "/wiki") ? "select" : "" %>">Page</a></li>
				<li><a href="<%=cp %>/source/<%=titre %>" class="<%=request.getRequestURI().startsWith(cp + "/source") ? "select" : "" %>">Source</a></li>
				<li><a href="<%=cp %>/history/<%=titre %>" class="<%=request.getRequestURI().startsWith(cp + "/history") ? "select" : "" %>">History</a></li>
			</ul>
		</nav>
		<h1><%=current %></h1>
		<%@include file="fragments/breadcrumb.jsp" %>
	</header>
	<%=contenu %>
</article>

<%@include file="fragments/footer.jsp" %>