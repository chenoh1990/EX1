import java.util.*;
import java.io.Serializable;

public class WGraph_DS implements weighted_graph, Serializable {

    private  int MC = 0;
    private int edge_size = 0;
    private int node_size = 0;
    private HashMap<Integer,nodeData> nodes = new HashMap<Integer, nodeData>();
    private HashMap<Integer,HashMap<Integer, Double>> edges = new HashMap<Integer,HashMap<Integer, Double>>();


    public WGraph_DS(){

    }

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

//    public String toString (){
//
//        String str = "";
//        str +=  "mc: " + this.getMC();
//        str += "/n num of nodes: " + this.node_size;
//        str += "/n num of edges: " + this.edge_size;
//        str += "/n nodes: ";
//
//        for (node_info i : this.getV()){
//
//            nodeData temp = (nodeData) i;
//            str += " /n " + i.toString();
//        }
//        str += " /n edges: ";
//
//        for(node_info i : this.getV()){
//
//        }
//    }
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
    public Collection<node_info> getV() {

        ArrayList<node_info> ans = new ArrayList<node_info>();
        ans.addAll(this.nodes.values());
        return ans;
    }

    @Override
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
    public void removeEdge(int node1, int node2) {

        if(node1 == node2) return;

        if(! this.hasEdge(node1, node2)) return;

        this.edges.get(this.nodes.get(node1).getKey()).remove(node2);
        this.edges.get(this.nodes.get(node2).getKey()).remove(node1);

        edge_size--;
        MC++;
    }

    @Override
    public int nodeSize() {

        return node_size;
    }

    @Override
    public int edgeSize() {

        return this.edge_size;
    }

    @Override
    public int getMC() {

        return this.MC;
    }


    protected class nodeData implements node_info, Serializable {

        private int key;
        private String info;
        private Double tag;


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
    public boolean equals(java.lang.Object g){
            nodeData n=(nodeData)g;
            if(this.getKey() != n.getKey() || !this.getInfo().equals(n.getInfo()) || this.getTag() != n.getTag()){
                return false;
            }
          return true;
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
