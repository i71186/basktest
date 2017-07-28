package com.iso.claimsearch.casemanager.config;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

/**
 * Restrict resources on web page to be requested from another domain outside the domain from which
 * the resource originated. Also enable Products caching.
 * 
 * @author i90845
 * 
 */
@Component
public class CORSFilter implements Filter {

  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    HttpServletResponse response = (HttpServletResponse) res;
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
    response.setHeader("Access-Control-Max-Age", "1800");
    response.setHeader("Access-Control-Allow-Headers",
        "Authorization, Origin, X-Requested-With, Content-Type, Accept");
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0

    chain.doFilter(req, res);
  }

  public void init(FilterConfig filterConfig) {}

  public void destroy() {}
}
