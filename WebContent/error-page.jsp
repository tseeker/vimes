<%
String error = (String) request.getAttribute("error");
String errorInfo = ( String ) request.getAttribute("error-info");
String titre = "Error";
String[] pages = titre.split("/");
String current = pages[pages.length - 1];

String cp = request.getContextPath();
%>

<%@include file="fragments/header.jsp" %>

<article id="content">
	<header class="page-header">
		<h1><%=titre%></h1>
		<%@include file="fragments/breadcrumb.jsp" %>
	</header>
	<p>Argh! Something went wrong!</p>
	<p><%= error %></p>
	<%if ( errorInfo != null) { %>
		<p><em><%= errorInfo %>.</em></p>
	<%} %>
</article>

<%@include file="fragments/footer.jsp" %>