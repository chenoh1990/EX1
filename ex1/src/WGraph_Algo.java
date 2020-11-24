import java.util.*;
import java.io.*;
public class WGraph_Algo implements weighted_graph_algorithms{

    private WGraph_DS g;

    /**
     * This method performs a breadth first search on all neighbors of the start vertex
     * and  count the number of vertices connected to the start vertex.
     *
     * @param start
     * @return:
     */
    public int BFS (node_info start){

        for(node_info i :this.g.getV()){
            i.setTag(-1);
        }

        Queue<node_info> Unvisited = new LinkedList<>();
        int num_of_nodes = 1;

        Unvisited.add(start);
        start.setTag(1);

        while(!Unvisited.isEmpty()){

            node_info temp =  Unvisited.poll();

            for (int i :this.g.getNI(temp.getKey())){

                node_info node = this.g.getNode(i);

                    if(node.getTag() == -1) {
                        node.setTag(1);
                        Unvisited.add(node);
                        num_of_nodes++;
                    }
                }
        }

        for(node_info i :this.g.getV()){
            i.setTag(-1);
        }
        return  num_of_nodes;
    }

    /**
     *this method Solves the problem of finding the easiest route from a point on a graph instead of a weighted graph.
     *this method using in BFS algorithm.
     *
      * @param src
     */
public void dijkstra (int src){

    Queue<node_info> Unvisited = new LinkedList<>();
    for(node_info i :this.g.getV()){
        i.setTag(-1);
        i.setInfo("");
    }
    for(node_info i :this.g.getV()) {
        if (i.getKey() != src) {
            i.setTag(Integer.MAX_VALUE);
            i.setInfo("No Path");
        }
        else{
            i.setTag(0);
            i.setInfo(""+i.getKey());
            Unvisited.add(i);
        }
    }
    while(!Unvisited.isEmpty()){
        node_info temp =  Unvisited.poll();
        double Spath_tempdis = temp.getTag();
        System.out.println("tag: "+ this.g.getNode(7).getTag());
        for (int i :this.g.getNI(temp.getKey())){
            node_info node = this.g.getNode(i);
            double Spath_idis = node.getTag();
            double w = this.g.getEdge(node.getKey(),temp.getKey());
            if(Spath_idis > Spath_tempdis+w){
                node.setTag(Spath_tempdis+w);
                node.setInfo(temp.getInfo()+"->"+node.getKey());
                Unvisited.add(node);
            }
        }
    }

    }
    @Override
    /**
     * initialize graph.
     */
    public void init(weighted_graph g) {
      this.g = (WGraph_DS) g;
    }

    @Override
    /**
     * Return the underlying graph of which this class works.
     */
    public weighted_graph getGraph() {
        return this.g;
    }

    @Override
    /**
     * this method makes a deep copy to the graph.
     *
     */
    public weighted_graph copy() {
        return new WGraph_DS((WGraph_DS) g);
    }

    @Override
    /**
     * this method check if and only if there have valid path between all nodes to each other.
     * if a valid path exist - return true.
     * if a valid path not exist - return false.
     */
    public boolean isConnected() {

        node_info temp = null;
        for (node_info i : this.g.getV()){
            temp = i;
            if(temp != null) break;
        }

        if (g.nodeSize() == 0) return true;
        return g.getV().size() == BFS(temp);
    }

    @Override
    /**
     *  returns the length of the shortest path between src to dest.
     *
     * @param: src (int)
     * @param: dest (int).
     */
    public double shortestPathDist(int src, int dest) {
        if (this.g.getNode(src) == null || this.g.getNode(dest) == null ){
            return -1;
        }
        System.out.println("*******************************************************************************");

        this.dijkstra(src);
        double ans = this.g.getNode(dest).getTag();
        for(node_info i :this.g.getV()){
            i.setTag(-1);
            i.setInfo("");
        }
        System.out.println(ans +" path+ " + this.shortestPath(src,dest).size());
        System.out.println("*******************************************************************************");
        return ans;
    }

    @Override
    /**
     * method return list of all nodes  of the shortest path between a given 2 nodes id. (src & dest)
     * this method using dijkstra algorithm.
     *
     * @param: src (int).
     * @param: dest (int).
     */
    public List<node_info> shortestPath(int src, int dest) {

        List<node_info> ans =new ArrayList<>();
        node_info start = this.g.getNode(src);
        node_info end = this.g.getNode(dest);
        if (start == null || end == null ){
            return ans;
        }
        this.dijkstra(src);
      if(end.getInfo().equals("No Path")) return ans;
        String [] parseInfo=end.getInfo().split("->");
        for(int i=0;i<parseInfo.length;i++){
            node_info vertex=this.g.getNode(Integer.parseInt(parseInfo[i]));
            ans.add(vertex);
        }
        for(node_info i :this.g.getV()){
            i.setTag(-1);
            i.setInfo("");
        }
        return ans;
    }


    @Override
    /**
     * this method save graph to given string file.
     *
     * @param: file.
     *
     */
    public boolean save(String file){
         try {
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(this.g);

            }
            catch (FileNotFoundException error){
                return false;
            }

            catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }


    @Override
    /**
     * this method load graph by given a string .
     *
     * @param: file.
     *
     */
    public boolean load(String file) {

        try {
            FileInputStream fis = new   FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            this.init((weighted_graph) ois.readObject());

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;

        }
        return true;
    }
}
