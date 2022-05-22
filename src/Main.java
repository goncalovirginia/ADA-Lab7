
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int[] netm = new int[4];
		
		for (int i = 0; i < 4; i++) {
			netm[i] = Integer.parseInt(in.readLine());
		}
		
		List<Node>[] inAdjacentNodes = new LinkedList[netm[0]+1];
		
		for (int i = 1; i < inAdjacentNodes.length; i++) {
			inAdjacentNodes[i] = new LinkedList<>();
		}
		
		for (int i = 0; i < netm[3]; i++) {
			int[] edge = Arrays.stream(in.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
			inAdjacentNodes[edge[1]].add(new Node(edge[0], edge[2]));
		}
		
		System.out.println(solve(inAdjacentNodes, netm[1], netm[2]));
	}
	
	private static int solve(List<Node>[] inAdjacentNodes, int exit, int time) {
		int mice = 1;
		
		int[] distance = new int[inAdjacentNodes.length];
		
		for (int i = 1; i < inAdjacentNodes.length; i++) {
			distance[i] = Integer.MAX_VALUE;
		}
		
		distance[exit] = 0;
		
		PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(Node::getWeight));
		pq.add(new Node(exit, 0));
		
		while (!pq.isEmpty()) {
			Node current = pq.poll();
			
			for (Node n : inAdjacentNodes[current.getIdentifier()]) {
				int cost = distance[current.getIdentifier()] + n.getWeight();
				
				if (cost <= time && cost < distance[n.getIdentifier()]) {
					distance[n.getIdentifier()] = n.getWeight() + distance[current.getIdentifier()];
					pq.add(new Node(n.getIdentifier(), distance[n.getIdentifier()]));
					mice++;
				}
			}
		}
		
		return mice;
	}
	
	private record Node(int identifier, int weight) {
		
		public int getIdentifier() {
			return identifier;
		}
		
		public int getWeight() {
			return weight;
		}
		
	}
	
}
