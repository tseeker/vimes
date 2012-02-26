<%@page import="org.eclipse.jgit.treewalk.TreeWalk"%>
<%@page import="org.eclipse.jgit.revwalk.RevTree"%>
<%@page import="org.eclipse.jgit.lib.Constants"%>
<%@page import="org.eclipse.jgit.lib.ObjectId"%>
<%@page import="org.eclipse.jgit.revwalk.RevWalk"%>
<%@page import="org.eclipse.jgit.lib.Repository"%>
<%
Repository repo = (Repository) request.getAttribute("repo");
ObjectId head = repo.resolve(Constants.HEAD);
TreeWalk treeWalk = new TreeWalk(repo);
treeWalk.addTree(new RevWalk(repo).parseTree(head));
%>
<!DOCTYPE html>
<html lang="fr">
	<head>
		<meta charset="utf-8">
		<title><%=current %> &nbsp;&nbsp;&laquo; wiki</title>
		
		<link href="<%=cp %>/main.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div id="container">
			<aside id="sidebar">
				<nav id="main-nav">
					<ul>
						<li class="home"><a href="<%=cp %>">Accueil</a></li>
					<%while (treeWalk.next()) {
						String fileName = treeWalk.getNameString(); 
						if (fileName.endsWith(".markdown")) {
							String displayName = fileName.substring(0, fileName.length() - 9); %>
						<li><a href="<%=cp %>/wiki/<%=displayName %>"><%=displayName %></a></li>
					<%	}%> 
					<%} %>
					</ul>
				</nav>
			</aside>