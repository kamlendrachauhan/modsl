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

package org.modsl.core.agt.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RootAGTModelTest extends AbstractAGTModelTest {

	@Test
	public void recalcSize() {
		root.recalcSize();
		assertEquals(1200d, root.getSize().x, Pt.EPSILON);
		assertEquals(1200d, root.getSize().y, Pt.EPSILON);
	}

	@Test
	public void getMaxPt() {
		assertEquals(1200d, root.getMaxPt().x, Pt.EPSILON);
		assertEquals(1200d, root.getMaxPt().y, Pt.EPSILON);
	}

	@Test
	public void getMinPt() {
		assertEquals(0d, root.getMinPt().x, Pt.EPSILON);
		assertEquals(0d, root.getMinPt().y, Pt.EPSILON);
	}

	@Test
	public void rescale() {
		root.rescale(new Pt(1000d, 1000d));
		assertEquals(1000d, root.getSize().x, Pt.EPSILON);
		assertEquals(1000d, root.getSize().y, Pt.EPSILON);
		assertEquals(800d, root.getNode("TopRight").getPos().x, 1d);
		assertEquals(0d, root.getNode("TopRight").getPos().y, 1d);
		assertEquals(0d, root.getNode("BottomLeft").getPos().x, 1d);
		assertEquals(800d, root.getNode("BottomLeft").getPos().y, 1d);
		assertEquals(400d, root.getNode("BottomMiddle").getPos().x, 1d);
		assertEquals(880d, root.getNode("BottomMiddle").getPos().y, 1d);
		assertEquals(1000000d, root.getArea(), 1d);
	}

	@Test
	public void getArea() {
		root.recalcSize();
		assertEquals(1440000d, root.getArea(), Pt.EPSILON);
	}

}