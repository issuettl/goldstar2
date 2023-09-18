package kr.co.lge.goldstar.core.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import lombok.extern.slf4j.Slf4j;

/**
 * @author issuettl
 * @since 2012. 11. 5.
 * @version 1.0
 *
 * <pre>
 * Name : DownloadFileView.java
 * Description  : 다운로드 파일 뷰
 * 
 * << 개정이력(Modification Information) >>
 *   
 *   수정일                         수정자                     수정내용
 * ==============================================
 *  2012. 11. 5.      issuettl         최초 생성
 *
 * </pre>
 */
@Slf4j
@Component("ImageView")
public class ImageView extends AbstractView {

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

        String defaultPath = (String) model.get("defaultPath");
        String filePath = (String) model.get("filePath");
        String contentType = (String) model.get("contentType");
        
        setContentType(contentType);
        
        log.debug("defaultPath + jpgPath : {}, {}", defaultPath, filePath);
		
		File file = new File(new StringBuilder(defaultPath).append(filePath).toString());
		if(!file.exists()) {
		    throw new FileNotFoundException(filePath);
		}

		InputStream in = new FileInputStream(file);
        response.setContentType(contentType);
        IOUtils.copy(in, response.getOutputStream());
	}
}
