<%
String titre = request.getParameter("p") == null ? "" : request.getParameter("p");
String contenu = (String) request.getAttribute("contenu");
contenu = contenu == null ? "" : contenu;

String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="fr">
	<head>
		<meta charset="utf-8">
		<title><%=titre.length() > 0 ? titre : "Untitled" %></title>
		
		<link href="<%=cp %>/main.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div id="container">
			<aside id="sidebar">
				<nav id="main-nav">
					<ul>
						<li><a href="<%=cp %>">Accueil</a></li>
					</ul>
				</nav>
			</aside>
		
			<article id="content">
				<header class="page-header">
					<nav class="page-nav">
						<ul>
							<li><a href="<%=cp %>/wiki/<%=titre %>" class="<%=request.getRequestURI().startsWith(cp + "/wiki") ? "select" : "" %>">Page</a></li>
							<li><a href="<%=cp %>/source/<%=titre %>" class="<%=request.getRequestURI().startsWith(cp + "/source") ? "select" : "" %>">Source</a></li>
							<li><a href="<%=cp %>/history/<%=titre %>" class="<%=request.getRequestURI().startsWith(cp + "/history") ? "select" : "" %>">History</a></li>
						</ul>
					</nav>
					<h1><%=titre.split("/")[titre.split("/").length-1] %></h1>
					<%@include file="fragments/breadcrumb.jsp" %>
				</header>
				<%=contenu %>
			</article>
		</div>
		
		<footer id="footer">
			<p>Powered by <a href="https://github.com/manudwarf/vimes" target="_blank">Vimes</a>.</p>
		</footer>
	</body>
</html>