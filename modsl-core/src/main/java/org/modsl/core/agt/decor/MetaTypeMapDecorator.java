/**
 * Copyright 2008 Andrew Vishnyakov <avishn@gmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package org.modsl.core.agt.decor;

import java.util.HashMap;
import java.util.Map;

import org.modsl.core.agt.model.MetaType;
import org.modsl.core.agt.model.Node;

/**
 * Adds quick access to the meta-type map when rendering the string template
 * @author AVishnyakov
 */
public class MetaTypeMapDecorator extends AbstractDecorator<Node> {

	protected Map<String, Object> metaTypeMap;

	/**
	 * Creates new instance for the given meta type class
	 * @param mtc
	 */
	public MetaTypeMapDecorator(Class<? extends MetaType> mtc) {
		Object[] mta = mtc.getEnumConstants();
		metaTypeMap = new HashMap<String, Object>(mta.length);
		for (Object mt : mta) {
			metaTypeMap.put(mt.toString(), mt);
		}
	}

	/**
	 * Convenience method for string template access to the meta-type map
	 * @return meta type map
	 */
	public Map<String, Object> getMeta() {
		return metaTypeMap;
	}

}