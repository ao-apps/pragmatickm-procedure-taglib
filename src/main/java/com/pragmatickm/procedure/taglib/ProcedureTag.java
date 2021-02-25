/*
 * pragmatickm-procedure-taglib - Procedures nested within SemanticCMS pages and elements in a JSP environment.
 * Copyright (C) 2014, 2015, 2016, 2017, 2020, 2021  AO Industries, Inc.
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

import com.aoindustries.encoding.Doctype;
import com.aoindustries.encoding.Serialization;
import com.aoindustries.encoding.servlet.DoctypeEE;
import com.aoindustries.encoding.servlet.SerializationEE;
import com.aoindustries.html.Document;
import static com.aoindustries.lang.Coercion.nullIfEmpty;
import static com.aoindustries.taglib.AttributeUtils.resolveValue;
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
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.servlet.ServletContext;
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
	private Serialization serialization;
	private Doctype doctype;

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
			ServletContext servletContext = pageContext.getServletContext();
			pageIndex = PageIndex.getCurrentPageIndex(pageContext.getRequest());
			styleObj = nullIfEmpty(resolveValue(style, Object.class, pageContext.getELContext()));
			serialization = SerializationEE.get(servletContext, request);
			doctype = DoctypeEE.get(servletContext, request);
		}
		super.doBody(procedure, captureLevel);
	}

	@Override
	public void writeTo(Writer out, ElementContext context) throws IOException {
		Document document = new Document(serialization, doctype, out);
		document.setIndent(false); // Do not add extra indentation to JSP
		ProcedureImpl.writeProcedureTable(pageIndex, document, context, styleObj, getElement());
	}
}
