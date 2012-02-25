package fr.ipefix;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PageServlet
 */
@WebServlet("/wiki")
public class PageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private MarkdownLoader loader;
	private String defaultFile;

	private void initProperties() throws IOException {
		Properties properties = new Properties();
		properties.load(getServletContext().getResourceAsStream(
				"/WEB-INF/vimes.properties"));

		this.defaultFile = properties.getProperty("defaultFile", "index");

		this.loader = new MarkdownLoader(properties.getProperty("basePath",
				"/var/lib/vimes"));
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		if (this.loader == null) {
			this.initProperties();
		}

		String path = request.getParameter("p");
		if (path == null || "".equals(path = path.trim())) {
			path = this.defaultFile;
		}

		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/page-template.jsp");
		request.setAttribute("contenu", this.loader.getHTML(path));
		dispatcher.forward(request, response);
	}

}
