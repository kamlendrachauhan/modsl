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

package org.modsl.cls.layout;

import org.modsl.cls.ClassDiagramConfig;
import org.modsl.cls.model.ClassDiagram;
import org.modsl.core.layout.AbstractLayout;

/**
 * Layout flow for the class diagram. Aggregates ohter layout calls.
 * 
 * @author avishnyakov
 *
 */
public class ClassDiagramLayout extends AbstractLayout<ClassDiagram, ClassDiagramConfig> {

    public ClassDiagramLayout(ClassDiagramConfig config) {
        super(config);
    }

    public void apply(ClassDiagram diagram) {
        new ElementLayout(config).apply(diagram);
        new InitialCirclePosition(config).apply(diagram);
        // new WeightFlip(config).apply(diagram);
        new FruchtermanRheingoldLayout(config).apply(diagram);
        // new DiagramRescale(config).apply(diagram);
        new DiagramRescale(config).apply(diagram);
        diagram.timestamp("layout");
    }

}
