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
		<h1><%=current.length() == 0 ? "Home" : current %></h1>
		<%@include file="fragments/breadcrumb.jsp" %>
	</header>
	
	<div class="wiki-content"><%=contenu %></div>
</article>

<footer id="toolbox">
	<aside id="sidebar-toolbox" class="toolbox">
		Last commit <%=headCommit.getName().substring(0, 7) %>
	</aside>
	<article id="content-toolbox" class="toolbox">
		<nav>
			<ul>
				<li><a href="<%=cp %>/wiki<%=path %>?format=html">Download page</a></li>
			</ul>
		</nav>
	</article>
</footer>

<%@include file="fragments/footer.jsp" %>