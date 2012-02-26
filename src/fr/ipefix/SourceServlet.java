package fr.ipefix;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;

@WebServlet("/source")
public class SourceServlet extends VimesServletBase {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getParameter("p");
		if (path == null || "".equals(path = path.trim())) {
			path = this.repo.getDefaultFile();
		}

		String content;
		content = this.repo.getFile(path + ".markdown").getContent();
		if (content == null ) {
			this.displayPageNotFound(path, request, response);
		} else {
			String raw = StringEscapeUtils.escapeHtml4(content).replace("\n", "<br/>");
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher("/page-template.jsp");
			request.setAttribute("contenu", raw);
			request.setAttribute("repo", this.repo.getRepository());
			dispatcher.forward(request, response);
		}
	}

}
