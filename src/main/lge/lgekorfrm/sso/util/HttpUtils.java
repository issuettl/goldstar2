package lgekorfrm.sso.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HttpUtils {

    protected static String DEFAULT_ENCODING_FOR_URL = "UTF-8";
    private Log logger = LogFactory.getLog(this.getClass());

    private RequestConfig requestConfig;

    public static String replaceLineSeparator(String value) {
        return value.replaceAll("(\r\n|\r|\n|\n\r)", "");
    }

    public void setRequestConfig(RequestConfig requestConfig){
        this.requestConfig = requestConfig;
    }
    public HttpResponse makeFileRequest(String type, String uri, File file, Map<String, String> headers) throws Exception {
        HttpRequestBase request = null;

        switch (type) {
            case "GET":
                request = new HttpGet(uri);
                break;
            case "POST":
                request = new HttpPost(uri);
                break;
            case "PUT":
                request = new HttpPut(uri);
                break;
            case "DELETE":
                request = new HttpDelete(uri);
                break;
            default:
                throw new RuntimeException("Invalid HTTP request type: " + type);
        }

        if (headers != null) {
            for (Entry<String, String> header : headers.entrySet()) {
                request.setHeader(replaceLineSeparator(header.getKey()), replaceLineSeparator(header.getValue()));
            }
        }

        if (file != null) {
            try {
                if (request instanceof HttpPut)
                    ((HttpPut) request).setEntity(new FileEntity(file, "binary/octet-stream"));
                if (request instanceof HttpPost) {
                    ((HttpPost) request).setEntity(new FileEntity(file, "binary/octet-stream"));
                }
            } catch (Exception e1) {
                logger.error("Failed to add binary " + uri, e1);
            }
        }

        HttpClient client = new DefaultHttpClient();
        logger.info("Making " + request.getMethod() + " request to: " + uri);
        HttpResponse httpResponse = client.execute(request);

        return httpResponse;
    }

    public HttpResponse makeRequest(String type, String uri, String data, Map<String, String> headers) throws Exception {
        HttpRequestBase request = null;

        switch (type) {
            case "GET":
                request = new HttpGet(uri);
                break;
            case "POST":
                request = new HttpPost(uri);
                break;
            case "PUT":
                request = new HttpPut(uri);
                break;
            case "DELETE":
                request = new HttpDelete(uri);
                break;
            default:
                throw new RuntimeException("Invalid HTTP request type: " + type);
        }

        if(this.requestConfig != null) {
            request.setConfig(this.requestConfig);
        }

        if (headers != null) {
            for (Entry<String, String> header : headers.entrySet()) {
                request.setHeader(replaceLineSeparator(header.getKey()), replaceLineSeparator(header.getValue()));
            }
        }

        if (data != null) {
            try {
                ((HttpEntityEnclosingRequestBase) request).setEntity(new StringEntity(data, "UTF-8"));
            } catch (Exception e1) {
                logger.error("Failed to add data " + uri, e1);
            }
        }

        logger.info("Making " + request.getMethod() + " request to: " + uri);


        if (uri.startsWith("https")) {
            SSLContext sslContext = new SSLContextBuilder()
                    .loadTrustMaterial(null, (certificate, authType) -> true).build();

            CloseableHttpClient client = HttpClients.custom()
                    .setSSLContext(sslContext)
                    .setSSLHostnameVerifier(new NoopHostnameVerifier())
                    .build();
            HttpResponse httpResponse = client.execute(request);
            return httpResponse;
        } else {
            HttpClient client = new DefaultHttpClient();
            HttpResponse httpResponse = client.execute(request);
            return httpResponse;
        }
    }

    public static String createGETQueryString(Map params) {
        StringBuilder sb = new StringBuilder("");

        List<String> listOfParams = new ArrayList<String>();
        for (Object param : params.keySet()) {
            listOfParams.add(param + "=" + encodeString(params.get(param) + ""));
        }

        if (!listOfParams.isEmpty()) {
            String query = String.join("&", listOfParams);
            sb.append("?");
            sb.append(query);
        }

        return sb.toString();
    }

    public static String createPOSTQueryString(Map params) {
        StringBuilder sb = new StringBuilder("");

        List<String> listOfParams = new ArrayList<String>();
        for (Object param : params.keySet()) {
            listOfParams.add(param + "=" + encodeString(params.get(param) + ""));
        }

        if (!listOfParams.isEmpty()) {
            String query = String.join("&", listOfParams);
            sb.append(query);
        }

        return sb.toString();
    }

    public static String encodeString(String name) throws NullPointerException {
        String tmp = null;

        if (name == null)
            return null;

        try {
            tmp = URLEncoder.encode(name, DEFAULT_ENCODING_FOR_URL);
        } catch (Exception e) {
        }

        if (tmp == null)
            throw new NullPointerException();

        return tmp;
    }

    public static String decodeString(String name) throws NullPointerException {
        String tmp = null;

        if (name == null)
            return null;

        try {
            tmp = URLDecoder.decode(name, DEFAULT_ENCODING_FOR_URL);
        } catch (Exception e) {
        }

        if (tmp == null)
            throw new NullPointerException();

        return tmp;
    }
}
