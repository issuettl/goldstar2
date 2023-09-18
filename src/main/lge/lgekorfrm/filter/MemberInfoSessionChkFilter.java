package lgekorfrm.filter;

import com.nimbusds.jwt.JWTClaimsSet;
import devonframe.configuration.ConfigService;
import lgekorfrm.sso.util.JsonUtils;
import lgekorfrm.sso.util.JwtUtils;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@Component("MemberInfoSessionChkFilter")
@PropertySources(@PropertySource("classpath:/config/project.properties"))

public class MemberInfoSessionChkFilter implements Filter {

    private Logger LOGGER = LoggerFactory.getLogger(MemberInfoSessionChkFilter.class);

    @Resource(name = "SsoMemberInfoSessionChkFilter")
    SsoMemberInfoSessionChkFilter ssoMemberInfoSessionChkFilter;

    @Resource(name = "EmpSsoMemberInfoSessionChkFilter")
    EmpSsoMemberInfoSessionChkFilter empSsoMemberInfoSessionChkFilter;

    @Resource(name = "configService")
    ConfigService configService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        String ssoLogin = configService.getString("ssoLogin");

        Filter ssoFilter = null;

        if(ssoLogin.contains("/emp/")) {
            ssoFilter = empSsoMemberInfoSessionChkFilter;
        }else{
            ssoFilter = ssoMemberInfoSessionChkFilter;
        }
        ssoFilter.doFilter(servletRequest, servletResponse, filterChain);

        /* EMP SSO 와 동시사용 - 임시
        boolean isJWTToken = false;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String accessToken = (String) request.getSession().getAttribute("ACCESS_TOKEN");
        try {
            JWTClaimsSet jwtClaimsSet = JwtUtils.parseToken(accessToken);
            JSONObject contexts = (JSONObject) jwtClaimsSet.getClaim("context");
            isJWTToken = true;
        } catch (Exception ex) {

        }

        if(isJWTToken) {
            ssoFilter = ssoMemberInfoSessionChkFilter;
        }else{
            ssoFilter = empSsoMemberInfoSessionChkFilter;
        }
        ssoFilter.doFilter(servletRequest, servletResponse, filterChain);
        */
    }

    @Override
    public void destroy() {

    }
}
