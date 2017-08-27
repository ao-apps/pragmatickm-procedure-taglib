/*
 * pragmatickm-procedure-taglib - Procedures nested within SemanticCMS pages and elements in a JSP environment.
 * Copyright (C) 2014, 2015, 2016, 2017  AO Industries, Inc.
 *     support@aoindustries.com
 *     7262 Bull Pen Cir
 *     Mobile, AL 36695
 *
 * This file is part of pragmatickm-procedure-taglib.
 *
 * pragmatickm-procedure-taglib is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * pragmatickm-procedure-taglib is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with pragmatickm-procedure-taglib.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.pragmatickm.procedure.taglib;

import com.pragmatickm.procedure.renderer.html.ProcedureTreeHtmlRenderer;
import com.semanticcms.core.pages.CaptureLevel;
import com.semanticcms.core.pages.local.CurrentCaptureLevel;
import java.io.IOException;
import javax.el.ValueExpression;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class ProcedureTreeTag extends SimpleTagSupport {

	private ValueExpression root;
	public void setRoot(ValueExpression root) {
		this.root = root;
	}

	/**
	 * Creates the nested &lt;ul&gt; and &lt;li&gt; tags for the procedure tree.
	 */
	@Override
	public void doTag() throws JspTagException, IOException {
		try {
			final PageContext pageContext = (PageContext)getJspContext();
			final HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();

			// Get the current capture state
			final CaptureLevel captureLevel = CurrentCaptureLevel.getCaptureLevel(request);
			ProcedureTreeHtmlRenderer.writeProcedureTree(
				pageContext.getServletContext(),
				pageContext.getELContext(),
				request,
				(HttpServletResponse)pageContext.getResponse(),
				captureLevel == CaptureLevel.BODY ? pageContext.getOut() : null,
				root
			);
		} catch(ServletException e) {
			throw new JspTagException(e);
		}
	}
}
