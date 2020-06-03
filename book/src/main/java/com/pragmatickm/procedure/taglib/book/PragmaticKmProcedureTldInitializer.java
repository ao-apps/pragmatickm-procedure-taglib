/*
 * pragmatickm-procedure-taglib - Procedures nested within SemanticCMS pages and elements in a JSP environment.
 * Copyright (C) 2017, 2019, 2020  AO Industries, Inc.
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

import com.semanticcms.tagreference.TagReferenceInitializer;

public class PragmaticKmProcedureTldInitializer extends TagReferenceInitializer {

	public PragmaticKmProcedureTldInitializer() {
		super(
			Maven.properties.getProperty("project.name") + " Reference",
			"Taglib Reference",
			"/procedure/taglib",
			"/pragmatickm-procedure.tld",
			true,
			Maven.properties.getProperty("documented.javadoc.link.javase"),
			Maven.properties.getProperty("documented.javadoc.link.javaee"),
			// Self
			"com.pragmatickm.procedure.taglib", Maven.properties.getProperty("project.url") + "apidocs/",
			// Dependencies
			"com.semanticcms.core.model", "https://semanticcms.com/core/model/apidocs/"
		);
	}
}
