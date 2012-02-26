<%@page import="org.eclipse.jgit.api.Git"%>
<%@page import="org.eclipse.jgit.revwalk.RevCommit"%>
<%@page import="org.eclipse.jgit.lib.ObjectLoader"%>
<%@page import="org.eclipse.jgit.treewalk.TreeWalk"%>
<%@page import="org.eclipse.jgit.revwalk.RevTree"%>
<%@page import="org.eclipse.jgit.lib.Constants"%>
<%@page import="org.eclipse.jgit.lib.ObjectId"%>
<%@page import="org.eclipse.jgit.revwalk.RevWalk"%>
<%@page import="org.eclipse.jgit.lib.Repository"%>
<%
Repository repo = (Repository) request.getAttribute("repo");
Git git = new Git(repo);
RevCommit headCommit = git.log().call().iterator().next();
ObjectId head = repo.resolve(Constants.HEAD);
TreeWalk treeWalk = new TreeWalk(repo);
treeWalk.addTree(new RevWalk(repo).parseTree(head));
%>
<!DOCTYPE html>
<html lang="fr">
	<head>
		<meta charset="utf-8">
		<title><%=current %> &nbsp;&laquo; wiki</title>
		
		<link href="<%=cp %>/main.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div id="container">
			<aside id="sidebar">
				<nav id="main-nav">
					<ul>
						<li class="home <%=current.length() == 0 ? "current" : "" %>"><a href="<%=cp %>/wiki">Home</a></li>
						<li class="pages">Pages
							<ul>
							<%while (treeWalk.next()) {
								ObjectLoader loader = repo.open(treeWalk.getObjectId(0));
								String fileName = treeWalk.getNameString();
								if (fileName.endsWith(".markdown")) {
									String displayName = fileName.substring(0, fileName.length() - 9);
									if ("index".equalsIgnoreCase(displayName)) continue; %>
								<li class="page <%=displayName.equals(current) ? "current" : "" %>"><a href="<%=cp %>/wiki/<%=displayName %>"><%=displayName %></a></li>
							<%	} %> 
							<%} %>
							</ul>
						</li>
					</ul>
				</nav>
				
				<div class="last-commit">
					Last commit <%=headCommit.getName().substring(0, 7) %> by <%=headCommit.getAuthorIdent().getName() %>
				</div>
			</aside>