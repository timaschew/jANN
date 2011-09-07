package de.unikassel.ann.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Pass the constructor as parametr for the getBundle methode:
 * 
 * <pre>
 * ResourceBundle bundle = ResourceBundle.getBundle(&quot;langpack&quot;, new XMLResourceBundleControl());
 * </pre>
 */

public class XMLResourceBundleControl extends ResourceBundle.Control {
	private static String XML = "xml";

	@Override
	public List<String> getFormats(final String baseName) {
		return Collections.singletonList(XML);
	}

	@Override
	public ResourceBundle newBundle(final String baseName, final Locale locale, final String format, final ClassLoader loader,
			final boolean reload) throws IllegalAccessException, InstantiationException, IOException {

		if (baseName == null || locale == null || format == null || loader == null) {
			throw new NullPointerException();
		}
		ResourceBundle bundle = null;
		if (!format.equals(XML)) {
			return null;
		}

		String bundleName = toBundleName(baseName, locale);
		String resourceName = toResourceName(bundleName, format);
		URL url = loader.getResource(resourceName);
		if (url == null) {
			return null;
		}
		URLConnection connection = url.openConnection();
		if (connection == null) {
			return null;
		}
		if (reload) {
			connection.setUseCaches(false);
		}
		InputStream stream = connection.getInputStream();
		if (stream == null) {
			return null;
		}
		BufferedInputStream bis = new BufferedInputStream(stream);
		bundle = new XMLResourceBundle(bis);
		bis.close();

		return bundle;
	}
}

class XMLResourceBundle extends ResourceBundle {
	private Properties props;

	XMLResourceBundle(final InputStream stream) throws IOException {
		props = new Properties();
		props.loadFromXML(stream);
	}

	@Override
	protected Object handleGetObject(final String key) {
		return props.getProperty(key);
	}

	@Override
	public Enumeration<String> getKeys() {
		Set<String> handleKeys = props.stringPropertyNames();
		return Collections.enumeration(handleKeys);
	}
}
