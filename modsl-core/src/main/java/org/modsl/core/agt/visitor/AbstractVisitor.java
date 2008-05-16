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

package org.modsl.core.agt.visitor;

import org.modsl.core.agt.model.Bend;
import org.modsl.core.agt.model.Edge;
import org.modsl.core.agt.model.Graph;
import org.modsl.core.agt.model.Label;
import org.modsl.core.agt.model.Node;

/**
 * Visitor interface to traverse the abstarct graph tree. For each type of node
 * implements in() and out() events.
 * 
 * @author avishnyakov
 * 
 * @param <T> element meta type class
 */
public abstract class AbstractVisitor {

    /**
     * Enter bend
     * @param bend
     */
    public void in(Bend bend) {
        // NO OP
    }

    /**
     * Enter edge
     * @param edge
     */
    public void in(Edge edge) {
        // NO OP
    }

    /**
     * Enter graph
     * @param graph
     */
    public void in(Graph graph) {
        // NO OP
    }

    /**
     * Enter label
     * @param label
     */
    public void in(Label<?> label) {
        // NO OP
    }

    /**
     * Enter node
     * @param node
     */
    public void in(Node node) {
        // NO OP
    }

    /**
     * Exit bend
     * @param bend
     */
    public void out(Bend bend) {
        // NO OP
    }

    /**
     * Exit edge
     * @param edge
     */
    public void out(Edge edge) {
        // NO OP
    }
    /**
     * Exit graph
     * @param graph
     */
    public void out(Graph graph) {
        // NO OP
    }

    /**
     * Exit label
     * @param label
     */
    public void out(Label label) {
        // NO OP
    }

    /**
     * Exit node
     * @param node
     */
    public void out(Node node) {
        // NO OP
    }

}
