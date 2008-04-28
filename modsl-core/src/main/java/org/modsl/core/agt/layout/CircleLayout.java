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

package org.modsl.core.agt.layout;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.floor;
import static java.lang.Math.sin;

import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.model.Pt;

/**
 * Lays the element out on a circle to reduce edge crossing in the FR algorithm
 * later
 * @author avishnyakov
 */
public class CircleLayout implements Layout {

    @SuppressWarnings("unused")
    private Logger log = Logger.getLogger(getClass());

    protected double angle;
    protected Node<?> graph;
    protected int circlePositions, maxRounds;
    protected Random random;

    @Override
    public void apply(Edge<?> edge) {
        // nothing to do here
    }

    @Override
    public void apply(Node<?> node) {

        this.graph = node;
        this.circlePositions = graph.getNodes().size();
        this.angle = 2d * PI / circlePositions;

        this.random = new Random(node.hashCode()); // TODO same seed for the same graph name

        initCircle();
        optimizeEdgeLength();

    }

    @Override
    public String getConfigName() {
        return "circle_layout";
    }

    private double getPosAngle(int p) {
        return PI / 4d - angle * p;
    }

    private void initCircle() {
        for (int i = 0; i < circlePositions; i++) {
            setCirclePosition(graph.getNode(i), i);
        }
    }

    private void optimizeEdgeLength() {
        for (int i = 0; i < maxRounds * circlePositions; i++) {
            double curLen = graph.getSumEdgeLengths();
            int p1 = (int) floor(random.nextDouble() * circlePositions);
            int p2 = (int) floor(random.nextDouble() * circlePositions);
            swap(p1, p2);
            if (graph.getSumEdgeLengths() > curLen) {
                swap(p1, p2);
            }
        }
    }

    private void setCirclePosition(Node<?> n, int p) {
        double len = graph.getReqSize().len();
        n.getPos().x = graph.getReqSize().x / 2d + len / 2d * cos(getPosAngle(p));
        n.getPos().y = graph.getReqSize().y / 2d + len / 2d * sin(getPosAngle(p));
    }

    @Override
    public void setLayoutConfig(Map<String, String> propMap) {
        maxRounds = Integer.parseInt(propMap.get("maxRounds"));
    }

    private void swap(int p1, int p2) {
        Node<?> n1 = graph.getNode(p1);
        Node<?> n2 = graph.getNode(p2);
        Pt pt1 = n1.getPos();
        n1.setPos(n2.getPos());
        n2.setPos(pt1);
    }

}