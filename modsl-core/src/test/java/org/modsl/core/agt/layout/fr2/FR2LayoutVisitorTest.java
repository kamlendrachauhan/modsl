package org.modsl.core.agt.layout.fr2;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.modsl.core.agt.TMetaType;
import org.modsl.core.agt.model.Graph;
import org.modsl.core.agt.model.Node;
import org.modsl.core.agt.model.Pt;

public class FR2LayoutVisitorTest {

    Logger log = Logger.getLogger(getClass());

    Graph graph = new Graph(TMetaType.GRAPH, "g");
    Node n1 = new Node(TMetaType.NODE, "n1");
    Node n2 = new Node(TMetaType.NODE, "n2");

    FR2LayoutVisitor layout = new FR2LayoutVisitor(TMetaType.GRAPH);

    @Before
    public void setUp() throws Exception {

        graph.setReqSize(new Pt(100d, 100d));
        graph.add(n1);
        n1.setSize(new Pt(10d, 10d));
        graph.add(n2);
        n2.setSize(new Pt(10d, 10d));

        layout.maxIterations = 200;
        layout.tempMultiplier = 0.05d;
        layout.attractionMultiplier = 0.75d;
        layout.repulsionMultiplier = 0.75d;

    }

    @Test
    public void twoBit() {
        layout.in(graph);
        assertEquals(81d, n1.getCtrDelta(n2).len(), 1d);
        //log.debug(new ToStringVisitor().toString(graph));
    }

}