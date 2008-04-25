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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.modsl.core.agt.visitor.AbstractVisitor;

/**
 * Graph node
 * @author AVishnyakov
 * @param <T> type enum
 */
public class Node<T extends MetaType> extends AbstractGraphElement<T> {

    /**
     * List of children nodes
     */
    protected List<Node<T>> nodes = new LinkedList<Node<T>>();

    /**
     * Map of children nodes {name->node}
     */
    protected Map<String, Node<T>> nodeMap = new HashMap<String, Node<T>>();

    /**
     * List of children edges
     */
    protected List<Edge<T>> edges = new LinkedList<Edge<T>>();

    /**
     * Map of children edges {name->edge}
     */
    protected Map<String, Edge<T>> edgeMap = new HashMap<String, Edge<T>>();

    /**
     * This element's size
     */
    protected Pt size;

    /**
     * This element's position (it's left top corner) relative to it's parent
     * node
     */
    protected Pt pos;

    /**
     * Create new
     * @param type type
     */
    public Node(T type) {
        super(type);
    }

    /**
     * Create new
     * @param type type
     * @param name name
     */
    public Node(T type, String name) {
        super(type, name);
    }

    @Override
    public void accept(AbstractVisitor<T> visitor) {
        visitor.in(this);
        for (Edge<T> e : edges) {
            e.accept(visitor);
        }
        for (Node<T> n : nodes) {
            n.accept(visitor);
        }
        visitor.out(this);
    }

    /**
     * Add child edge
     * @param child
     */
    public void add(Edge<T> child) {
        child.parent = this;
        edges.add(child);
        edgeMap.put(child.getName(), child);
    }

    /**
     * Add child node
     * @param child
     */
    public void add(Node<T> child) {
        child.parent = this;
        nodes.add(child);
        nodeMap.put(child.getName(), child);
    }

    /**
     * @param key
     * @return edge by it's name
     */
    public Edge<T> getEdge(String key) {
        return edgeMap.get(key);
    }

    /**
     * @return children edge list
     */
    public List<Edge<T>> getEdges() {
        return edges;
    }

    /**
     * @param key
     * @return node by it's name
     */
    public Node<T> getNode(String key) {
        return nodeMap.get(key);
    }

    /**
     * @return children node list
     */
    public List<Node<T>> getNodes() {
        return nodes;
    }

    /**
     * @return size
     */
    public Pt getSize() {
        return size;
    }

    /**
     * Set size
     * @param size
     */
    public void setSize(Pt size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return name + ":" + type;
    }

    public Pt getPos() {
        return pos;
    }

    public void setPos(Pt pos) {
        this.pos = pos;
    }

}