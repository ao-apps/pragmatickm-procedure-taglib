/*
 * pragmatickm-procedure-taglib - Procedures nested within SemanticCMS pages and elements in a JSP environment.
 * Copyright (C) 2017, 2019, 2020, 2021, 2022  AO Industries, Inc.
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

package com.pragmatickm.procedure.taglib.book;

import com.aoapps.lang.validation.ValidationException;
import com.aoapps.net.DomainName;
import com.aoapps.net.Path;
import com.semanticcms.core.model.BookRef;
import com.semanticcms.core.model.ResourceRef;
import com.semanticcms.tagreference.TagReferenceInitializer;

public class PragmaticKmProcedureTldInitializer extends TagReferenceInitializer {

  public PragmaticKmProcedureTldInitializer() throws ValidationException {
    super(
        Maven.properties.getProperty("documented.name") + " Reference",
        "Taglib Reference",
        new ResourceRef(
            new BookRef(
                DomainName.valueOf("pragmatickm.com"),
                Path.valueOf("/procedure/taglib")
            ),
            Path.valueOf("/pragmatickm-procedure.tld")
        ),
        true,
        Maven.properties.getProperty("documented.javadoc.link.javase"),
        Maven.properties.getProperty("documented.javadoc.link.javaee"),
        // Self
        "com.pragmatickm.procedure.taglib", Maven.properties.getProperty("project.url") + "apidocs/com.pragmatickm.procedure.taglib/",
        // Dependencies
        "com.semanticcms.core.model", "https://semanticcms.com/core/model/apidocs/"
    );
  }
}
