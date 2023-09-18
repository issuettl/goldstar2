/*******************************************************************************
 * FILE: /java/lgcommkt/commonmodule/model/CommonParamVO.java
 * DESC : COM_CONF_M VO
 *
 * PROJ : LG.com 5.0
 *******************************************************************************
 *                  Modification History
 *******************************************************************************
 * CSR No.      DATE              AUTHOR             DESCRIPTION
 *******************************************************************************
 *           2019-03-07        yeonghwan.choe       initial Release
 *******************************************************************************/
package lgekormkt.commonmodule.model;

import java.util.List;
import java.util.Locale;

public class CommonParamVO {
	
	private String localeCode;
	
	private String confCode;
	
	private List<String> codeList;
	
	private Locale javaLocaleCode;

	public String getLocaleCode() {
		return localeCode;
	}

	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}

	public String getConfCode() {
		return confCode;
	}

	public void setConfCode(String confCode) {
		this.confCode = confCode;
	}

	public List<String> getCodeList() {
		return codeList;
	}

	public void setCodeList(List<String> confCodeList) {
		this.codeList = confCodeList;
	}

	public Locale getJavaLocaleCode() {
		return javaLocaleCode;
	}

	public void setJavaLocaleCode(Locale locale) {
		this.javaLocaleCode = locale;
	}
	
}
