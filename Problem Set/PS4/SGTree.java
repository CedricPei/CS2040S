public class SGTree {
    enum Child {LEFT, RIGHT}
    public static int index;

    public static class TreeNode {
        int key;
        public TreeNode left = null;
        public TreeNode right = null;

        TreeNode(int k) {
            key = k;
        }
    }
    public TreeNode root = null;

    //Answer Below
    public int countNodes(TreeNode node, Child child) {
        if (child == Child.LEFT) {
            if (node.left != null) {
                return countNodes(node.left, Child.LEFT) + countNodes(node.left, Child.RIGHT) + 1;
            } else {
                return 0;
            }
        }
        else {
            if (node.right != null) {
                return  countNodes(node.right, Child.LEFT) + countNodes(node.right, Child.RIGHT) + 1;
            } else {
                return 0;
            }
        }
    }

    public TreeNode[] enumerateNodes(TreeNode node, Child child) {
        index = 0;
        int num;
        if (child == Child.LEFT)    num = countNodes(node, Child.LEFT);
        else                        num = countNodes(node, Child.RIGHT);
        TreeNode[] result = new TreeNode[num];
        if (child == Child.LEFT)    storeTraversal(node.left, result);
        else                        storeTraversal(node.right, result);
        return result;
    }

    public void storeTraversal(TreeNode node, TreeNode[] result) {
        if (node != null) {
            storeTraversal(node.left, result);
            result[index] = node;
            index += 1;
            storeTraversal(node.right, result);
        }
    }

    public TreeNode buildTree(TreeNode[] nodeList) {
        int len = nodeList.length;
        if (len == 0) return null;
        if (len == 1) return new TreeNode(nodeList[0].key);
        else {
            return setSubtree(nodeList, 0, len - 1);
            /*
            To copy the array is time-consuming.

            TreeNode[] lArr = new TreeNode[mid];
            System.arraycopy(nodeList, 0, lArr, 0, mid);
            root.left = buildTree(lArr);

            TreeNode[] rArr = new TreeNode[len - mid - 1];
            System.arraycopy(nodeList, mid + 1, rArr, 0, len - 1 - mid);
            root.right = buildTree(rArr);
             */
        }
    }

    public TreeNode setSubtree(TreeNode[]  nodeList, int a, int b) {
        if (a <= b) {
            int mid = a + (b - a) / 2;
            TreeNode root = nodeList[mid];

            root.left = setSubtree(nodeList, a, mid - 1);
            root.right = setSubtree(nodeList, mid + 1, b);

            return root;
        } else {
            return null;
        }
    }

    public void insert(int key) {
        if (root == null) {
            root = new TreeNode(key);
            return;
        }

        TreeNode node = root;

        while (true) {
            if (key <= node.key) {
                if (node.left == null) break;
                node = node.left;
            } else {
                if (node.right == null) break;
                node = node.right;
            }
        }

        if (key <= node.key) {
            node.left = new TreeNode(key);
        } else {
            node.right = new TreeNode(key);
        }
    }

    public void rebuild(TreeNode node, Child child) {
        if (node == null) return;
        TreeNode[] nodeList = enumerateNodes(node, child);
        TreeNode newChild = buildTree(nodeList);
        if (child == Child.LEFT) {
            node.left = newChild;
        } else if (child == Child.RIGHT) {
            node.right = newChild;
        }
    }

    public static void main(String[] args) {
        SGTree tree = new SGTree();
        tree.enumerateNodes(tree.root, Child.RIGHT);
    }
}
