import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MazeSolver implements IMazeSolver {
	private static final int[][] DELTAS = new int[][] {
		{ -1, 0 }, // North
		{ 1, 0 }, // South
		{ 0, 1 }, // East
		{ 0, -1 } // West
	};
	private Maze maze;
	private boolean status;
	private boolean[][] vis;
	private List<Integer> path;

	public MazeSolver() {
		this.status = false;
		this.maze = null;
	}

	@Override
	public void initialize(Maze maze) {
		this.maze = maze;
		this.vis = new boolean[maze.getRows()][maze.getColumns()];
	}

	//Helper Data Structure
	static class Helper {
		int row;
		int col;
		int dis;
		Helper parent;

		public Helper(int row, int col, int dis) {
			this.row = row;
			this.col = col;
			this.dis = dis;
		}

		public void set(Helper node) {
			this.parent = node;
		}

		public Helper get() {
			return this.parent;
		}
	}

	//BFS
	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		//Exception Handling
		if (maze == null)  throw new Exception("Not Initialize");
		if ((startRow < 0) || (startCol < 0) || (startRow >= maze.getRows()) || (startCol >= maze.getColumns()) ||
				(endRow < 0) || (endCol < 0) || (endRow >= maze.getRows()) || (endCol >= maze.getColumns())) {
			throw new IllegalArgumentException("Invalid");
		}

		status = false;
		path = new ArrayList<>();
		int ans = 0;
		int cnt = 0;

		for (int i = 0; i < maze.getRows(); i++) {
			for (int j = 0; j < maze.getColumns(); j++) {
				vis[i][j] = false;
				maze.getRoom(i, j).onPath = false;
			}
		}
		vis[startRow][startCol] = true;
		maze.getRoom(startRow, startCol).onPath = true;

		Queue<Helper> q = new LinkedList<>();
		//Starting Point
		Helper str = new Helper(startRow, startCol, 0);
		str.set(null);
		q.add(str);
		//Tracing
		Helper trc = null;

		while (!q.isEmpty()) {
			path.add(cnt, q.size());

			Queue<Helper> Q = new LinkedList<>();
			for (Helper node : q) {
				int r = node.row;
				int c = node.col;
				if (r == endRow && c == endCol) {
					status = true;
					ans = node.dis;
					trc = node;
				}
				vis[r][c] = true;

				//Four Direction
				for (int i = 0; i < 4; i++) {
					if (!maze.getRoom(r, c).hasNorthWall() && !vis[r + DELTAS[i][0]][c + DELTAS[i][1]]) {
						Helper d = new Helper(r + DELTAS[i][0], c + DELTAS[i][1], node.dis +1);
						Q.add(d);
						vis[d.row][d.col] = true;
						d.set(node);
					}
				}
			}
			q = Q;
			cnt++;
		}

		if (status) {
			while (trc.get() != null) {
				maze.getRoom(trc.row, trc.col).onPath = true;
				trc = trc.get();
			}
			return ans;
		} else return null;
	}

	@Override
	public Integer numReachable(int k) throws Exception {
		if (k < 0)               throw new Exception("Invalid");
		if (k > path.size() - 1) return 0;
		else                     return path.get(k);
	}

	public static void main(String[] args) {
		try {
			Maze maze = Maze.readMaze("maze-sample.txt");
			IMazeSolver solver = new MazeSolver();
			solver.initialize(maze);

			System.out.println(solver.pathSearch(0, 0, 2, 3));
			MazePrinter.printMaze(maze);

			for (int i = 0; i <= 9; ++i) {
				System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
