
import java.util.*; 
/**
 * Instances of this class maintain a vertex name and a list of adjacent 
 * vertexes or neighbors and supporting operations. 
 *
 * @author Qiying
 */
public class GraphNode extends Object implements Comparable<GraphNode>
{
	private String name; 
	private boolean existSpycam;
	private List<Neighbor> neighbors; 
	public static int NOT_NEIGHBOR; 

	/**
	 * Represents a valid location in the game graph. 
	 *
	 * @param name - the label that uniquely identifies this graphNode. 
	 */
	public GraphNode (String name)
	{
		this.name = name;
		existSpycam = false;
		neighbors = new ArrayList<Neighbor>();
		NOT_NEIGHBOR = Integer.MAX_VALUE;
	}

	/**
	 * Return the name of this GraphNode 
	 *
	 * @return name of the node 
	 */
	public String getNodeName() 
	{
		return name; 
	}

	/**
	 * A getter for the spycam 
	 *
	 * @return true if the GraphNode has a spycam 
	 */
	public boolean getSpycam()
	{
		return existSpycam; 
	}

	/**
	 * A setter for the spycam 
	 *
	 * @param cam - indicates whether the node now has a spycam 
	 */
	public void setSpycam(boolean cam)
	{
		existSpycam = cam;
	}

	/**
	 * Returns a list of the neighbors of this GraphNode instance. 
	 *
	 * @return a list of neighbors of this GraphNode 
	 */
	public List<Neighbor> getNeighbors() 
	{
		return neighbors; 
	}

	/**
	 * Returns true if this node name is a neighbor of current node. 
	 *
	 * @param neighborName - name of the neighbor to look for 
	 * @return true if the node is an adjacent neighbor 
	 */
	public boolean isNeighbor(String neighborName)
	{
		//create a new iterator 
		Iterator <String> itr = this.getNeighborNames(); 
		//iterate through list of neighbors' names 
		while(itr.hasNext())
		{
			String curr = itr.next(); 
			if(curr.equals(neighborName))
				return true; 	
		}
		return false; 
	}

	/**
	 * Returns an iterator that can be used to find neighbors of this GraphNode
	 *
	 * @return an iterator of String node labels 
	 */
	public Iterator<String> getNeighborNames() 
	{
		List<String> neighborsName = new ArrayList<String>();
		for(int i=0; i<neighbors.size(); i++)
		{
			neighborsName.add(i, neighbors.get(i).getNeighborNode().
					getNodeName());
		}
		return neighborsName.iterator(); 
	}

	/**
	 * Adding a neighbor to the list while maintaining sorted order of 
	 * neighbors by neighbor's name 
	 *
	 * @param neighbor - an adjacent node (a neighbor) 
	 * @param cost - the cost to move to that node from this node 
	 */
	public void addNeighbor(GraphNode neighbor, int cost)
	{
		Neighbor neighborNode = new Neighbor(cost, neighbor);
		int pos = 0;
		int size = neighbors.size();
		// determine the correct place to add the graphnode
		for(int i=0; i<size; i++)
		{
			if(neighbors.get(i).compareTo(neighborNode)<0)
				pos++;
		}
		neighbors.add(pos, neighborNode);
	}

	/**
	 * Returns the cost to the Neighbor whose name is given 
	 *
	 * @param neighborName - name of potential neighbor 
	 * @return cost to neighborName
	 * @throws NotNeighborException - if neighborName is not a neighbor 
	 */
	public int getCostTo(String neighborName) throws NotNeighborException
	{
		//throw exception if necessary 
		if (!isNeighbor(neighborName))
			throw new NotNeighborException();
		int pos = 0;
		//check with all of the neighbors to find the Neighbor with name given
		while(!neighbors.get(pos).getNeighborNode().getNodeName().
				equalsIgnoreCase(neighborName))
		{
			pos++;
		}
		return neighbors.get(pos).getCost();
	}

	/**
	 * Returns the GraphNode that's associated with the name that is a neighbor
	 *
	 * @param name - name of potential neighbor 
	 * @return the GraphNode associated with name that is a neighbor of the 
	 * current node 
	 * @throws NotNeighborException - if name is not neighbor of the GraphNode
	 */
	public GraphNode getNeighbor(String name) throws NotNeighborException
	{
		// throw exception if necessary 
		if (!isNeighbor(name))
			throw new NotNeighborException();
		int pos = 0;
		//check with all of the neighbors to find the Neighbor with name given
		while(!neighbors.get(pos).getNeighborNode().getNodeName().
				equalsIgnoreCase(name))
		{
			pos++;
		}
		return neighbors.get(pos).getNeighborNode();
	}
	/**
	 * Print a list of neighbors of this GraphNode and the cost of the edge to 
	 * them.
	 */
	public void displayCostToEachNeighbor()
	{
		printNeighborNames();
	}

	/**
	 * Returns the results of comparing this node's name to the other node's 
	 * name. 
	 *
	 * @param otherNode - another node to compare names with this node 
	 * @return the result of compareTo on the node names 
	 */
	public int compareTo(GraphNode otherNode)
	{
		if(name.compareTo(otherNode.getNodeName())<0)
			return -1;
		if(name.compareTo(otherNode.getNodeName())>0)
			return 1;
		return 0;
	}

	/**
	 * Returns a String representation of this GraphNode. 
	 *
	 * @return name of the node 
	 */
	public String toString()
	{
		return name; 
	}

	/**
	 * Displays the node name followed by a list of neighbors to this node. 
	 */
	public void printNeighborNames() 
	{
		for(int i=0; i<neighbors.size(); i++)
		{
			Neighbor curr = neighbors.get(i);
			String name = curr.getNeighborNode().getNodeName();
			int cost = curr.getCost();
			System.out.println(cost+" "+name);
		}
	}
}
