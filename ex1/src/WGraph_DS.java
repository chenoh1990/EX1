import java.util.*;
import java.io.Serializable;

public class WGraph_DS implements weighted_graph, Serializable {

    private  int MC = 0;
    private int edge_size = 0;
    private int node_size = 0;
    private HashMap<Integer,nodeData> nodes = new HashMap<Integer, nodeData>();
    private HashMap<Integer,HashMap<Integer, Double>> edges = new HashMap<Integer,HashMap<Integer, Double>>();

    /**
     * default constructor.
     */
    public WGraph_DS(){

    }

    /**
     *
     * this method perform a deep copy for a given Wgraph_DS.
     * @param wg
     */
    public WGraph_DS (WGraph_DS wg){

        for (node_info i : wg.getV()){
            this.nodes.put(i.getKey(), new nodeData((nodeData) i));
        }

        for (node_info i : wg.getV()){

            for(node_info j : wg.getV(i.getKey())){

                if (wg.hasEdge(i.getKey(), j.getKey())){
                    this.connect(i.getKey(), j.getKey(),wg.getEdge(i.getKey(), j.getKey()));
                }
            }
        }
        this.MC = wg.getMC();
        this.edge_size = wg.edgeSize();
        this.node_size = wg.nodeSize();
    }

    public Set<Integer> getNI(int key){
    if(nodes.get(key)==null||this.edges.get(key)==null) return new HashSet<Integer>();

    return this.edges.get(key).keySet();
    }


    @Override
     public boolean equals(java.lang.Object g){
        WGraph_DS temp = (WGraph_DS)g;
        if (this.nodeSize() != temp.nodeSize() || this.getMC() != temp.getMC() || this.edge_size != temp.edge_size){
            return false;
        }
        if(! this.getV().equals(temp.getV())){
            return false;
        }
        if(! this.edges.equals(temp.edges)){
            return false;
        }
        return true;
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

        if(one == null || two == null) {
            return false;
        }

        if(!this.edges.containsKey(node1)){
            return false;
        }

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
        MC++;
        node_size++;

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

        if(node1 == node2) return;

        if (!this.hasEdge(node1, node2)){
            edge_size++;
        }
        if(this.edges.get(node1) == null){

            this.edges.put(node1, new HashMap<Integer, Double>());
        }
        if(this.edges.get(node2) == null){

            this.edges.put(node2, new HashMap<Integer, Double>());
        }
        this.edges.get(node1).put(node2, w) ;
        this.edges.get(node2).put(node1, w) ;

        MC++;
    }

    @Override
    /**
     * this method making a shallow copy for a collection of all nodes that include in graph.
     */
    public Collection<node_info> getV() {

        ArrayList<node_info> ans = new ArrayList<node_info>();
        ans.addAll(this.nodes.values());
        return ans;
    }

    @Override
    /**
     * This method returns a Collection of all the nodes connected to node_id.
     *
     * @param: node_id (int).
     */
    public Collection<node_info> getV(int node_id) {

        Collection<node_info> ans = new ArrayList<>();

        if(this.edges.get(node_id) == null){

            return ans;
        }
        Collection<Integer> neighbors = this.edges.get(node_id).keySet();


        for (int i : neighbors){
            ans.add(this.nodes.get(i));
        }
        return ans;
    }

    @Override
    /**
     * this method remove node by a given node key.
     *
     * @param: key (int).
     */
    public node_info removeNode(int key) {

        node_info temp = this.nodes.get(key);
        if(temp == null) return temp;

        Collection<node_info> neighbors =  this.getV(key);

        for(node_info i :neighbors){
            this.removeEdge(key, i.getKey());
        }

        this.edges.remove(key);
        this.nodes.remove(key);
        node_size--;
        MC++;

        return temp;
    }

    @Override
    /**
     * this method remove edge by given two nodes key's.
     *
     * @param: node1 (int).
     * @param: node2 (int).
     */
    public void removeEdge(int node1, int node2) {

        if(node1 == node2) return;

        if(! this.hasEdge(node1, node2)) return;

        this.edges.get(this.nodes.get(node1).getKey()).remove(node2);
        this.edges.get(this.nodes.get(node2).getKey()).remove(node1);

        edge_size--;
        MC++;
    }

    @Override
    /**
     *this methid return the node size.
     */
    public int nodeSize() {

        return node_size;
    }

    @Override
    /**
     * this method return the edge size.
     */
    public int edgeSize() {

        return this.edge_size;
    }

    @Override
    /**
     * this method return the number of all changes that made in graph.
     */
    public int getMC() {

        return this.MC;
    }


    protected class nodeData implements node_info, Serializable {

        private int key;
        private String info;
        private Double tag;

        /**
         * copy constructor.
         *
         * @param nd
         */
        public nodeData(nodeData nd){

            this.key = nd.getKey();
            this.info = nd.getInfo();
            this.tag = nd.getTag();

        }

        public nodeData(int key){

            this.key = key;
            this.info = "";
            this.tag = -1.0;
        }

        public int compareTo(nodeData node2){
            if(Double.parseDouble(this.getInfo())>Double.parseDouble(node2.getInfo())) return 1;
            if(Double.parseDouble(this.getInfo())<Double.parseDouble(node2.getInfo())) return -1;
            return 0; //if its equal
        }
        public String toString (){
            String str = "key: " + this.getKey();
            str += " info: " + this.getInfo();
            str += " tag: " + this.getTag();

            return str;
        }
    @Override
    /**
     * this method built as an auxiliary function to compare two objects.
     * if the objects are equal - return true.
     * if the objects are not equal - return false.
     */
    public boolean equals(java.lang.Object g){

            nodeData n=(nodeData)g;

            if(this.getKey() != n.getKey() || !this.getInfo().equals(n.getInfo()) || this.getTag() != n.getTag()){
                return false;
            }
          return true;
        }

            @Override
        /**
         * this method return the key of this node.
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
        /**
         * this method set the node info to a given string.
         *
         * @param: string s.
         */
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
        /**
         * this method set the tag value to a given double.
         *
         * @param: double t.
         */
        public void setTag(double t) {

            this.tag = t;

        }
    }
}
