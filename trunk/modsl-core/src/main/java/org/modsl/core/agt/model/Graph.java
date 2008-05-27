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

package org.modsl.core.agt.model;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.awt.Font;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.modsl.core.agt.common.FontTransform;
import org.modsl.core.agt.visitor.AbstractVisitor;

public class Graph extends AbstractBox<Graph> {

    private static int counter = 0;

    /**
     * List of children nodes
     */
    List<Node> nodes = new LinkedList<Node>();

    /**
     * Labels
     */
    List<GraphLabel> labels = new LinkedList<GraphLabel>();

    /**
     * Map of children nodes {name->node}
     */
    Map<String, Node> nodeMap = new HashMap<String, Node>();

    /**
     * List of children edges
     */
    List<Edge> edges = new LinkedList<Edge>();

    /**
     * This element's requested size
     */
    Pt reqSize = new Pt();

    /**
     * Top padding
     */
    double topPadding = 0d;

    /**
     * Bottom padding
     */
    double bottomPadding;

    /**
     * Left padding
     */
    double leftPadding;

    /**
     * Right padding
     */
    double rightPadding;

    /**
     * Processing attributes
     */
    Map<String, String> procAttrs = new HashMap<String, String>();

    /**
     * Status line
     */
    FontTransform statusFt = new FontTransform("Tahoma", 8, Font.PLAIN);
    
    /**
     * Log message container
     */
    List<String> logMessages;

    public Graph(MetaType type) {
        super(type);
        this.index = counter++;
        resetPaddings();
    }

    public Graph(MetaType type, String name) {
        super(type, name);
        resetPaddings();
        this.index = counter++;
    }

    @Override
    public void accept(AbstractVisitor visitor) {
        visitor.in(this);
        for (Edge e : edges) {
            e.accept(visitor);
        }
        for (Node n : nodes) {
            n.accept(visitor);
        }
        for (GraphLabel l : getLabels()) {
            l.accept(visitor);
        }
        visitor.out(this);
    }

    /**
     * Add child edge
     * @param child
     */
    public void add(Edge child) {
        child.parent = this;
        edges.add(child);
    }

    /**
     * Add child node
     * @param child
     */
    public void add(Node child) {
        child.parent = this;
        nodes.add(child);
        nodeMap.put(child.getName(), child);
    }

    public void addProcAttr(String key, String value) {
        procAttrs.put(key, value);
    }

    /**
     * @return node's area in pixels^2
     */
    public double getArea() {
        return getSize().x * getSize().y;
    }

    public double getBottomPadding() {
        return bottomPadding;
    }

    /**
     * @param index
     * @return edge by index
     */
    public Edge getEdge(int index) {
        return edges.get(index);
    }

    /**
     * @return all edge labels
     */
    public List<EdgeLabel> getEdgeLabels() {
        List<EdgeLabel> l = new LinkedList<EdgeLabel>();
        for (Edge e : edges) {
            l.addAll(e.getLabels());
        }
        return l;
    }

    /**
     * @return children edge list
     */
    public List<Edge> getEdges() {
        return edges;
    }

    private Pt getExtraPadding() {
        return new Pt(leftPadding + rightPadding + 1, topPadding + bottomPadding + 1);
    }

    public List<GraphLabel> getLabels() {
        return labels;
    }

    public double getLeftPadding() {
        return leftPadding;
    }

    public List<String> getLogMessages() {
        return logMessages;
    }

    /**
     * @param index
     * @return node by index
     */
    public Node getNode(int index) {
        return nodes.get(index);
    }

    /**
     * @param key
     * @return node by it's name
     */
    public Node getNode(String key) {
        return nodeMap.get(key);
    }

    /**
     * @return children node list
     */
    public List<Node> getNodes() {
        return nodes;
    }

    public Map<String, String> getProcAttrs() {
        return procAttrs;
    }

    /**
     * @return size requested by the client
     */
    public Pt getReqSize() {
        return reqSize;
    }

    public double getRightPadding() {
        return rightPadding;
    }

    public Pt getStatusLine() {
        return new Pt(statusFt.getLeftPadding(), getSize().y - statusFt.getExtHeight(1) + statusFt.getExtBaseline(0));
    }

    /**
     * @return sum of all edge lengths
     */
    public double getSumChildEdgeLengths() {
        double len = 0d;
        for (Edge e : edges) {
            len += e.getLength();
        }
        return len;
    }

    public double getTopPadding() {
        return topPadding;
    }

    /**
     * @return max position
     */
    public Pt maxPos() {
        MinMaxVisitor mmv = new MinMaxVisitor(new Pt(-Double.MAX_VALUE, -Double.MAX_VALUE)) {
            @Override
            void apply(AbstractBox<?> b) {
                p.x = max(p.x, b.getPos().x);
                p.y = max(p.y, b.getPos().y);
            }
        };
        accept(mmv);
        return mmv.p;
    }

    /**
     * Bottom right corner's (x, y)
     * @return max (x, y)
     */
    public Pt maxPt() {
        MinMaxVisitor mmv = new MinMaxVisitor(new Pt(-Double.MAX_VALUE, -Double.MAX_VALUE)) {
            @Override
            void apply(AbstractBox<?> b) {
                p.x = max(p.x, b.getPos().x + b.getSize().x);
                p.y = max(p.y, b.getPos().y + b.getSize().y);
            }
        };
        accept(mmv);
        return mmv.p;
    }

    /**
     * @return node with max x (the rightmost one, including it's size)
     */
    public AbstractBox<?> maxXBox() {
        MinMaxVisitor mmv = new MinMaxVisitor() {
            @Override
            void apply(AbstractBox<?> b) {
                box = box == null ? b : (box.getPos().x + box.getSize().x < b.getPos().x + b.getSize().x ? b : box);
            }
        };
        accept(mmv);
        return mmv.box;
    }

    /**
     * @return node with max y (the lowest one, including it's size)
     */
    public AbstractBox<?> maxYBox() {
        MinMaxVisitor mmv = new MinMaxVisitor() {
            @Override
            void apply(AbstractBox<?> b) {
                box = box == null ? b : (box.getPos().y + box.getSize().y < b.getPos().y + b.getSize().y ? b : box);
            }
        };
        accept(mmv);
        return mmv.box;
    }

    public Pt minPos() {
        return minPt();
    }

    /**
     * Top left corner's (x, y). Can be different from 0, 0 depending on the
     * nodes' positions
     * @return min (x, y)
     */
    public Pt minPt() {
        MinMaxVisitor mmv = new MinMaxVisitor(new Pt(Double.MAX_VALUE, Double.MAX_VALUE)) {
            @Override
            void apply(AbstractBox<?> b) {
                p.x = min(p.x, b.getPos().x);
                p.y = min(p.y, b.getPos().y);
            }
        };
        accept(mmv);
        return mmv.p;
    }

    /**
     * Shift (x, y) on all vertexes to bring min(x, y) to (0, 0)
     */
    public void normalize() {
        MinMaxVisitor mmv = new MinMaxVisitor(minPt()) {
            @Override
            void apply(AbstractBox<?> b) {
                b.getPos().decBy(p);
            }
        };
        accept(mmv);
    }

    public void randomize(Random random) {
        for (Node n : nodes) {
            n.getPos().randomize(random, reqSize);
        }
    }

    /**
     * Recalculates and sets size of this (non-normalized) graph to true size of
     * the non-normalized graph
     */
    public void recalcSize() {
        size = maxPt().decBy(minPt());
    }

    /**
     * Rescale/normalize diagram to it's current content, add paddings
     * @param newSize new size
     */
    public void rescale() {
        recalcSize();
        rescale(getSize().plus(getExtraPadding()));
    }

    /**
     * Rescale diagram to the given size
     * @param newSize new size
     */
    public void rescale(Pt newSize) {

        if (nodes.isEmpty()) {
            return;
        }

        normalize();
        recalcSize();

        AbstractBox<?> maxXBox = maxXBox();
        AbstractBox<?> maxYBox = maxYBox();

        Pt maxXYSize = new Pt(maxXBox.getSize().x, maxYBox.getSize().y);

        final Pt newSizeExt = newSize.minus(maxXYSize).decBy(getExtraPadding());
        final Pt sizeExt = getSize().minus(maxXYSize).max(1d, 1d);
        final Pt topLeft = new Pt(leftPadding, topPadding);

        MinMaxVisitor mmv = new MinMaxVisitor() {
            @Override
            void apply(AbstractBox<?> b) {
                b.getPos().mulBy(newSizeExt).divBy(sizeExt).incBy(topLeft);
            }
        };
        accept(mmv);

        size = new Pt(newSize);

    }

    /**
     * Resets paddings to the values dictated by font transform object
     * (essentially based on the font size)
     */
    private void resetPaddings() {
        FontTransform ft = type.getConfig().getFontTransform();
        if (ft != null) {
            leftPadding = ft.getLeftPadding();
            rightPadding = ft.getRightPadding();
            topPadding = ft.getTopPadding();
            bottomPadding = ft.getBottomPadding() + statusFt.getExtHeight(1);
        }
    }

    public void setLogMessages(List<String> logMessages) {
        this.logMessages = logMessages;
    }

    public void setReqSize(double x, double y) {
        this.reqSize.x = x;
        this.reqSize.y = y;
    }

}
