package lgekorfrm.filter;

import lgekorfrm.util.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;

@Component("admLogFilter")
public class AdmLogFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdmLogFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if ("/health".equals(request.getRequestURI())) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        boolean bWebResource = ServletUtils.isWebResource(request);
        if (!bWebResource) {
            StringBuilder requestHeader = new StringBuilder(">[Request Header] ");
            Enumeration<String> headerNames = request.getHeaderNames();

            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                requestHeader.append(name);
                requestHeader.append(" : ");
                requestHeader.append(request.getHeader(name));
                requestHeader.append(" | ");
            }
            LOGGER.info(requestHeader.toString());
        }

        filterChain.doFilter(servletRequest, servletResponse);

        if (!bWebResource) {
            StringBuilder responseHeader = new StringBuilder(">[Response Header] ");
            Collection<String> headerNames = response.getHeaderNames();

            for (String name : headerNames) {
                responseHeader.append(name);
                responseHeader.append(" : ");
                responseHeader.append(response.getHeader(name));
                responseHeader.append(" | ");
            }
            LOGGER.info(responseHeader.toString());
        }
    }

    @Override
    public void destroy() {

    }
}
