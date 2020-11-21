import java.util.*;
import java.io.*;
public class WGraph_Algo implements weighted_graph_algorithms{

    private WGraph_DS g;

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

    @Override
    public void init(weighted_graph g) {
      this.g = (WGraph_DS) g;
    }

    @Override
    public weighted_graph getGraph() {
        return this.g;
    }

    @Override
    public weighted_graph copy() {
        return new WGraph_DS((WGraph_DS) g);
    }

    @Override
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
    public double shortestPathDist(int src, int dest) {
        if (this.g.getNode(src) == null || this.g.getNode(dest) == null ){
            return -1;
        }

        Queue<node_info> Unvisited = new LinkedList<>();

        for(node_info i :this.g.getV()) {

            if (i.getKey() != src) {
                i.setInfo("" + Integer.MAX_VALUE);
                Unvisited.add(i);
            }
            else{
                i.setInfo("" + 0);
                Unvisited.add(i);
            }
        }
        while(!Unvisited.isEmpty()){

            node_info temp =  Unvisited.poll();
            double Spath_temp = Double.parseDouble(temp.getInfo());

            for (int i :this.g.getNI(temp.getKey())){

                node_info node = this.g.getNode(i);

                double Spath_i = Double.parseDouble(node.getInfo());
                double w = this.g.getEdge(node.getKey(),temp.getKey())+Spath_temp;

                    if(Spath_i > w){

                        node.setInfo(""+w);
                    }
            }
        }
        double ans = Double.parseDouble(this.g.getNode(dest).getInfo());

        for(node_info i :this.g.getV()){
            i.setTag(-1);
            i.setInfo("");
        }
        return ans;
    }

    @Override
    public List<node_info> shortestPath(int src, int dest) {

        List<node_info> ans =new ArrayList<>();
        node_info start = this.g.getNode(src);
        node_info end = this.g.getNode(dest);
        if (start == null || end == null ){
            return ans;
        }

        Queue<node_info> Unvisited = new LinkedList<>();

        for(node_info i :this.g.getV()) {

            if (i.getKey() != src) {
                i.setTag(Integer.MAX_VALUE);
                i.setInfo("No Path");
                Unvisited.add(i);
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
            for (int i :this.g.getNI(temp.getKey())){
                node_info node = this.g.getNode(i);
                double Spath_idis = node.getTag();
                double w = this.g.getEdge(node.getKey(),temp.getKey());

                if(Spath_idis > Spath_tempdis+w){
                    node.setTag(Spath_tempdis+w);
                    node.setInfo(temp.getInfo()+"->"+node.getKey());
                }
            }
        }
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
    public boolean save(String file){
         try {
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);

                oos.write(this.g.nodeSize());
                oos.write(this.g.edgeSize());
                oos.write(this.g.getMC());
                oos.writeObject(this.g.getV());

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
