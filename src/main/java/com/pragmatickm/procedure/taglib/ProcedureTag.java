/*
 * pragmatickm-procedure-taglib - Procedures nested within SemanticCMS pages and elements in a JSP environment.
 * Copyright (C) 2014, 2015, 2016, 2017, 2020, 2021, 2022  AO Industries, Inc.
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
 * along with pragmatickm-procedure-taglib.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.pragmatickm.procedure.taglib;

import com.aoapps.encoding.Doctype;
import com.aoapps.encoding.Serialization;
import com.aoapps.encoding.servlet.DoctypeEE;
import com.aoapps.encoding.servlet.SerializationEE;
import com.aoapps.html.Document;
import static com.aoapps.lang.Coercion.nullIfEmpty;
import static com.aoapps.taglib.AttributeUtils.resolveValue;
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
import java.nio.charset.Charset;
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
  private Charset characterEncoding;

  @Override
  protected void doBody(Procedure procedure, CaptureLevel captureLevel) throws JspException, IOException {
    final PageContext pageContext = (PageContext) getJspContext();
    final HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
    final Page currentPage = CurrentPage.getCurrentPage(request);
    if (currentPage == null) {
      throw new JspTagException("<procedure> tag must be nested inside a <page> tag.");
    }
    // Label defaults to page short title
    if (procedure.getLabel() == null) {
      procedure.setLabel(currentPage.getShortTitle());
    }
    if (captureLevel == CaptureLevel.BODY) {
      ServletContext servletContext = pageContext.getServletContext();
      pageIndex = PageIndex.getCurrentPageIndex(pageContext.getRequest());
      styleObj = nullIfEmpty(resolveValue(style, Object.class, pageContext.getELContext()));
      serialization = SerializationEE.get(servletContext, request);
      doctype = DoctypeEE.get(servletContext, request);
      characterEncoding = Charset.forName(pageContext.getResponse().getCharacterEncoding());
    }
    super.doBody(procedure, captureLevel);
  }

  @Override
  public void writeTo(Writer out, ElementContext context) throws IOException {
    ProcedureHtmlRenderer.writeProcedureTable(
        pageIndex,
        new Document(serialization, doctype, characterEncoding, out)
            .setAutonli(false) // Do not add extra newlines to JSP
            .setIndent(false), // Do not add extra indentation to JSP
        context,
        styleObj,
        getElement()
    );
  }
}
