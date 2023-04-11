package com.example.domain.myapp.general.common.impl.security;

import java.util.regex.Pattern;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * This is the implementation of {@link RequestMatcher}, which decides which {@link HttpServletRequest Requests} require
 * a correct CSRF token.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Cross-site_request_forgery">Cross-site request forgery</a>
 */
@Named("CsrfRequestMatcher")
public class CsrfRequestMatcher implements RequestMatcher {

  private static final Pattern HTTP_METHOD_PATTERN = Pattern.compile("^GET$");

  private static final String[] PATH_PREFIXES_WITHOUT_CSRF_PROTECTION =
      { "/login", "/logout", "/services/rest/login", "/websocket" };

  @Override
  public boolean matches(HttpServletRequest request) {

    // GET requests are read-only and therefore do not need CSRF protection
    if (HTTP_METHOD_PATTERN.matcher(request.getMethod()).matches()) {

      return false;
    }

    // There are specific URLs which can not be protected from CSRF. For example, in case of the the login page,
    // the CSRF token can only be accessed after a successful authentication ("login").
    String requestPath = getRequestPath(request);
    for (String path : PATH_PREFIXES_WITHOUT_CSRF_PROTECTION) {
      if (requestPath.startsWith(path)) {
        return false;
      }
    }

    return true;
  }

  private String getRequestPath(HttpServletRequest request) {

    String url = request.getServletPath();
    String pathInfo = request.getPathInfo();

    if (pathInfo != null) {
      url += pathInfo;
    }

    return url;
  }
}