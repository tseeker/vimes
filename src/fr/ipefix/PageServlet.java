package fr.ipefix;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PageServlet
 */
@WebServlet("/wiki")
public class PageServlet extends VimesServletBase {
	private static final long serialVersionUID = 1L;

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

	private void render(String content, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String jsp = "/page-template.jsp";
		if ("raw".equalsIgnoreCase(request.getParameter("format"))) {
			jsp = "/page-rawhtml.jsp";
		}
		
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(jsp);
		request.setAttribute("contenu", content);
		request.setAttribute("contentClass", "page");
		request.setAttribute("repo", this.repo.getRepository());
		dispatcher.forward(request, response);
	}

}
