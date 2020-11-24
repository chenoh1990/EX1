

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_AlgoTest {

    @Test
    void isConnected() {
        weighted_graph g0 = WGraph_DSTest.graph_creator(0,0,1);
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        assertTrue(ag0.isConnected());

        g0 = WGraph_DSTest.graph_creator(1,0,1);
        ag0 = new WGraph_Algo();
        ag0.init(g0);
        assertTrue(ag0.isConnected());

        g0 = WGraph_DSTest.graph_creator(2,0,1);
        ag0 = new WGraph_Algo();
        ag0.init(g0);
        assertFalse(ag0.isConnected());

        g0 = WGraph_DSTest.graph_creator(2,1,1);
        ag0 = new WGraph_Algo();
        ag0.init(g0);
        assertTrue(ag0.isConnected());

        g0 = WGraph_DSTest.graph_creator(10,30,1);
        ag0.init(g0);
        boolean b = ag0.isConnected();
        assertTrue(b);
        weighted_graph g1 = new WGraph_DS();
        for(int i=0; i<10;i++){
                g1.addNode(i);
        }
        for(int i=0; i<10;i++){
            for (int j=0;j<10;j++){
                g1.connect(i,j,i+j);
            }
        }
        ag0.init(g1);
        assertTrue(ag0.isConnected());
        for(int i=0;i<10;i++){
            g1.removeEdge(0,i);
        }
        assertFalse(ag0.isConnected());
        g1.connect(0,6,6);
        assertTrue(ag0.isConnected());
        g1.removeEdge(0,6);
        g1.removeNode(6);
        assertFalse(ag0.isConnected());
    }

    @Test
    void shortestPathDist() {
        weighted_graph g0 = small_graph();
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        assertTrue(ag0.isConnected());
        double d = ag0.shortestPathDist(0,10);
        assertEquals(d, 5.1);
        weighted_graph g1=new WGraph_DS();
        for(int i=0; i<7; i++){
            g1.addNode(i);
        }

        g1.connect(0,3,1);
        g1.connect(0,2,2);
        g1.connect(0,1,3);
        g1.connect(0,6,4.1);
        g1.connect(1,6,3);
        g1.connect(2,3,2);
        g1.connect(3,4,1);
        g1.connect(4,5,1);
        g1.connect(5,6,1);
        weighted_graph_algorithms ag1= new WGraph_Algo();

        ag1.init(g1);
        assertEquals(4,ag1.shortestPathDist(0,6));
       // assertEquals(4,ag0.shortestPathDist(6,0));
        g1.addNode(7);
        for (node_info n :ag1.shortestPath(0,7) ) {
            System.out.print( n.getKey() + " --> ");
        }
        System.out.println();

        assertEquals(-1,ag1.shortestPathDist(0,7));
        g1.connect(0,3,0.5);
        assertEquals(4.1,ag1.shortestPathDist(0,6));

    }

    @Test
    void shortestPath() {
        weighted_graph g0 = small_graph();
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        List<node_info> sp = ag0.shortestPath(0,10);
        //double[] checkTag = {0.0, 1.0, 2.0, 3.1, 5.1};
        int[] checkKey = {0, 1, 5, 7, 10};
        int i = 0;
        for(node_info n: sp) {
            //assertEquals(n.getTag(), checkTag[i]);
            assertEquals(n.getKey(), checkKey[i]);
            i++;
        }
        weighted_graph g1=new WGraph_DS();
        for(int j=0; j<7; j++){
            g1.addNode(j);
        }
        ag0.init(g1);
        g1.connect(0,3,1);
        g1.connect(0,2,2);
        g1.connect(0,1,3);
        g1.connect(0,6,4.1);
        g1.connect(1,6,3);
        g1.connect(2,3,2);
        g1.connect(3,4,1);
        g1.connect(4,5,1);
        g1.connect(5,6,1);
        sp=ag0.shortestPath(0,6);

        assertEquals(0,sp.get(0).getKey());
        assertEquals(3,sp.get(1).getKey());
        assertEquals(4,sp.get(2).getKey());
        assertEquals(5,sp.get(3).getKey());
        assertEquals(6,sp.get(4).getKey());

        g1.addNode(7);
        sp=ag0.shortestPath(0,7);
        assertEquals(0,sp.size());
        g1.connect(0,3,0.5);
        sp=ag0.shortestPath(0,6);
        assertEquals(0,sp.get(0).getKey());
        assertEquals(3,sp.get(1).getKey());
    }

    @Test
    void save_load() {
        weighted_graph g0 = WGraph_DSTest.graph_creator(10,30,1);
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        String str = "g0.obj";
        try {
            ag0.save(str);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        weighted_graph g1 = WGraph_DSTest.graph_creator(10,30,1);
        WGraph_DS test0 = (WGraph_DS)g0;
        WGraph_DS test1 = (WGraph_DS)g1;

        assertEquals(test0,test1);

        ag0.load(str);
        assertEquals(g0,g1);
        g0.removeNode(0);
        assertNotEquals(g0,g1);
    }

    private weighted_graph small_graph() {
        weighted_graph g0 = WGraph_DSTest.graph_creator(11,0,1);
        g0.connect(0,1,1);
        g0.connect(0,2,2);
        g0.connect(0,3,3);

        g0.connect(1,4,17);
        g0.connect(1,5,1);
        g0.connect(2,4,1);
        g0.connect(3, 5,10);
        g0.connect(3,6,100);
        g0.connect(5,7,1.1);
        g0.connect(6,7,10);
        g0.connect(7,10,2);
        g0.connect(6,8,30);
        g0.connect(8,10,10);
        g0.connect(4,10,30);
        g0.connect(3,9,10);
        g0.connect(8,10,10);

        return g0;
    }
}