package com.woniu.network.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Preconditions;

/**
 * 照抄 {@link org.springframework.core.io.ClassPathResource}
 * 
 * @author yang.hao
 * @since 2011-10-27 上午10:17:24
 */
public class ClassPathResource {

	private final String path;

	private ClassLoader classLoader;

	public ClassPathResource(String path) {
		this(path, (ClassLoader) null);
	}

	public ClassPathResource(String path, ClassLoader classLoader) {
		Preconditions.checkNotNull(path, "Path must not be null");
		String pathToUse = StringUtils.trim(path);
		if (pathToUse.startsWith("/")) {
			pathToUse = pathToUse.substring(1);
		}
		this.path = pathToUse;
		this.classLoader = (classLoader != null ? classLoader : getDefaultClassLoader());
	}

	public static ClassLoader getDefaultClassLoader() {
		ClassLoader cl = null;
		try {
			cl = Thread.currentThread().getContextClassLoader();
		} catch (Throwable ex) {
		}
		if (cl == null) {
			cl = ClassPathResource.class.getClassLoader();
		}
		return cl;
	}

	/**
	 * This implementation opens an InputStream for the given class path
	 * resource.
	 * 
	 * @see java.lang.ClassLoader#getResourceAsStream(String)
	 * @see java.lang.Class#getResourceAsStream(String)
	 */
	public InputStream getInputStream() throws IOException {
		InputStream is = this.classLoader.getResourceAsStream(this.path);
		if (is == null) {
			throw new FileNotFoundException(getDescription() + " cannot be opened because it does not exist");
		}
		return is;
	}

	/**
	 * This implementation returns a description that includes the class path
	 * location.
	 */
	public String getDescription() {
		return "class path resource [" + this.path + "]";
	}
}
