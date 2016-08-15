/*
 * pragmatickm-procedure-taglib - Procedures nested within SemanticCMS pages and elements in a JSP environment.
 * Copyright (C) 2014, 2015, 2016  AO Industries, Inc.
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

import com.pragmatickm.procedure.model.Procedure;
import com.pragmatickm.procedure.servlet.impl.ProcedureImpl;
import com.semanticcms.core.model.ElementContext;
import com.semanticcms.core.model.Page;
import com.semanticcms.core.servlet.CaptureLevel;
import com.semanticcms.core.servlet.CurrentPage;
import com.semanticcms.core.servlet.PageIndex;
import com.semanticcms.core.taglib.ElementTag;
import java.io.IOException;
import java.io.Writer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;

public class ProcedureTag extends ElementTag<Procedure> {

	public ProcedureTag() {
		super(new Procedure());
	}

	private String style;
	public void setStyle(String style) {
		if(style!=null && style.isEmpty()) style = null;
		this.style = style;
	}

	public void setLabel(String label) {
		element.setLabel(label);
    }

	private PageIndex pageIndex;
	@Override
	protected void doBody(CaptureLevel captureLevel) throws JspException, IOException {
		final PageContext pageContext = (PageContext)getJspContext();
		final HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		final Page currentPage = CurrentPage.getCurrentPage(request);
		if(currentPage == null) throw new JspTagException("<procedure> tag must be nested inside a <page> tag.");
		pageIndex = PageIndex.getCurrentPageIndex(pageContext.getRequest());
		// Label defaults to page short title
		if(element.getLabel() == null) {
			element.setLabel(currentPage.getShortTitle());
		}
		super.doBody(captureLevel);
	}

	@Override
	public void writeTo(Writer out, ElementContext context) throws IOException {
		ProcedureImpl.writeProcedureTable(pageIndex, out, context, style, element);
	}
}
