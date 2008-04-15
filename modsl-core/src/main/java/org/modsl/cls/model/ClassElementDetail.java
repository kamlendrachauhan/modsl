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

package org.modsl.cls.model;

import org.modsl.cls.ClassFontSizeTransform;
import org.modsl.core.model.XY;
import org.modsl.core.model.diagram.ElementDetail;

/**
 * Class diagram implementation of an element detail
 * 
 * @author avishnyakov
 */
public class ClassElementDetail extends ElementDetail<ClassElement> {

	protected ClassElementDetailType type;
	protected ClassElementDetailScope scope;

	public ClassElementDetail(String metaKey, String name, ClassElement parent, ClassElementDetailType type,
			ClassElementDetailScope scope) {
		super(metaKey, parent, name);
		this.type = type;
		this.scope = scope;
		switch (type) {
		case ATTRIBUTE:
			parent.getAttributes().add(this);
			break;
		case METHOD:
			parent.getMethods().add(this);
			break;
		}
	}

	public ClassElementDetailType getType() {
		return type;
	}

	public void calcSizeAndPosition(XY startPosition, ClassFontSizeTransform elementDetailFST, int index) {
		position.x = elementDetailFST.getLeftLeading();
		position.y = startPosition.y + elementDetailFST.getExtPosition(index);
		size.x = elementDetailFST.getExtStringWidth(name);
		size.y = elementDetailFST.getHeight();
	}

}
