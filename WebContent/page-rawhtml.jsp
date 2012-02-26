<%
String titre = request.getParameter("p") == null ? "" : request.getParameter("p");
String contenu = (String) request.getAttribute("contenu");
contenu = contenu == null ? "" : contenu;
String[] pages = titre.split("/");
String current = pages[pages.length - 1];

String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="fr">
	<head>
		<meta charset="utf-8">
		<title><%=current.length() == 0 ? "Home" : current %> &nbsp;&laquo; wiki</title>
	</head>
	<body>
		<header class="page-header">
			<h1><%=current.length() == 0 ? "Home" : current %></h1>
		</header>
		
		<article class="wiki-content"><%=contenu %></article>
	</body>
</html>