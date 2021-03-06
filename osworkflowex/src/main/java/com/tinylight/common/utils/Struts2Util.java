package com.tinylight.common.utils;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Struts2 Util绫�.
 * 
 * 瀹炵幇鑾峰彇Request/Response/Session涓庣粫杩噅sp/freemaker鐩存帴杈撳嚭鏂囨湰鐨勭畝鍖栧嚱鏁�.
 * 
 */
public class Struts2Util {

	//header 甯搁噺瀹氫箟
	private static final String ENCODING_PREFIX = "encoding:";
	private static final String NOCACHE_PREFIX = "no-cache:";
	private static final String ENCODING_DEFAULT = "UTF-8";
	private static final boolean NOCACHE_DEFAULT = true;

	private static Logger log = LoggerFactory.getLogger(Struts2Util.class);

	private Struts2Util() { 
	}

	// 鍙栧緱Request/Response/Session鐨勭畝鍖栧嚱鏁� //

	/**
	 * 鍙栧緱HttpSession鐨勭畝鍖栨柟娉�.
	 */
	public static HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}

	/**
	 * 鍙栧緱HttpRequest鐨勭畝鍖栨柟娉�.
	 */
	public static HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * 鍙栧緱HttpResponse鐨勭畝鍖栨柟娉�.
	 */
	public static HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	/**
	 * 鍙栧緱Request Parameter鐨勭畝鍖栨柟娉�.
	 */
	public static String getParameter(String name) {
		return getRequest().getParameter(name);
	}

	// 缁曡繃jsp/freemaker鐩存帴杈撳嚭鏂囨湰鐨勫嚱鏁� //

	/**
	 * 鐩存帴杈撳嚭鍐呭鐨勭畝渚垮嚱鏁�.
	 
	 * eg.
	 * render("text/plain", "hello", "encoding:GBK");
	 * render("text/plain", "hello", "no-cache:false");
	 * render("text/plain", "hello", "encoding:GBK", "no-cache:false");
	 * 
	 * @param headers 鍙彉鐨刪eader鏁扮粍锛岀洰鍓嶆帴鍙楃殑鍊间负"encoding:"鎴�"no-cache:",榛樿鍊煎垎鍒负UTF-8鍜宼rue.                 
	 */
	public static void render(final String contentType, final String content, final String... headers) {
		try {
			//鍒嗘瀽headers鍙傛暟
			String encoding = ENCODING_DEFAULT;
			boolean noCache = NOCACHE_DEFAULT;
			for (String header : headers) {
				String headerName = StringUtils.substringBefore(header, ":");
				String headerValue = StringUtils.substringAfter(header, ":");

				if (StringUtils.equalsIgnoreCase(headerName, ENCODING_PREFIX)) {
					encoding = headerValue;
				} else if (StringUtils.equalsIgnoreCase(headerName, NOCACHE_PREFIX)) {
					noCache = Boolean.parseBoolean(headerValue);
				} else
					throw new IllegalArgumentException(headerName + "涓嶆槸涓�涓悎娉曠殑header绫诲瀷");
			}

			HttpServletResponse response = ServletActionContext.getResponse();

			//璁剧疆headers鍙傛暟
			String fullContentType = contentType + ";charset=" + encoding;
			response.setContentType(fullContentType);
			if (noCache) {
				response.setHeader("Pragma", "No-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0);
			}

			response.getWriter().write(content);

		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 鐩存帴杈撳嚭鏂囨湰.
	 * @see #render(String, String, String...)
	 */
	public static void renderText(final String text, final String... headers) {
		render("text/plain", text, headers);
	}

	/**
	 * 鐩存帴杈撳嚭HTML.
	 * @see #render(String, String, String...)
	 */
	public static void renderHtml(final String html, final String... headers) {
		render("text/html", html, headers);
	}

	/**
	 * 鐩存帴杈撳嚭XML.
	 * @see #render(String, String, String...)
	 */
	public static void renderXml(final String xml, final String... headers) {
		render("text/xml", xml, headers);
	}

	/**
	 * 鐩存帴杈撳嚭JSON.
	 * 
	 * @param string json瀛楃涓�.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final String string, final String... headers) {
		render("application/json", string, headers);
	}

	/**
	 * 鐩存帴杈撳嚭JSON.
	 * 
	 * @param map Map瀵硅薄,灏嗚杞寲涓簀son瀛楃涓�.
	 * @see #render(String, String, String...)
	 */
	@SuppressWarnings("unchecked")
	public static void renderJson(final Map map, final String... headers) {
		String jsonString = JSONObject.fromObject(map).toString();
		renderJson(jsonString, headers);
	}

	/**
	 * 鐩存帴杈撳嚭JSON.
	 * 
	 * @param object Java瀵硅薄,灏嗚杞寲涓簀son瀛楃涓�.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final Object object, final String... headers) {
		String jsonString =  JSONObject.fromObject(object).toString();
		renderJson(jsonString, headers);
	}
}
