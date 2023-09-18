package lgekorfrm.filter;

import devonframe.configuration.ConfigService;
import devonframe.util.NullUtil;
import lgekorfrm.context.WebContext;
import lgekorfrm.redirect.PolicyService;
import lgekorfrm.util.HtmlUtils;
import lgekorfrm.util.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Component("redirectFilter")
public class RedirectFilter implements Filter {
	private static final Logger LOGGER = LoggerFactory.getLogger(RedirectFilter.class);
	private final PolicyService policyService;
	private final ConfigService configService;

	public RedirectFilter(PolicyService policyService, ConfigService configService) {
		this.policyService = policyService;
		this.configService = configService;
	}

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

		if (WebContext.isApi()) {
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}

		if (ServletUtils.isWebResource(request)) {
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}

		String requestUri = request.getRequestURI();
		String requestQueryString = request.getQueryString();
		String originUri;
		String decodeParamUri;
		String decodeUri;
		String project = configService.getString("project");

		if (!NullUtil.isNone(project)) {
			if ("LGEKOR_MKT".equals(project) || "LGEKOR_EVT".equals(project)) {
				if (requestUri.startsWith("/kr")) {
					requestUri = requestUri.replaceFirst("/kr", "");
				}
			}
		}

		if (!NullUtil.isNone(requestQueryString)) {
			originUri = String.format("%s?%s", requestUri, requestQueryString);
			decodeParamUri = String.format("%s?%s", requestUri, URLDecoder.decode(requestQueryString, "UTF-8"));
			decodeUri = String.format("%s?%s", URLDecoder.decode(requestUri, "UTF-8"),
					URLDecoder.decode(requestQueryString, "UTF-8"));
		} else {
			originUri = requestUri;
			decodeParamUri = requestUri;
			decodeUri = URLDecoder.decode(requestUri, "UTF-8");
		}

		// If target exists for origin uri
		Map<String, String> redirectMap = policyService.findRedirectPath(originUri);
		String sourceUri = "";
		String targetUri = "";
		String startDateTime = "";
		Date now = new Date();
		SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentDateTime = sdt.format(now);

		if (!NullUtil.isNone(redirectMap)) {
			sourceUri = redirectMap.get("source");
			targetUri = redirectMap.get("target");
			startDateTime = redirectMap.get("startDateTime");

			if (!NullUtil.isNone(startDateTime)) {
				if (currentDateTime.compareTo(startDateTime) >= 0) {
					LOGGER.debug("[ startDatetime : \"{}\", currentDateTime: \"{}\" ]", startDateTime, currentDateTime);
					if (!NullUtil.isNone(targetUri)) {
						targetUri = HtmlUtils.unescapeHtml(targetUri);

						if (sourceUri.endsWith("*")) {
							if (targetUri.endsWith("*")) {
								sourceUri = sourceUri.replace("*", "");
								StringBuilder newTargetUri = new StringBuilder(targetUri.replace("*", ""));
								newTargetUri.append(originUri.substring(originUri.indexOf(sourceUri) + sourceUri.length()));
								response.setStatus(HttpStatus.MOVED_PERMANENTLY.value());
								response.setHeader("Location", newTargetUri.toString());
								LOGGER.debug("RedirectFilter [ sourceUri: \"{}\", targetUri: \"{}\" ]", sourceUri, targetUri);
								return;
							} else {
								response.setStatus(HttpStatus.MOVED_PERMANENTLY.value());
								response.setHeader("Location", targetUri);
								LOGGER.debug("RedirectFilter [ sourceUri: \"{}\", targetUri: \"{}\" ]", sourceUri, targetUri);
								return;
							}
						} else {
							response.setStatus(HttpStatus.MOVED_PERMANENTLY.value());
							response.setHeader("Location", targetUri);
							LOGGER.debug("RedirectFilter [ sourceUri: \"{}\", targetUri: \"{}\" ]", sourceUri, targetUri);
							return;
						}
					}
				}
			}
		}

		// If target exists for decoded parameter
		redirectMap = policyService.findRedirectPath(decodeParamUri);
		if (!NullUtil.isNone(redirectMap)) {
			sourceUri = redirectMap.get("source");
			targetUri = redirectMap.get("target");
			startDateTime = redirectMap.get("startDateTime");

			if (!NullUtil.isNone(startDateTime)) {
				if (currentDateTime.compareTo(startDateTime) >= 0) {
					if (!NullUtil.isNone(targetUri)) {
						targetUri = HtmlUtils.unescapeHtml(targetUri);

						if (sourceUri.endsWith("*")) {
							if (targetUri.endsWith("*")) {
								sourceUri = sourceUri.replace("*", "");
								StringBuilder newTargetUri = new StringBuilder(targetUri.replace("*", ""));
								newTargetUri.append(originUri.substring(originUri.indexOf(sourceUri) + sourceUri.length()));
								response.setStatus(HttpStatus.MOVED_PERMANENTLY.value());
								response.setHeader("Location", newTargetUri.toString());
								LOGGER.debug("RedirectFilter [ sourceUri: \"{}\", targetUri: \"{}\" ]", sourceUri, targetUri);
								return;
							} else {
								response.setStatus(HttpStatus.MOVED_PERMANENTLY.value());
								response.setHeader("Location", targetUri);
								LOGGER.debug("RedirectFilter [ sourceUri: \"{}\", targetUri: \"{}\" ]", sourceUri, targetUri);
								return;
							}
						} else {
							response.setStatus(HttpStatus.MOVED_PERMANENTLY.value());
							response.setHeader("Location", targetUri);
							LOGGER.debug("RedirectFilter [ sourceUri: \"{}\", targetUri: \"{}\" ]", sourceUri, targetUri);
							return;
						}
					}
				}
			}
		}

		// If target exists for decoded uri
		redirectMap = policyService.findRedirectPath(decodeUri);
		if (!NullUtil.isNone(redirectMap)) {
			sourceUri = redirectMap.get("source");
			targetUri = redirectMap.get("target");
			startDateTime = redirectMap.get("startDateTime");

			if (!NullUtil.isNone(startDateTime)) {
				if (currentDateTime.compareTo(startDateTime) >= 0) {

					if (!NullUtil.isNone(targetUri)) {
						targetUri = HtmlUtils.unescapeHtml(targetUri);

						if (sourceUri.endsWith("*")) {
							if (targetUri.endsWith("*")) {
								sourceUri = sourceUri.replace("*", "");
								StringBuilder newTargetUri = new StringBuilder(targetUri.replace("*", ""));
								newTargetUri.append(originUri.substring(originUri.indexOf(sourceUri) + sourceUri.length()));
								response.setStatus(HttpStatus.MOVED_PERMANENTLY.value());
								response.setHeader("Location", newTargetUri.toString());
								LOGGER.debug("RedirectFilter [ sourceUri: \"{}\", targetUri: \"{}\" ]", sourceUri, targetUri);
								return;
							} else {
								response.setStatus(HttpStatus.MOVED_PERMANENTLY.value());
								response.setHeader("Location", targetUri);
								LOGGER.debug("RedirectFilter [ sourceUri: \"{}\", targetUri: \"{}\" ]", sourceUri, targetUri);
								return;
							}
						} else {
							response.setStatus(HttpStatus.MOVED_PERMANENTLY.value());
							response.setHeader("Location", targetUri);
							LOGGER.debug("RedirectFilter [ sourceUri: \"{}\", targetUri: \"{}\" ]", sourceUri, targetUri);
							return;
						}
					}
				}
			}
		}

		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void destroy() {

	}
}
