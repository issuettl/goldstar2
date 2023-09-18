package lgekorfrm.filter;

import devonframe.util.NullUtil;
import lgekorfrm.context.WebContext;
import lgekorfrm.blockurl.service.BlockUrlService;
import lgekorfrm.util.ServletUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("blockUrlFilter")
public class BlockUrlFilter implements Filter {

    private final BlockUrlService blockUrlService;

    public BlockUrlFilter(BlockUrlService blockUrlService) {
        this.blockUrlService = blockUrlService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if("/health".equals(request.getRequestURI())) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if(WebContext.isApi()) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if(ServletUtils.isWebResource(request)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String requestUri = request.getRequestURI();

        Map<String, Object> input = new HashMap<>();
        input.put("requestUri", requestUri);

        Map<String, String> blockUrlList = blockUrlService.retrieveBlockUrlList();

        if(NullUtil.isNone(blockUrlList) || !blockUrlList.containsKey(requestUri)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        List<Map<String, Object>> list = blockUrlService.retrieveBlockUrlTimeInfo(input);

        if(NullUtil.isNone(list)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String downtimeCheckUrl = "/support/error-pages/down-time-check";
        OffsetDateTime now = OffsetDateTime.now(ZoneId.of("UTC+09:00"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").withZone(ZoneId.of("UTC+09:00"));

        for(Map<String, Object> data : list) {
            OffsetDateTime downTime = LocalDateTime.parse(data.get("downTime").toString(), formatter).atOffset(ZoneOffset.of("+09:00"));
            OffsetDateTime openTime = LocalDateTime.parse(data.get("openTime").toString(), formatter).atOffset(ZoneOffset.of("+09:00"));

            if(now.isAfter(downTime) && now.isBefore(openTime)) {
                request.getSession().setAttribute("blockId", data.get("akeyId"));
                response.setStatus(HttpStatus.TEMPORARY_REDIRECT.value());
                response.setHeader("Location", downtimeCheckUrl);

                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
