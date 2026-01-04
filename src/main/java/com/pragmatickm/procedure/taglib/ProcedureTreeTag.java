/*
 * pragmatickm-procedure-taglib - Procedures nested within SemanticCMS pages and elements in a JSP environment.
 * Copyright (C) 2014, 2015, 2016, 2017, 2020, 2021, 2022, 2025, 2026  AO Industries, Inc.
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

import com.aoapps.html.servlet.DocumentEE;
import com.pragmatickm.procedure.servlet.impl.ProcedureTreeImpl;
import com.semanticcms.core.servlet.CaptureLevel;
import jakarta.el.ValueExpression;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspTagException;
import jakarta.servlet.jsp.PageContext;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Writes a procedure tree as &lt;ul&gt; and &lt;li&gt; tags.
 */
public class ProcedureTreeTag extends SimpleTagSupport {

  private ValueExpression root;

  public void setRoot(ValueExpression root) {
    this.root = root;
  }

  /**
   * Creates the nested &lt;ul&gt; and &lt;li&gt; tags for the procedure tree.
   */
  @Override
  public void doTag() throws JspException, IOException {
    try {
      final PageContext pageContext = (PageContext) getJspContext();
      final HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

      // Get the current capture state
      final CaptureLevel captureLevel = CaptureLevel.getCaptureLevel(request);
      ServletContext servletContext = pageContext.getServletContext();
      HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
      ProcedureTreeImpl.writeProcedureTree(
          servletContext,
          pageContext.getELContext(),
          request,
          response,
          (captureLevel == CaptureLevel.BODY) ? new DocumentEE(
              servletContext,
              request,
              response,
              pageContext.getOut(),
              false, // Do not add extra newlines to JSP
              false  // Do not add extra indentation to JSP
          ) : null,
          root
      );
    } catch (ServletException e) {
      throw new JspTagException(e);
    }
  }
}
