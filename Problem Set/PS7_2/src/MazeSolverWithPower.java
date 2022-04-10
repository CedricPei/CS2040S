import java.util.ArrayDeque;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;

public class MazeSolverWithPower implements IMazeSolverWithPower {
	private static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3;
	private static final int[][] DELTAS = new int[][] {
		{ -1, 0 }, // North
		{ 1, 0 }, // South
		{ 0, 1 }, // East
		{ 0, -1 } // West
	};

	private Maze maze;
	private int eR, eC;
	private int sp = 0;
	private boolean[][] vis;
	private boolean[][][] sv;
	private List<Integer> path;
	private Helper[][][] parent;

	//Helper Data Structure
	private static class Helper {
		private final int r;
		private final int c;
		private final int sp;

		public Helper(int r, int c, int sp) {
			this.r = r;
			this.c = c;
			this.sp = sp;
		}

		public int getR() {
			return this.r;
		}

		public int getC() {
			return this.c;
		}

		public int getSp() {
			return this.sp;
		}
	}

	public MazeSolverWithPower() {
		this.maze = null;
	}

	@Override
	public void initialize(Maze maze) {
		this.maze = maze;
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		initial(startRow, startCol, endRow, endCol);
		return solve(startRow, startCol);
	}

	@Override
	public Integer numReachable(int k) throws Exception {
		if (k < 0)                   throw new Exception("Invalid");
		if (k >= path.size() - 1)    return 0;
		else                         return path.get(k);
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow,
							  int endCol, int superpowers) throws Exception {
		sp = superpowers;
		initial(startRow, startCol, endRow, endCol);
		return solve(startRow, startCol);
	}

	public void initial(int startRow, int startCol, int endRow, int endCol) throws Exception {
		if (maze == null)   throw new Exception("Not Initializing");
		if ((startRow < 0) || (startCol < 0) || (startRow >= maze.getRows()) || (startCol >= maze.getColumns()) ||
				(endRow < 0) || (endCol < 0) || (endRow >= maze.getRows()) || (endCol >= maze.getColumns())) {
			throw new Exception("Invalid");
		}

		sv = new boolean[maze.getRows()][maze.getColumns()][sp + 1];
		parent = new Helper[maze.getRows()][maze.getColumns()][sp + 1];
		vis = new boolean[maze.getRows()][maze.getColumns()];
		path = new ArrayList<>();

		for (int i = 0; i < maze.getRows(); ++i) {
			for (int j = 0; j < maze.getColumns(); ++j) {
				for (int k = 0; k < sp + 1; k++) {
					vis[i][j] = false;
					sv[i][j][k] = false;
					maze.getRoom(i, j).onPath = false;
					parent[i][j][k] = null;
				}
			}
		}
		eR = endRow;
		eC = endCol;
	}

	private Integer solve(int R, int C) {
		int stp = 0;
		int ftp = 0;   //Final Step
		int fp = 0;    //Final Super Power
		boolean status = false;
		Queue<Helper> q = new ArrayDeque<>();

		sv[R][C][sp] = true;
		maze.getRoom(R, C).onPath = true;
		vis[R][C] = true;
		path.add(1);

		q.add(new Helper(R, C, sp));

		while (!q.isEmpty()) {
			Queue<Helper> Q = new ArrayDeque<>();
			path.add(0);

			for (Helper cube : q) {
				int cR = cube.getR();
				int cC = cube.getC();
				int spw = cube.getSp();

				if (!vis[cR][cC]) {
					vis[cR][cC] = true;
					path.set(stp, path.get(stp) + 1);
				}

				if (cR == eR && cC == eC && !status) {
					ftp = stp;
					fp = spw;
					status = true;
				}

				for (int dir = 0; dir < 4; dir++) {
					if (mov(cR, cC, dir)) {
						int r = cR + DELTAS[dir][0];
						int c = cC + DELTAS[dir][1];

						if (!sv[r][c][spw]) {
							sv[r][c][spw] = true;
							parent[r][c][spw] = cube;
							Q.add(new Helper(r, c, spw));
						}
					} else {
						if (brk(cR, cC, dir, spw)) {
							int r = cR + DELTAS[dir][0];
							int c = cC + DELTAS[dir][1];
							if (!sv[r][c][spw-1]) {
								sv[r][c][spw-1] = true;
								parent[r][c][spw-1] = cube;
								Q.add(new Helper(r, c, spw - 1));
							}
						}
					}
				}
			}
			stp++;
			q = Q;
		}
		maze.getRoom(eR, eC).onPath = status;
		Helper prt = parent[eR][eC][fp];

		while (prt != null) {
			maze.getRoom(prt.getR(), prt.getC()).onPath = true;
			prt = parent[prt.getR()][prt.getC()][prt.getSp()];
		}
		return status ? ftp : null;
	}

	private boolean mov(int row, int col, int dir) {
		if (row + DELTAS[dir][0] < 0 || row + DELTAS[dir][0] >= maze.getRows())    return false;
		if (col + DELTAS[dir][1] < 0 || col + DELTAS[dir][1] >= maze.getColumns()) return false;

		switch (dir) {
			case NORTH: return !maze.getRoom(row, col).hasNorthWall();
			case SOUTH: return !maze.getRoom(row, col).hasSouthWall();
			case EAST:  return !maze.getRoom(row, col).hasEastWall();
			case WEST:  return !maze.getRoom(row, col).hasWestWall();
		}
		return false;
	}

	private boolean brk(int r, int c, int dir, int sp) {
		if (r + DELTAS[dir][0] < 0 || r + DELTAS[dir][0] >= maze.getRows())    return false;
		if (c + DELTAS[dir][1] < 0 || c + DELTAS[dir][1] >= maze.getColumns()) return false;

		switch (dir) {
			case NORTH: return maze.getRoom(r, c).hasNorthWall() && sp > 0;
			case SOUTH: return maze.getRoom(r, c).hasSouthWall() && sp > 0;
			case EAST:  return maze.getRoom(r, c).hasEastWall() && sp > 0;
			case WEST:  return maze.getRoom(r, c).hasWestWall() && sp > 0;
		}
		return false;
	}

	public static void main(String[] args) {
		try {
			Maze maze = Maze.readMaze("maze-sample.txt");
			IMazeSolverWithPower solver = new MazeSolverWithPower();
			solver.initialize(maze);

			System.out.println(solver.pathSearch(0, 0, 4, 1, 2));
			MazePrinter.printMaze(maze);

			for (int i = 0; i <= 9; ++i) {
				System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
