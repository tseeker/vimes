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
		<aside id="sidebar">
			<nav id="main-nav">
				<ul>
					<li><a href="<%=cp %>">Accueil</a></li>
				</ul>
			</nav>
		</aside>
	
		<article id="content">
			<header id="header">
				<nav id="page-nav">
					<ul>
						<li><a href="<%=cp %>/wiki/<%=titre %>">Page</a></li>
						<li><a href="<%=cp %>/source/<%=titre %>">Source</a></li>
						<li><a href="<%=cp %>/history/<%=titre %>">History</a></li>
					</ul>
				</nav>
			</header>
			<%=contenu %>
		</article>
		
		<footer id="footer">
			<p>Powered by <a href="https://github.com/manudwarf/vimes" target="_blank">Vimes</a>.</p>
		</footer>
	</body>
</html>