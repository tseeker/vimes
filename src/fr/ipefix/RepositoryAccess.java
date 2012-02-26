package fr.ipefix;

import java.io.File;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

final class RepositoryAccess {

	private static RepositoryAccess singleton;

	private String defaultFile;
	private String gitDirectory;
	private FileRepository repository;
	private MarkdownLoader loader;

	private RepositoryAccess(ServletContext context) throws ServletException {
		try {
			Properties properties = new Properties();
			properties.load(context
					.getResourceAsStream("/WEB-INF/vimes.properties"));

			this.defaultFile = properties.getProperty("defaultFile", "index");
			this.gitDirectory = properties.getProperty("basePath",
					"/var/lib/vimes");
			this.loader = new MarkdownLoader(this.gitDirectory);

			// Init git repo
			this.repository = new FileRepositoryBuilder()
					.setGitDir(new File(this.gitDirectory + "/.git"))
					.readEnvironment().findGitDir().build();
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	public static void initialise(ServletContext context)
			throws ServletException {
		if (RepositoryAccess.singleton == null) {
			RepositoryAccess.singleton = new RepositoryAccess(context);
		}
	}

	public static RepositoryAccess get() {
		return RepositoryAccess.singleton;
	}

	public MarkdownLoader getLoader() {
		return this.loader;
	}

	public Repository getRepository() {
		return this.repository;
	}

	public String getDefaultFile() {
		return this.defaultFile;
	}

}
