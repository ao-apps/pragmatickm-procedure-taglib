/*
 * pragmatickm-procedure-taglib - Procedures nested within SemanticCMS pages and elements in a JSP environment.
 * Copyright (C) 2014, 2015, 2016, 2017, 2020  AO Industries, Inc.
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

import static com.aoindustries.encoding.Coercion.nullIfEmpty;
import com.aoindustries.html.servlet.HtmlEE;
import static com.aoindustries.taglib.AttributeUtils.resolveValue;
import com.pragmatickm.procedure.model.Procedure;
import com.pragmatickm.procedure.renderer.html.ProcedureHtmlRenderer;
import com.semanticcms.core.model.ElementContext;
import com.semanticcms.core.model.Page;
import com.semanticcms.core.pages.CaptureLevel;
import com.semanticcms.core.pages.local.CurrentPage;
import com.semanticcms.core.renderer.html.PageIndex;
import com.semanticcms.core.taglib.ElementTag;
import java.io.IOException;
import java.io.Writer;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;

public class ProcedureTag extends ElementTag<Procedure> /*implements StyleAttribute*/ {

	private ValueExpression style;
	public void setStyle(ValueExpression style) {
		this.style = style;
	}

	private ValueExpression label;
	public void setLabel(ValueExpression label) {
		this.label = label;
    }

	@Override
	protected Procedure createElement() {
		return new Procedure();
	}

	@Override
	protected void evaluateAttributes(Procedure procedure, ELContext elContext) throws JspTagException, IOException {
		super.evaluateAttributes(procedure, elContext);
		procedure.setLabel(resolveValue(label, String.class, elContext));
	}

	private PageIndex pageIndex;
	private Object styleObj;

	@Override
	protected void doBody(Procedure procedure, CaptureLevel captureLevel) throws JspException, IOException {
		final PageContext pageContext = (PageContext)getJspContext();
		final HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		final Page currentPage = CurrentPage.getCurrentPage(request);
		if(currentPage == null) throw new JspTagException("<procedure> tag must be nested inside a <page> tag.");
		// Label defaults to page short title
		if(procedure.getLabel() == null) {
			procedure.setLabel(currentPage.getShortTitle());
		}
		if(captureLevel == CaptureLevel.BODY) {
			pageIndex = PageIndex.getCurrentPageIndex(pageContext.getRequest());
			styleObj = nullIfEmpty(resolveValue(style, Object.class, pageContext.getELContext()));
		}
		super.doBody(procedure, captureLevel);
	}

	@Override
	public void writeTo(Writer out, ElementContext context) throws IOException {
		PageContext pageContext = (PageContext)getJspContext();
		ProcedureHtmlRenderer.writeProcedureTable(
			pageIndex,
			HtmlEE.get(pageContext.getServletContext(), (HttpServletRequest)pageContext.getRequest(), out),
			context,
			styleObj,
			getElement()
		);
	}
}
