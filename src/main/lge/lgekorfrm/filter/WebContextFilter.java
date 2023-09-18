package lgekorfrm.filter;

import devonframe.util.NullUtil;
import lgekorfrm.conf.model.Conf;
import lgekorfrm.conf.service.ConfService;
import lgekorfrm.context.WebContext;
import lgekorfrm.sso.util.JsonUtils;
import lgekorfrm.util.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

@Component("webContextFilter")
public class WebContextFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebContextFilter.class);
    private final ConfService confService;
    ServletContext servletContext;

    public WebContextFilter(ConfService confService) {
        this.confService = confService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        servletContext = filterConfig.getServletContext();
    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;

            // health check url(/health) --> 뒷부분 filter 로직 무시
            if ("/health".equals(request.getRequestURI())) {
                filterChain.doFilter(request, response);
                return;
            }

            try {
                if (!ServletUtils.isWebResource(request)) {
                    WebContext.setServletContext(request.getServletContext());
                    WebContext.setRequest(request);
                    WebContext.setResponse(response);

                    // Put Request Header
                    StringBuilder requestHeader = new StringBuilder(">[Request Header] ");
                    requestHeader.append(request.getMethod()).append(" ").append(request.getRequestURI()).append(" | ");
                    Enumeration<String> headerNames = request.getHeaderNames();
                    while (headerNames.hasMoreElements()) {
                        String name = headerNames.nextElement();
                        requestHeader.append(name);
                        requestHeader.append(" : ");
                        requestHeader.append(request.getHeader(name));
                        requestHeader.append(" | ");
                    }
                    LOGGER.warn(requestHeader.toString());

                    Locale locale = new Locale("ko", "KR");
                    WebContext.setLocale(locale);
                    WebContext.setCountry(locale.getCountry());
                    WebContext.setLanguage(locale.getLanguage());

                    // Conf
                    Map<String, Conf> confMap = confService.retrieveConfMap();
                    WebContext.setConfMap(confMap);
                    LOGGER.debug("=== WebContextFilter : URI - (" + request.getRequestURI() + ") || Map:Conf - " + JsonUtils.marshal(confMap));

                    String userAgent = request.getHeader("User-Agent");
                    if (!NullUtil.isNone(userAgent)) {
                        boolean ie = userAgent.contains("MSIE") || userAgent.contains("Trident");
                        WebContext.setIe(ie);
                    }

                    // domain address
                    /*
                    String domain = request.getRequestURL().toString().replace(request.getRequestURI(), "");
                    if (domain.indexOf("http://") > -1) domain = domain.replace("http://", "");
                    if (domain.indexOf("https://") > -1) domain = domain.replace("https://", "");
                    */

                    // api
                    AntPathMatcher matcher = new AntPathMatcher();
                    String apiPattern = "/*/*/api/**";
                    try {
                        boolean bApi = matcher.match(apiPattern, request.getRequestURI().toLowerCase());
                        WebContext.setApi(bApi);
                    } catch (Exception e) {
                        WebContext.setApi(false);
                    }
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }

            filterChain.doFilter(request, response);

            if (!ServletUtils.isWebResource(request)) {
                StringBuilder resHeader = new StringBuilder(">[Response Header] ");
                resHeader.append(request.getMethod()).append(" ").append(request.getRequestURI()).append(" | ");
                if(request.getHeader("x-amzn-trace-id") != null){
                    resHeader.append(" x-amzn-trace-id : ").append(request.getHeader("x-amzn-trace-id")).append(" | ");
                }
                Collection<String> resCookies = response.getHeaders("Set-Cookie");
                //Collection<String> headerNames = response.getHeaderNames();

                for (String cookie : resCookies) {
                    resHeader.append(cookie);
                    resHeader.append(" | ");
                }

                LOGGER.warn(resHeader.toString());
            }
        } finally {
            if (WebContext.getContext() != null) WebContext.clearAll();
        }
    }
}
