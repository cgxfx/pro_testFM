package tool;

import javax.servlet.http.Cookie;

import org.apache.commons.lang3.StringUtils;

public class CookieValue {
	
	public static String getCookieValue(Cookie[] cookies, String key) {
		if (cookies == null || cookies.length == 0 || StringUtils.isBlank(key))
			return null;
		for (int i = 0, size = cookies.length; i < size; i++) {
			if (cookies[i].getName().equals(key)) {
				return cookies[i].getValue();
			}
		}
		return null;
	}

}
