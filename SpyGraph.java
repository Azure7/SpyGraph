
import java.util.*;
import java.lang.*;

/**
 * Stores all vertexes as a list of GraphNodes. Provides necessary graph 
 * operations as need by the SpyGame class.
 * 
 * @author Qiying
 *
 */
public class SpyGraph extends Object implements Iterable<GraphNode> 
{
	/** DO NOT EDIT -- USE THIS LIST TO STORE ALL GRAPHNODES */
	private List<GraphNode> vlist;

	/**
	 * Initializes an empty list of GraphNode objects
	 */
	public SpyGraph()
	{
		this.vlist = new ArrayList<GraphNode> (); 
	}

	/**
	 * Add a vertex with this label to the list of vertexes.
	 * No duplicate vertex names are allowed.
	 * @param name The name of the new GraphNode to create and add to the list.
	 */
	public void addGraphNode(String name)
	{
		if(name==null)
			return; 
		GraphNode newNode = new GraphNode (name);
		vlist.add(newNode);
	}

	/**
	 * Adds v2 as a neighbor of v1 and adds v1 as a neighbor of v2.
	 * Also sets the cost for each neighbor pair.
	 *   
	 * @param v1name The name of the first vertex of this edge
	 * @param v2name The name of second vertex of this edge
	 * @param cost The cost of traveling to this edge
	 * @throws IllegalArgumentException if the names are the same
	 */
	public void addEdge(String v1name, String v2name, int cost) 
			throws IllegalArgumentException
	{
		//throw exception if names are the same 
		if(v1name.equals(v2name))
			throw new IllegalArgumentException(); 
		GraphNode vertex1 = this.getNodeFromName(v1name); 
		GraphNode vertex2 = this.getNodeFromName(v2name); 
		vertex1.addNeighbor(vertex2, cost);
		vertex2.addNeighbor(vertex1, cost);
	}

	/**
	 * Return an iterator through all nodes in the SpyGraph
	 * @return iterator through all nodes in alphabetical order.
	 */
	public Iterator<GraphNode> iterator() 
	{
		return vlist.iterator();
	}

	/**
	 * Return Breadth First Search list of nodes on path 
	 * from one Node to another.
	 * @param start First node in BFS traversal
	 * @param end Last node (match node) in BFS traversal
	 * @return The BFS traversal from start to end node.
	 */
	public List<Neighbor> BFS( String start, String end ) 
	{
		GraphNode startNode = this.getNodeFromName(start);
		GraphNode endNode = this.getNodeFromName(end);

		boolean isEnd = false;
		if(startNode.equals(endNode))
			isEnd = true;

		//a list to store visited GraphNodes
		List<GraphNode> visited = new ArrayList<GraphNode>();
		//a list to put temporary date
		ArrayList<Neighbor> queue = new ArrayList<Neighbor>();    
		//a list to store visited Neighbors
		ArrayList<Neighbor> result = new ArrayList<Neighbor>();
		//a list to store the predecessor for each node
		ArrayList<Neighbor> ancestor = new ArrayList<Neighbor>();

		//update the first step
		int a = 0;
		GraphNode curr = startNode;
		Neighbor currNeighbor = null;
		visited.add(curr);
		result.add(new Neighbor(0, startNode));
		ancestor.add(visited.indexOf(curr), null);
		List<Neighbor> nList = curr.getNeighbors();

		while(!isEnd)
		{
			//for each neighbor of the current node, check if it was already 
			// visited
			for(Neighbor n: nList)
			{
				boolean existed = false;
				if(visited.contains(n.getNeighborNode()))
					existed = true;
				for(int i=0; i<queue.size(); i++)
				{
					if(queue.get(i).getNeighborNode().equals
							(n.getNeighborNode()))
						existed = true;
				}   	 
				//if not, add them to the queue and update their predecessor
				if(existed == false)
				{
					a++;
					queue.add(n);
					ancestor.add(a, currNeighbor);
				}
			}
			//update the current node
			if(!queue.isEmpty())
			{
				currNeighbor = queue.remove(0);
				curr = currNeighbor.getNeighborNode();
				visited.add(curr);
				result.add(currNeighbor);
				nList = curr.getNeighbors();   	 
			}
			//ending the loop
			if(curr.equals(endNode))
				isEnd = true;
		}

		//tracing back to find the Neighbors we need
		List<Neighbor> answer = new ArrayList<Neighbor>();
		Neighbor trace = result.get(result.size()-1);
		while(trace!=null)
		{
			int idx = result.indexOf(trace);
			trace = ancestor.get(idx);
			answer.add(0, trace);
		}
		if(!answer.isEmpty())
			answer.remove(0);
		answer.add(result.get(result.size()-1));

		return answer;
	}

	/**
	 * @param name Name corresponding to node to be returned
	 * @return GraphNode associated with name, null if no such node exists
	 */
	public GraphNode getNodeFromName(String name)
	{
		for ( GraphNode n : vlist ) {
			if (n.getNodeName().equalsIgnoreCase(name))
				return n;
		}
		return null;
	}

	/**
	 * Return Depth First Search list of nodes on path 
	 * from one Node to another.
	 * @param start First node in DFS traversal
	 * @param end Last node (match node) in DFS traversal
	 * @return The DFS traversal from start to end node.
	 */
	public List<Neighbor> DFS(String start, String end) 
	{
		//a list to store visited GraphNodes
		List<GraphNode> visited = new ArrayList<GraphNode>();
		//a list that traces the nodes, will only contain the nodes we need in 
		// the end
		Stack<Neighbor> stack = new Stack<Neighbor>();

		GraphNode startNode = this.getNodeFromName(start);
		GraphNode endNode = this.getNodeFromName(end);
		boolean isEnd = false;
		if(startNode.equals(endNode))
			isEnd = true;

		//a list to store the neighbors of startNode
		List<Neighbor> startList = startNode.getNeighbors();
		//count how many of nodes in the startList has been visited
		int startNeighbor = 0;
		//update the first step
		GraphNode curr = startNode;
		visited.add(curr);
		while(!isEnd)
		{
			List<Neighbor> nList = curr.getNeighbors();
			int a = 0;
			boolean breaked = false;
			//loop to find an unvisited neighbor
			while(a<nList.size())
			{
				//update the current node to this neighbor
				curr = nList.get(a).getNeighborNode();
				if(!visited.contains(curr))
				{
					//if found the neighbor, break out the loop
					breaked = true;
					break;
				}
				a++;
			}

			//if found the unvisited neighbor, add the neighbor to stack and 
			// visited
			if(breaked){
				stack.push(nList.get(a));
				visited.add(curr);
				//update the startNeighbor
				if(startList.contains(nList.get(a)))
					startNeighbor ++;
			}
			//if did not find the unvisited neighbor, move back one step
			else
			{
				stack.pop();
				//a special case: if move back to the startNode, add the next 
				// unvisited neighbor of startNode to the stack and set it as 
				// current node
				if(stack.isEmpty())
					stack.push(startList.get(startNeighbor));
				curr = stack.peek().getNeighborNode();
			}
			//ending the loop
			if(curr.equals(endNode))
				isEnd = true;
		}
		return stack;
	}

	/**
	 * OPTIONAL: Students are not required to implement Dijkstra's ALGORITHM
	 *
	 * Return Dijkstra's shortest path list of nodes on path 
	 * from one Node to another.
	 * @param start First node in path
	 * @param end Last node (match node) in path
	 * @return The shortest cost path from start to end node.
	 */
	public List<Neighbor> Dijkstra(String start, String end)
	{
		return new ArrayList<Neighbor>();
	}

	/**
	 * DO NOT EDIT THIS METHOD 
	 * @return a random node from this graph
	 */
	public GraphNode getRandomNode() 
	{
		if (vlist.size() <= 0 ) {
			System.out.println
			("Must have nodes in the graph before randomly choosing one.");
			return null;
		}
		int randomNodeIndex = Game.RNG.nextInt(vlist.size());
		return vlist.get(randomNodeIndex);
	}
}
