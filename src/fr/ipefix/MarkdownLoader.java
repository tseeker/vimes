package fr.ipefix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.petebevin.markdown.MarkdownProcessor;

/**
 * Markdown loader
 * 
 * <p>
 * Load files from some location, and process their contents with MarkdownJ.
 */
class MarkdownLoader {

	/** Base path for all files */
	private final File basePath;

	/** MarkdownJ processor instance */
	private final MarkdownProcessor processor;

	/**
	 * Initialise the base path and processor
	 * 
	 * @param basePath
	 *            the base path to use
	 */
	MarkdownLoader(String basePath) {
		this.basePath = new File(basePath).getAbsoluteFile();
		this.processor = new MarkdownProcessor();
	}

	/**
	 * Get the HTML for some path
	 * 
	 * <p>
	 * Load the file matching the specified path inside the repository, then
	 * process it.
	 * 
	 * @param path
	 *            the path of the file inside the repository
	 * 
	 * @return the HTML generated from the file's contents
	 * 
	 * @throws IllegalArgumentException
	 *             if the path is invalid
	 * @throws IOException
	 *             if something goes awry while loading the source file
	 */
	public String getHTML(String path) throws IllegalArgumentException,
			IOException {
		File file = new File(this.basePath, path + ".markdown")
				.getAbsoluteFile();
		if (!file.getAbsolutePath().startsWith(this.basePath.getAbsolutePath())
				|| file.getAbsolutePath().startsWith(
						new File(this.basePath, ".git").getAbsolutePath())) {
			throw new IllegalArgumentException(path);
		}

		String markdown = this.getFileContents(file);
		return this.processor.markdown(markdown);
	}

	/**
	 * Load the file's contents
	 * 
	 * @param file
	 *            path to the file to load
	 * 
	 * @return the file's contents
	 * 
	 * @throws IOException
	 *             if something goes awry while loading the source file
	 */
	private String getFileContents(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		try {
			StringBuilder str = new StringBuilder();
			String input;
			while ((input = reader.readLine()) != null) {
				str.append(input);
			}
			return str.toString();
		} finally {
			reader.close();
		}
	}

}
