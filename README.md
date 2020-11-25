EX0 

:author 

chen ohayon 

introduction:1.

In this task we are required to upgrade the existing data structure we have built. 
 
We will refer to the graph methods by weight, to the algorithms we have built, we will add the option of saving and uploading from a file and we will characterize the properties of the .algorithms accordingly 

Inner class nodeData methods: 

this class represend a vertex in wheited graph.
each node include the following fields:

1. key - represent the id of this node.
2.tag - represent the tag of this node (help us to "coloring" the vertex.
3.info - represent the information of this node.

methods:

1.getkey(): return the key of this node. 

2.getInfo(): return the information of this node. 

3..getTag(): return the tag of this node. 

4.setInfo(String str): get string and set it as a info of this node. 

6.setTag(Double t): set t to be the tag of this node. 

7.equals(Object obj):  check if this node equal by a given node.  

class WGraph_DS:

this raph have the following fields:

MC - represent the number of the changes that made in graph.
edge_size - represent the number of the edges in this graph.
node_size - represent the number of this nodes in this graph.
nodes - a collaction of nodes that connected to this graph.
edges - a collaction of edges in this graph.

methods:

1.getNode(): return the node by a given key. 

2.hasEdge(): return true  <-> if there has edge between  two nodes. 

3.addNode(): add new node.

4.connect(): connect between 2 edges.

5.getV(): return collaction of all nodes in this graph.

6.getV():  return collaction of all neihbors in this graph by a given id.

7.renoveNode(): remove a node from graph by a given key.

8.removeEdge(): remove edge from this graph by a given 2 nodes keys.

9.nodeSize(): return the number of the nodes that connected to this graph.

10.edgeSize(): return the number of edges in graph.

11.getMC: return the number of changes that made in graph.

12.equals(): equal function.

13.getNI(): return the list of all neibhors of a given node.


WGraph_Algo class:

methods: 

1.init(): init the graph. 

2.getGraph(): return return the graph. 

3.copy(): return a deep copy of the graph. 

4.isConnected(): check if this graph is connected. 

5.shortestPath(): return a collaction of the shortest path between two nodes, if there no path between two nodes- return an empty collaction. 

6.shortestPathDist(): return the lowest cost path between two nodes, if there no path between two nodes- return -1. 

7.save(): save this graph by a given string that represent the file name. 

8.load(): load the graph to this graph algorithm . 
