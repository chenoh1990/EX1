import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class WGraph_DS implements weighted_graph {

    private static int counter = 0;
    private HashMap<Integer,node_info> nodes = new HashMap<Integer, node_info>();
    private HashMap<Integer,HashMap<Integer, Double>> edges = new HashMap<Integer,HashMap<Integer, Double>>();


    public WGraph_DS(){


    }

    @Override
    /**
     * this method return the node by given key.
     *
     * @param: key
     */
    public node_info getNode(int key) {

        return nodes.get(key);
    }

    @Override
    /**
     * this method check if there exist edge between node1 & node2.
     *
     * @param: node1.
     * @param: node2.
     */
    public boolean hasEdge(int node1, int node2) {

        node_info one = this.nodes.get(node1);
        node_info two = this.nodes.get(node2);

        if(one == null || two == null) return false;

        else{

            if(this.edges.get(node1).get(node2) != null) return true;

        }
        return false;
    }

    @Override
    /**
     *this methodes returns the weight of the edge between node1 to node2, if not exist - return -1.
     *
     * @param: node1
     * @param :node2
     */
    public double getEdge(int node1, int node2) {

        if (!hasEdge(node1, node2)) return -1;

        return this.edges.get(node1).get(node2);
    }

    @Override
    /**
     * this method adding a new node to graph by given key.
     *
     * @param: key.
     */
    public void addNode(int key) {

        if(this.nodes.get(key) != null){
            return;
        }
         this.nodes.put(key, new nodeData(key));

    }

    @Override
    /**
     * this method connected an edge between node 1 to node 2 if w >= 0.
     * if w < 0 , this method do not do anything.
     * if the edge between node1 to node 2 is already exist, this method only update w.
     *
     * @param: node1.
     * @param: node2.
     * @param: w.
     */
    public void connect(int node1, int node2, double w) {

        if(w<0){
            System.out.println("w < 0");
            return;
        }
        if(this.edges.get(node1) != null){

            this.edges.get(node1).put(node2, w) ;
        }
        else{
            this.edges.put(node1, new HashMap<Integer, Double>());
        }

        if(this.edges.get(node2) != null){

            this.edges.get(node2).put(node1, w) ;
        }
        else{
            this.edges.put(node2, new HashMap<Integer, Double>());
        }


    }

    @Override
    public Collection<node_info> getV() {

        return this.nodes.values();
    }

    @Override
    public Collection<node_info> getV(int node_id) {

        Collection<node_info> ans = new ArrayList<>();

        if(this.nodes.get(node_id) == null){

            return ans;
        }
        Collection<Integer> neighbors = this.edges.get(node_id).keySet();

        for (int i : neighbors){
            ans.add(this.nodes.get(i));
        }
        return ans;
    }

    @Override
    public node_info removeNode(int key) {

        node_info temp = this.nodes.get(key);
        if(temp == null) return temp;

        Collection<node_info> neighbors =  this.getV(key);

        for(node_info i :neighbors){
            this.removeEdge(key, i.getKey());
        }

        this.edges.remove(key);

        return temp;
    }

    @Override
    public void removeEdge(int node1, int node2) {

        if(! this.hasEdge(node1, node2)) return;

        this.edges.get(this.nodes.get(node1).getKey()).remove(node2);
        this.edges.get(this.nodes.get(node2).getKey()).remove(node1);

    }

    @Override
    public int nodeSize() {

        return this.nodes.values().size();
    }

    @Override
    public int edgeSize() {
        return 0;
    }

    @Override
    public int getMC() {
        return 0;
    }


    protected class nodeData implements node_info {

        private int key;
        private String info;
        private Double tag;


        public nodeData(int key){

            this.key = key;
            this.info = "";
            this.tag = -1.0;
        }
        /**
         * default constructor
         */
        public nodeData(){
            this.key = counter;
            counter++;
            this.info = "";
            this.tag = -1.0;
        }

        @Override
        /**
         * this method return the id of this node.
         */
        public int getKey() {

            return this.key;
        }

        @Override
        /**
         * this method return the info of this node.
         */
        public String getInfo() {

            return this.info;
        }

        @Override
        public void setInfo(String s) {

            this.info = s;
        }

        @Override
        /**
         * this method return the tag of this node.
         */
        public double getTag() {

            return this.tag;
        }

        @Override
        public void setTag(double t) {

            this.tag = t;

        }
    }
}
