public class GameTree {

    public enum Player {ONE, TWO}
    public enum Game {Regular, Misere, NoTie, Arbitrary}
    final static int bsize = 3;
    final static int btotal = bsize * bsize;
    final static char EMPTYCHAR = '_';
    final int[] valArray = {1, -2, 1, -3, -2, 2, -2, -1, 1};

    static class TreeNode {
        public String name = "";
        public TreeNode[] children = null;
        public int numChildren = 0;
        public int value = Integer.MIN_VALUE;
        public boolean leaf = false;

        TreeNode() {}

        TreeNode(String s, boolean l) {
            name = s;
            leaf = l;
            children = new TreeNode[btotal];
            for (int i = 0; i < btotal; i++) {
                children[i] = null;
            }
            numChildren = 0;
        }
    }

    public TreeNode root = null;

    private Player other(Player p) {
        if (p == Player.ONE) return Player.TWO;
        else return Player.ONE;
    }

    public void drawBoard(String s) {
        System.out.println("-------");
        for (int j = 0; j < bsize; j++) {
            System.out.print("|");
            for (int i = 0; i < bsize; i++) {
                char c = s.charAt(i + 3 * j);
                if (c != EMPTYCHAR)
                    System.out.print(c);
                else System.out.print(" ");
                System.out.print("|");
            }
            System.out.println();
            System.out.println("-------");
        }
    }

    public void readTree(String fName) {
        try {
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(fName));
            root = readTree(reader);
        } catch (java.io.IOException e) {
            System.out.println("Error reading file: " + e);
        }
    }

    private TreeNode readTree(java.io.BufferedReader reader) throws java.io.IOException {
        String s = reader.readLine();
        if (s == null) {
            throw new java.io.IOException("File ended too soon.");
        }
        TreeNode node = new TreeNode();
        node.numChildren = Integer.parseInt(s.substring(0, 1));
        node.children = new TreeNode[node.numChildren];
        node.leaf = (s.charAt(1) == '1');
        node.value = Integer.MIN_VALUE;
        if (node.leaf) {
            char v = s.charAt(2);
            node.value = Character.getNumericValue(v);
            node.value--;
        }
        node.name = s.substring(3);

        for (int i = 0; i < node.numChildren; i++) {
            node.children[i] = readTree(reader);
        }
        return node;
    }

    //Answer Below
    /*
    int bestScore(TreeNode node, Player player) {
        if (node == null)    return Integer.MIN_VALUE;
        if (node.leaf)       return node.value;
        else {
            if (player == Player.ONE) {
                int maxValue = Integer.MIN_VALUE;
                for (TreeNode child : node.children) {
                    int value = bestScore(child, this.other(player));
                    if (value > maxValue)        maxValue = value;
                }
                return maxValue;
            } else {
                int minValue = Integer.MAX_VALUE;
                for (TreeNode child : node.children) {
                    int value = bestScore(child, this.other(player));
                    if (value < minValue)        minValue = value;
                }
                return minValue;
            }
        }
    }

    int findValue() {
        int a = 0;
        int b = 0;

        for (char c : root.name.toCharArray()) {
            if (c == 'X') a += 1;
            if (c == 'O') b += 1;
        }

        Player nextPlayer = a <= b ? Plasyer.ONE : Player.TWO;
        return bestScore(root, nextPlayer);
    }
     */

    public int findValue() {
        if (root == null)    return Integer.MIN_VALUE;
        int tmp = find(this.root, Player.ONE);
        this.root.value = tmp;
        return tmp;
    }

    public int find(TreeNode node, Player p) {
        if (node.leaf)       return node.value;
        for (int i = 0; i < node.children.length; i++) {
            node.children[i].value = find(node.children[i], other(p));
        }
        return bestScore(node.children, p);
    }

    public int bestScore(TreeNode[] arr, Player player){
        if (player == Player.ONE) {
            int maximum = Integer.MIN_VALUE;
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].value > maximum)     maximum = arr[i].value;
            }
            return maximum;
        } else {
            int minimum = Integer.MAX_VALUE;
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].value < minimum)     minimum = arr[i].value;
            }
            return minimum;
        }
    }

    public static void main(String[] args) {
        GameTree tree = new GameTree();
        tree.readTree("games/tictac_9_empty.txt");
        System.out.println(tree.findValue());
    }

}
