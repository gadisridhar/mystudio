/**
 * Light-commons Project
 * http://light-commons.googlecode.com
 * Copyright (C) 2008 Jason Green
 * email: guileen@gmail.com
 *
 * License: Apache License 2.0 
 * (http://www.apache.org/licenses/LICENSE-2.0)
 *
 */
package org.yy.common.template;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import org.yy.common.io.IOUtils;
import org.yy.common.resource.Resource;
import org.yy.common.resource.ResourceLoader;
import org.yy.common.util.Assert;

/**
 * @author gl
 * @since Jun 16, 2008
 */
public abstract class AbstractTemplateFactory implements TemplateFactory {

	private ResourceLoader resourceLoader;
	
	public abstract Template compileSource(String name,String source) throws IOException;
	
	public Template compile(String name, String source) throws IOException {
		// TODO 此处可以设置缓存机制
		return compileSource(name, source);
	}
	
	public Template getTemplate(final Resource resource, final String encoding) throws Exception {
		return compile(resource.getFullPath(), resource.getText(encoding));
	}

	public Template getTemplate(String name,final InputStream inputStream,
			final String encoding) throws IOException {
			return compile(name,IOUtils.toString(inputStream, encoding));
	}

	/**
	 * @return the resourceLoader
	 */
	public ResourceLoader getResourceLoader() {
		return resourceLoader;
	}

	/**
	 * @param resourceLoader the resourceLoader to set
	 */
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
		initResourceLoader(resourceLoader);
	}

	protected abstract void initResourceLoader(ResourceLoader resourceLoader);
	
	public Template getTemplate(String location, Locale local,String encoding) throws Exception {
		Resource r = resourceLoader.getResource(location,local);
		if(r == null)
			return null;
		return compile(location,r.getText(encoding));
	}
	
	public Template getTemplate(String location, String encoding) throws Exception {
		Assert.notNull(resourceLoader,"No resourceLoader found for TemplateFactory");
		Resource r = resourceLoader.getResource(location);
		if(r == null)
			return null;
		return compile(location,r.getText(encoding));
	}
}
