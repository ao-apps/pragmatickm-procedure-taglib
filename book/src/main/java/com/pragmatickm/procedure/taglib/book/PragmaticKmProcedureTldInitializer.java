/*
 * pragmatickm-procedure-taglib - Procedures nested within SemanticCMS pages and elements in a JSP environment.
 * Copyright (C) 2017, 2019  AO Industries, Inc.
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
package com.pragmatickm.procedure.taglib.book;

import com.aoindustries.net.DomainName;
import com.aoindustries.net.Path;
import com.aoindustries.validation.ValidationException;
import com.semanticcms.core.model.BookRef;
import com.semanticcms.core.model.ResourceRef;
import com.semanticcms.tagreference.TagReferenceInitializer;
import java.util.LinkedHashMap;
import java.util.Map;

public class PragmaticKmProcedureTldInitializer extends TagReferenceInitializer {

	private static final Map<String,String> additionalApiLinks = new LinkedHashMap<>();
	static {
		// Self
		additionalApiLinks.put("com.pragmatickm.procedure.taglib.", Maven.properties.getProperty("project.url") + "apidocs/");
		// Dependencies
		additionalApiLinks.put("com.semanticcms.core.model.", "https://semanticcms.com/core/model/apidocs/");
	}

	public PragmaticKmProcedureTldInitializer() throws ValidationException {
		super(
			"Procedure Taglib Reference",
			"Taglib Reference",
			new ResourceRef(
				new BookRef(
					DomainName.valueOf("pragmatickm.com"),
					Path.valueOf("/procedure/taglib")
				),
				Path.valueOf("/pragmatickm-procedure.tld")
			),
			Maven.properties.getProperty("documented.javadoc.link.javase"),
			Maven.properties.getProperty("javadoc.link.javaee.6"),
			additionalApiLinks
		);
	}
}
