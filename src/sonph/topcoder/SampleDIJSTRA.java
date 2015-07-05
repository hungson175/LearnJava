package sonph.topcoder;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * 
 * @author hungson175
 * This solution extracted from Bombman Topcoder TCCC ’04 Round 4 – 500
 */
public class SampleDIJSTRA {
	private static final char SPACE = '.';
	private static final char WALL = '#';
	private static final int INF = 3000;
	public static final int[] ROW_DIR = {-1,0,1,0};
	public static final int[] COL_DIR = {0,1,0,-1};
	public HashMap<String, Integer> minTime = new HashMap<String, Integer>();
	private char[][] maze;
	private Node start;
	private Node last;
	private int m;
	private int n;
	private int nb;
	class Node {
		
		int i;
		int j;
		int nb;
		public Node(int i, int j, int nb) {
			super();
			this.i = i;
			this.j = j;
			this.nb = nb;
		}
		public boolean isExit() {
			return (this.i == last.i) && (this.j == last.j);
		}
		public int getTime() {
			Integer val = minTime.get(this.getRep());
			if ( val == null ) return INF;
			return (int)val;
		}
		private String getRep() {
			return i + "#" + j + "#" + nb;
		}
		
		//next=null if not moveable: outbound, or wall without any bomb left
		public Node move(int d) {
			int ii = i + ROW_DIR[d];
			int jj = j + COL_DIR[d];
			if ( outbound(ii,jj) ) return null;
			if ( maze[ii][jj] == SPACE ) return new Node(ii,jj,nb);
			else if (maze[ii][jj]==WALL ){
				//WALL
				 if ( nb == 0) return null;
				 else return new Node(ii,jj,nb-1);
			} else {
				return null;
			}
		}
		private boolean outbound(int ii, int jj) {
			return (ii<0 || ii>=m) || (jj < 0 || jj >= n);
		}
		public int getMovingCost(int d) {
			Node next = move(d);
			if ( next == null) return INF;
			if ( next.isWall() ) return 3; else return 1;
		}
		
		private boolean isWall() {
			return maze[i][j] == WALL;
		}
		
		public void updateTime(int time) {
			minTime.put(getRep(), time);
		}

	}

	private Node parseChar(char c,String[] maze, int nb) {
		for(int i = 0 ; i < m ; i++)
			for(int j = 0 ; j < n ; j++) {
				if (maze[i].charAt(j) == c ) return new Node(i,j,nb);
			}
		return null;
	}
	
	public int shortestPath(String[] maze, int bombs) {
		this.m = maze.length;
		this.n = maze[0].length();

		this.start = parseChar('B',maze,bombs);
		this.last = parseChar('E',maze,0);

		this.maze = createMaze(maze);
		this.nb = bombs;


		PriorityQueue<Node> q = new PriorityQueue<Node>(251000,new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				return o1.getTime() - o2.getTime();
			}
		});
		
		start.updateTime(0);
		q.add(start);
		while  ( ! q.isEmpty()) {
			Node current = q.remove();
			if ( current.isExit() )  {
				int val = current.getTime();
				if ( val >=  INF ) return -1;
				return val;
			}
			for(int d = 0 ; d < 4 ; d++) {				
				Node next = current.move(d); //next=null if not moveable: outbound, or wall without any bomb left
				if ( next != null ) {
					int usingCurrent = current.getTime() + current.getMovingCost(d);
					if ( usingCurrent < next.getTime() ) {
						next.updateTime(usingCurrent);
						q.add(next);
					}
				}
			}
		}
		return -1;		
	}

	private char[][] createMaze(String[] maze) {
		char[][] b = new char[m][n];
		for(int i = 0 ; i < m ; i ++)
			for(int j = 0 ; j < n ; j++) {
				char c = maze[i].charAt(j);
				if ( c == '#' ) b[i][j] = WALL;
				else b[i][j] = SPACE;
			}
		return b;
	}
}
