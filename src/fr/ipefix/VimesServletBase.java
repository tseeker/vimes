package fr.ipefix;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;

abstract class VimesServletBase extends HttpServlet {

	private static final long serialVersionUID = 1L;
	protected RepositoryAccess repo;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		RepositoryAccess.initialise(this.getServletContext());
		this.repo = RepositoryAccess.get();
	}

	protected void displayPageNotFound(String path, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.renderError("The page you requested was not found.",
				"Requested path was " + StringEscapeUtils.escapeHtml4(path),
				request, response);
	}

	protected void renderError(String error, String errorInfo,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = this.getServletContext()
				.getRequestDispatcher("/error-page.jsp");
		request.setAttribute("error", error);
		request.setAttribute("error-info", errorInfo);
		request.setAttribute("repo", this.repo.getRepository());
		dispatcher.forward(request, response);
	}

}
