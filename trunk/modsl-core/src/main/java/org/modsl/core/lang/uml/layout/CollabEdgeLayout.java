/**
 * Copyright 2008 Andrew Vishnyakov <avishn@gmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.modsl.core.lang.uml.layout;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.List;

import org.modsl.core.agt.layout.AbstractNonConfigurableLayout;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.Label;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.model.Pt;
import org.modsl.core.lang.uml.UMLMetaType;

/**
 * Edge label placement
 * @author avishnyakov
 */
public class CollabEdgeLayout extends AbstractNonConfigurableLayout {

    @Override
    public void apply(Edge edge) {
        List<Label> lst = edge.getLabels(UMLMetaType.COLLAB_EDGE_LABEL);
        if (lst.isEmpty()) {
            return;
        } else {
            Label midLabel = lst.get(0);
            Pt midPoint = getMidPoint(edge); 
           midLabel.setPos(getTextPos(edge));
           
        }
    }

    /**
     * @return position of the connector's midpoint
     */
    public Pt getMidPoint(Edge edge) {
        //      return parent.getNode1().getCtrPos().plus(parent.getNode2().getCtrPos().minus(parent.getNode1().getCtrPos()).div(2d));
        Node n1 = edge.getNode1();
        Node n2 = edge.getNode2();
        double ratio = 1d * n1.getOutDegree() / (n1.getOutDegree() + n2.getInDegree());
        ratio = min(2d / 3d, max(ratio, 1d / 3d)); // TODO 
        return n1.getCtrPos().plus(n2.getCtrPos().minus(n1.getCtrPos()).mulBy(ratio));
    }

    /**
     * @return position of the label text
     */
    public Pt getTextPos(Edge edge) {
        return getMidPoint(edge).decBy(new Pt(getFt().getStringWidth(parent.getName()) / 2d, 0));
    }

    /**
     * @return size of the label text bg
     */
    public Pt getTextBgSize(Edge edge) {
        return new Pt(getFt().getStringWidth(parent.getName()), getFt().getHeight());
    }

    /**
     * @return pos of the label text bg
     */
    public Pt getTextBgPos(Edge edge) {
        return getTextPos().decBy(new Pt(0, getFt().getBaseline()));
    } /*
        * 
        *  @Override
           public void apply() {
               super.apply(node);
               FontTransform ft = node.getType().getConfig().getFontTransform();
               node.getSize().y += ft.getBottomPadding();
           }
        */

}
