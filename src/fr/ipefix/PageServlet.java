package fr.ipefix;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

/**
 * Servlet implementation class PageServlet
 */
@WebServlet("/wiki")
public class PageServlet extends HttpServlet {
	private static final long	serialVersionUID	= 1L;

	private MarkdownLoader		loader;
	private String				defaultFile;
	private String				gitDirectory;
	private Repository			repository;

	@Override
	public void init() throws ServletException {
		try {
			Properties properties = new Properties();
			properties.load(getServletContext().getResourceAsStream(
					"/WEB-INF/vimes.properties"));

			this.defaultFile = properties.getProperty("defaultFile", "index");
			this.gitDirectory = properties.getProperty("basePath",
					"/var/lib/vimes");
			this.loader = new MarkdownLoader(gitDirectory);

			// Init git repo
			this.repository = new FileRepositoryBuilder()
							.setGitDir(new File(gitDirectory))
							.readEnvironment()
							.findGitDir()
							.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String path = request.getParameter("p");
		if (path == null || "".equals(path = path.trim())) {
			path = this.defaultFile;
		}

		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/page-template.jsp");
		request.setAttribute("contenu", this.loader.getHTML(path));
		request.setAttribute("repo", repository);
		dispatcher.forward(request, response);
	}

}
