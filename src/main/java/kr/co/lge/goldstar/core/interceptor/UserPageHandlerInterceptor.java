/**
 * 
 */
package kr.co.lge.goldstar.core.interceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import kr.co.lge.goldstar.mvc.sso.service.SsoService;
import kr.co.lge.goldstar.mvc.u.sign.service.SignService;
import kr.co.rebel9.core.utils.DateUtils;
import kr.co.rebel9.web.utils.RequestUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author issuettl, data@rebel9.co.kr
 * @since 1.0
 * @date 2018. 4. 10.
 *
 */
@Slf4j
public class UserPageHandlerInterceptor implements HandlerInterceptor, InitializingBean {

	@Autowired
	private SignService signService;
	
	@Autowired
	private SsoService ssoService;
	
    private String versionHtml;
    
	@Value("${lge.url.gnb}")
	private String gnbUrl;
    
	@Value("${lge.url.footer}")
	private String footerUrl;
    
	@Value("${lge.url.domain}")
	private String lgeDomain;
    
	@Value("${lge.sso.logout}")
	private String lgeLogout;
    
	@Value("${lge.sso.logingo}")
	private String lgeLogin;
	
	@Value("${system.domain}")
	private String systemDomain;
	
	@Value("${spring.config.activate.on-profile}")
	private String systemProfile;
    
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        
		if(!"local".equals(systemProfile) && !"cafe".equals(systemProfile)) {
	    	//SSO체크
	    	this.ssoService.setAccountInfo(request, response);
		}
        return true;
    }

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		if("XMLHttpRequest".equals(request.getHeader("x-requested-with"))) {
			return;
		}
		
		if(ObjectUtils.isEmpty(modelAndView)) {
			return;
		}
		
		if(isRedirect(modelAndView)) {
			return;
		}

		if(request.getServerName().startsWith("localhost")) {
    		this.versionHtml = DateUtils.getToday("yyyyMMddHHmmssSSS");
    	}
		
        modelAndView.addObject("profile", systemProfile);
        modelAndView.addObject("versionHtml", this.versionHtml);
        modelAndView.addObject("currentURI", RequestUtils.getRequestURIExcludeContextPath());
        modelAndView.addObject("sign", this.signService.getSignIn());
        modelAndView.addObject("systemDomain", this.systemDomain);
        
        String gnb = lgeGnbFooter(this.gnbUrl);
        gnb = lgeExtra(gnb, dataLoginInfo, "/sso/loginInfo.do");
        gnb = lgeExtra(gnb, dataCartUrl, "/sso/minicart.do");

        gnb = StringUtils.replace(gnb, lgeLogout, request.getContextPath() + "/sso/account/out.do");
        gnb = StringUtils.replace(gnb, lgeLogin, "\"" + request.getContextPath() + "/sso/account/in.do" + "\"");
        
        modelAndView.addObject("lgeGnb", gnb);
        modelAndView.addObject("lgeFooter", lgeGnbFooter(this.footerUrl));
	}
	
	/**
	 * @param lgeGnbFooter
	 * @return
	 */
	private static final String dataLoginInfo = "data-login-info=\"";
	private static final String dataCartUrl = "data-cart-url=\"";
	
	private String lgeExtra(String target, String original, String replace) {

		int index = target.indexOf(original);
		
		String first = target.substring(0, index + original.length());
		String second = target.substring(index + original.length());
		index = second.indexOf("\"");
		second = second.substring(index);
		
		StringBuilder result = new StringBuilder(first).append(RequestUtils.getRequest().getContextPath()).append(replace).append(second);
		return result.toString();
	}

	private boolean isRedirect(ModelAndView modelAndView) {
		return modelAndView.getView() instanceof RedirectView || modelAndView.getViewName().startsWith("redirect:");
	}
	
	X509TrustManager easyTrustManager = new X509TrustManager() {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
    };
    
 // ssl security Exception 방지
 	public void disableSslVerification(){
 		try
 	    {
 	        // Create a trust manager that does not validate certificate chains
 	        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
 	            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
 	                return null;
 	            }
 	            public void checkClientTrusted(X509Certificate[] certs, String authType){
 	            }
 	            public void checkServerTrusted(X509Certificate[] certs, String authType){
 	            }
 	        }
 	        };
 	
 	        // Install the all-trusting trust manager
 	        SSLContext sc = SSLContext.getInstance("SSL");
 	        sc.init(null, trustAllCerts, new java.security.SecureRandom());
 	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
 	
 	        // Create all-trusting host name verifier
 	        HostnameVerifier allHostsValid = new HostnameVerifier() {
 	            public boolean verify(String hostname, SSLSession session){
 	                return true;
 	            }
 	        };
 	
 	        // Install the all-trusting host verifier
 	        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
 	    } catch (NoSuchAlgorithmException e) {
 	        e.printStackTrace();
 	    } catch (KeyManagementException e) {
 	        e.printStackTrace();
 	    }
 	}

    /**
	 * @return
	 */
	private String lgeGnbFooter(String url) {
		
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpClient client = HttpClients.createDefault();
		try {
			
			disableSslVerification();
			
			CloseableHttpResponse httpResponse = client.execute(httpGet);
			
			InputStreamReader responseReader = new InputStreamReader(
					httpResponse.getEntity().getContent());
			
			Stream<String> streamOfString = new BufferedReader(responseReader).lines();
            String html = streamOfString.collect(Collectors.joining());

            html = StringUtils.replace(html, "=\"/", "=\"" + lgeDomain);
			
			return html;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        
    }

	@Override
	public void afterPropertiesSet() throws Exception {
		this.versionHtml = DateUtils.getToday("yyyyMMddHHmmssSSS");
	}
}
