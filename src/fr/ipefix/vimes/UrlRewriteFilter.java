package fr.ipefix.vimes;

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
@WebFilter(urlPatterns = {"/wiki/*", "/source/*", "/history/*"})
public class UrlRewriteFilter implements Filter {
	
	private static Pattern URL_PATTERN = Pattern.compile("^/(\\w+)/(.*)");

	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		Matcher m = URL_PATTERN.matcher(httpRequest.getServletPath());
		if (m.find()) {
			String prefix = m.group(1);
			String page = m.group(2);
			String url = "/" + prefix + "?p=" + (page.indexOf("?") > 0 ? page.substring(0, page.indexOf("?")) : page);
			
			if (httpRequest.getParameter("format") != null) {
				url += "&format=" + httpRequest.getParameter("format");
			}
			request.getRequestDispatcher(url).forward(request, response);
		} else {
			// pass the request along the filter chain
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
