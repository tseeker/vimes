package fr.ipefix;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class UrlRewriteFilter
 */
@WebFilter("/wiki/*")
public class UrlRewriteFilter implements Filter {
	
	private static Pattern URL_PATTERN = Pattern.compile("wiki/(.*)");

	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		Matcher m = URL_PATTERN.matcher(((HttpServletRequest) request).getRequestURI());
		if (m.find()) {
			String page = m.group(1);
			String url = "/wiki?p=" + page;
			request.getRequestDispatcher(url).forward(request, response);
		} else {
			// pass the request along the filter chain
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
