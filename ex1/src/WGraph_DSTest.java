

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_DSTest {
    private static Random _rnd = null;

    @Test
    void nodeSize() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(1);

        g.removeNode(2);
        g.removeNode(1);
        g.removeNode(1);
        int s = g.nodeSize();
        assertEquals(1,s);


        weighted_graph g1 = new WGraph_DS();
        assertEquals(0, g1.nodeSize());
        g1.addNode(5);
        g1.addNode(5);
        g1.removeNode(4);
        assertEquals(1,g1.nodeSize());
        g1.addNode(7);
        g1.removeNode(5);
        assertEquals(1, g1.nodeSize());
        g1.addNode(1);
        g1.addNode(5);
        assertEquals(3,g1.nodeSize());
        g1.connect(1,5,1.2);
        assertEquals(3,g1.nodeSize());

    }

    @Test
    void edgeSize() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);
        g.connect(0,1,1);
        int e_size =  g.edgeSize();
        assertEquals(3, e_size);
        double w03 = g.getEdge(0,3);
        double w30 = g.getEdge(3,0);
        assertEquals(w03, w30);
        assertEquals(w03, 3);
        weighted_graph g1 = new WGraph_DS();
        assertEquals(0,g1.edgeSize());
        g1.addNode(0);
        g1.addNode(1);
        g1.addNode(2);
        g1.connect(0,1,0.1);
        assertEquals(1,g1.edgeSize());
        g1.connect(0,1,0.8);
        assertEquals(1,g1.edgeSize());
        g1.connect(0,2,6);
        assertEquals(2,g1.edgeSize());
        g1.removeEdge(1,2);
        assertEquals(2,g1.edgeSize());
        g1.removeEdge(0,1);
        assertEquals(1,g1.edgeSize());
        g1.removeEdge(0,1);
        assertEquals(1,g1.edgeSize());
        g1.removeEdge(0,2);
        assertEquals(0,g1.edgeSize());
        g1.connect(0,1,-1);
        assertEquals(0,g1.edgeSize());
        g1.connect(0,0,2);
        assertEquals(0,g1.edgeSize());

    }

    @Test
    void getV() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);
        g.connect(0,1,1);
        Collection<node_info> v = g.getV();
        Iterator<node_info> iter = v.iterator();
        while (iter.hasNext()) {
            node_info n = iter.next();
            assertNotNull(n);
        }
    }

    @Test
    void hasEdge() {
        int v = 10, e = v*(v-1)/2;
        weighted_graph g = graph_creator(v,e,1);
        for(int i=0;i<v;i++) {
            for(int j=i+1;j<v;j++) {
                boolean b = g.hasEdge(i,j);
                assertTrue(b);
                assertTrue(g.hasEdge(j,i));
            }
        }
        weighted_graph g1=new WGraph_DS();
        assertEquals(false,g1.hasEdge(1,2));
        g1.addNode(0);
        g1.addNode(1);
        g1.addNode(2);
        g1.connect(0,1,0.1);
        assertEquals(true,g1.hasEdge(0,1));
        assertEquals(false,g1.hasEdge(0,2));
      //  g1.connect(0,1,0.8);
       // assertEquals(1,g1.edgeSize());
        g1.connect(0,2,6);
        assertEquals(true,g1.hasEdge(0,2));
        assertEquals(true,g1.hasEdge(2,0));
        g1.removeEdge(0,1);
        assertEquals(false,g1.hasEdge(0,1));
        g1.connect(0,1,-1);
        assertEquals(false,g1.hasEdge(0,1));
        assertEquals(false,g1.hasEdge(1,0));
        g1.connect(0,0,6);
        assertEquals(false,g1.hasEdge(0,0));
        g1=new WGraph_DS();
        for(int j=0; j<7; j++){
            g1.addNode(j);
        }        g1.connect(0,3,1);
        g1.connect(0,2,2);
        g1.connect(0,1,3);
        g1.connect(0,6,4.1);
        g1.connect(1,6,3);
        g1.connect(2,3,2);
        g1.connect(3,4,1);
        g1.connect(4,5,1);
        g1.connect(6,5,1);
        assertEquals(true,g1.hasEdge(6,5));
        assertEquals(true,g1.hasEdge(5,6));
        assertEquals(true,g1.hasEdge(4,5));
        assertEquals(true,g1.hasEdge(5,4));
        assertEquals(true,g1.hasEdge(3,2));
    }

    @Test
    void connect() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);
        g.removeEdge(0,1);
        assertFalse(g.hasEdge(1,0));
        g.removeEdge(2,1);
        g.connect(0,1,1);
        double w = g.getEdge(1,0);
        assertEquals(w,1);
        weighted_graph g1 = new WGraph_DS();
        for(int i=0; i<10; i++){
            g1.addNode(i);
        }
        for(int i=0; i<5; i++) {
            for (int j = 0; j < 5; j++) {
                g1.connect(i, j, i * j);
            }
        }
        for(int i=0; i<5; i++) {
            for (int j = 0; j < 5; j++) {
                if(i!=j)  assertEquals(i * j, g1.getEdge(i,j));
            }
        }
        assertEquals(-1,g1.getEdge(1,8));
        g1.connect(1,2,-9);
        assertEquals(2,g1.getEdge(1,2));
        g1.connect(5,8,0.1);
        assertEquals(0.1,g1.getEdge(5,8));
        g1.connect(5,8,-2);
        assertEquals(0.1,g1.getEdge(5,8));
    }


    @Test
    void removeNode() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);
        g.removeNode(4);
        g.removeNode(0);
        assertFalse(g.hasEdge(1,0));
        int e = g.edgeSize();
        assertEquals(0,e);
        assertEquals(3,g.nodeSize());
        weighted_graph g1 = new WGraph_DS();
        for(int i=0; i<10; i++){
            g1.addNode(i);
        }
        for(int i=0; i<5; i++) {
            for (int j = 0; j < 5; j++) {
                g1.connect(i, j, i * j);
            }
        }
        g1.removeNode(0);
        assertEquals(6,g1.edgeSize());
        assertFalse(g1.hasEdge(1,0));
        assertNull(g1.removeNode(12));
        assertEquals(3,g1.removeNode(3).getKey());
        assertNull(g1.removeNode(3));
    }

    @Test
    void removeEdge() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);
        g.removeEdge(0,3);
        double w = g.getEdge(0,3);
        assertEquals(w,-1);
    }


    ///////////////////////////////////
    /**
     * Generate a random graph with v_size nodes and e_size edges
     * @param v_size
     * @param e_size
     * @param seed
     * @return
     */
    public static weighted_graph graph_creator(int v_size, int e_size, int seed) {
        weighted_graph g = new WGraph_DS();
        _rnd = new Random(seed);
        for(int i=0;i<v_size;i++) {
            g.addNode(i);
        }
        // Iterator<node_data> itr = V.iterator(); // Iterator is a more elegant and generic way, but KIS is more important
        int[] nodes = nodes(g);
        while(g.edgeSize() < e_size) {
            int a = nextRnd(0,v_size);
            int b = nextRnd(0,v_size);
            int i = nodes[a];
            int j = nodes[b];
            double w = _rnd.nextDouble();
            g.connect(i,j, w);
        }
        return g;
    }
    private static int nextRnd(int min, int max) {
        double v = nextRnd(0.0+min, (double)max);
        int ans = (int)v;
        return ans;
    }
    private static double nextRnd(double min, double max) {
        double d = _rnd.nextDouble();
        double dx = max-min;
        double ans = d*dx+min;
        return ans;
    }
    /**
     * Simple method for returning an array with all the node_data of the graph,
     * Note: this should be using an Iterator<node_edge> to be fixed in Ex1
     * @param g
     * @return
     */
    private static int[] nodes(weighted_graph g) {
        int size = g.nodeSize();
        Collection<node_info> V = g.getV();
        node_info[] nodes = new node_info[size];
        V.toArray(nodes); // O(n) operation
        int[] ans = new int[size];
        for(int i=0;i<size;i++) {ans[i] = nodes[i].getKey();}
        Arrays.sort(ans);
        return ans;
    }
}
