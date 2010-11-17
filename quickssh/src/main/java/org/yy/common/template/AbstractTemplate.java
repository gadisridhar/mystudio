/**
 * 
 */
package org.yy.common.template;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;

import org.yy.common.logger.Log;
import org.yy.common.logger.LogFactory;

/**
 * @author gl
 * @since Jun 15, 2008
 */
public abstract class AbstractTemplate implements Template {
	private static final Log log = LogFactory.getLog(AbstractTemplate.class);

	/*
	 * (non-Javadoc)
	 * @see org.lightcommons.template.Template#render(java.lang.Object)
	 */
	public String render(Object rootMap) {
		return render(rootMap, Locale.getDefault());
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.lightcommons.template.Template#render(java.lang.Object, java.io.Writer)
	 */
	public void render(Object rootMap, Writer writer) {
		render(rootMap, writer, Locale.getDefault());
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lightcommons.template.Template#render(java.util.Map,
	 *      java.lang.Object)
	 */
	public String render(final Object rootMap, Locale locale) {
		final StringWriter sw = new StringWriter();
		render(rootMap, sw, locale);
		final String ret = sw.toString();
		try {
			sw.close();
		} catch (final IOException e) {
			log.warn(e);
		}
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lightcommons.template.Template#render(java.lang.Object,
	 *      java.io.Writer)
	 */
	public void render(Object rootMap, Writer writer,Locale locale) {
//		WriterLogger log = new WriterLogger(writer, "TEMPLATE");
		try {
			renderUnsafe(rootMap, writer, locale);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
	}

	public abstract void renderUnsafe(Object rootMap, Writer writer,Locale locale)
			throws Exception;

}
