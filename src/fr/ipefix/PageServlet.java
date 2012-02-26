package fr.ipefix;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

/**
 * Servlet implementation class PageServlet
 */
@WebServlet("/wiki")
public class PageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private RepositoryAccess repo;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		RepositoryAccess.initialise(this.getServletContext());
		this.repo = RepositoryAccess.get();
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String path = request.getParameter("p");
		if (path == null || "".equals(path = path.trim())) {
			path = this.repo.getDefaultFile();
		}

		String content;
		content = this.repo.getFile(path + ".markdown").getContent();
		if (content == null ) {
			this.displayPageNotFound(path, request, response);
		} else {
			this.render(this.repo.getLoader().markdown(content), request, response);
		}
	}

	private void displayPageNotFound(String path, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.renderError("The page you requested was not found.",
				"Requested path was " + StringEscapeUtils.escapeHtml4(path),
				request, response);
	}

	private void renderError(String error, String errorInfo,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = this.getServletContext()
				.getRequestDispatcher("/error-page.jsp");
		request.setAttribute("error", error);
		request.setAttribute("error-info", errorInfo);
		request.setAttribute("repo", this.repo.getRepository());
		dispatcher.forward(request, response);
	}

	private void render(String content, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/page-template.jsp");
		request.setAttribute("contenu", content);
		request.setAttribute("repo", this.repo.getRepository());
		dispatcher.forward(request, response);
	}

}
