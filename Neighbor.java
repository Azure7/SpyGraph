
import java.util.*; 
import java.lang.*;
/**
 * Instances of this class represent an edge between two nodes, which stores 
 * the neighbor node and the cost to travel from this node to the neighbor node 
 *
 * @author Qiying
 */
public class Neighbor extends Object implements Comparable<Neighbor>
{
	private int cost; 
	private GraphNode neighbor;

	/**
	 * A neighbor is added to an existing GraphNode by creating an instance of 
	 * Neighbor that stores the neighbor and the cost to reach that neighbor. 
	 *
	 * @param cost - the cost to reach its neighbor
	 * @param neighbor - the neighbor node being reached by this edge
	 */
	public Neighbor(int cost, GraphNode neighbor)
	{
		this.cost = cost; 
		this.neighbor = neighbor; 
	}

	/**
	 * returns the cost of traveling this edge to get to the Neighbor at the 
	 * other end of this edge. 
	 *
	 * @return the cost of the edge to get to this neighbor 
	 */
	public int getCost()
	{
		return this.cost; 
	}

	/**
	 * Returns the Neighbor node that is at the other end of this node's edge
	 *
	 * @return the neighbor node itself 
	 */
	public GraphNode getNeighborNode()
	{
		return this.neighbor; 
	}

	/**
	 * Compares the node names of this node and the otherNode. Returns the 
	 * results of comparing this node's name to the otherNode's name. 
	 *
	 * @param otherNode - neighbor to be compared 
	 * @return compareTo the node names of two neighbors 
	 */
	public int compareTo(Neighbor otherNode)
	{
		return neighbor.compareTo(otherNode.getNeighborNode()); 
	}

	/**
	 * Returns a String representation of this Neighbor.
	 *
	 * @return a String with the cost and destination node 
	 */
	public String toString()
	{
		return "--"+cost+"--> "+neighbor.getNodeName(); 
	}
}
