package fr.ipefix;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Servlet implementation class PageServlet
 */
@WebServlet("/wiki")
public class PageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private MarkdownLoader loader;
	private String defaultFile;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String path = request.getParameter("p");
		if (path == null || "".equals(path = path.trim())) {
			path = this.defaultFile;
		}

		String content;
		try {
			content = this.loader.getHTML(path);
		} catch (IllegalArgumentException e) {
			this.displayPathError(e, request, response);
			return;
		} catch (IOException e) {
			this.displayPageNotFound(path, request, response);
			return;
		}
		this.render(content, request, response);
	}

	private void initProperties() throws IOException {
		Properties properties = new Properties();
		properties.load(getServletContext().getResourceAsStream(
				"/WEB-INF/vimes.properties"));

		this.defaultFile = properties.getProperty("defaultFile", "index");

		this.loader = new MarkdownLoader(properties.getProperty("basePath",
				"/var/lib/vimes"));
	}

	private void displayPageNotFound(String path, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.renderError("The page you requested was not found.",
				"Requested path was " + StringEscapeUtils.escapeHtml4(path),
				request, response);
	}

	private void displayPathError(IllegalArgumentException e,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.renderError("The path you requested is invalid.",
				StringEscapeUtils.escapeHtml4(e.getMessage()), request,
				response);
	}

	private void renderError(String error, String errorInfo,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/error-page.jsp");
		request.setAttribute("error", error);
		request.setAttribute("error-info", errorInfo);
		dispatcher.forward(request, response);
	}

	private void render(String content, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/page-template.jsp");
		request.setAttribute("contenu", content);
		dispatcher.forward(request, response);
	}

}
