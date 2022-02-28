public class SGTree {
    enum Child {LEFT, RIGHT}
    public static int index;

    public static class TreeNode {
        int weight;
        int key;
        public TreeNode left = null;
        public TreeNode right = null;

        TreeNode(int k) {
            key = k;
            weight = 1;
        }
    }

    public TreeNode root = null;

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

    TreeNode[] enumerateNodes(TreeNode node, Child child) {
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

    TreeNode buildTree(TreeNode[] nodeList) {
        int len = nodeList.length;
        if (len == 0) return null;
        if (len == 1) return new TreeNode(nodeList[0].key);
        else          return setSubtree(nodeList, 0, len - 1);
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

    public boolean checkBalance(TreeNode node) {
        boolean result = true;
        if (node != null) {
            if (node.left != null && (node.left.weight > node.weight * 2 / 3))      result = false;
            if (node.right != null && (node.right.weight > node.weight * 2 / 3))    result = false;
        }
        return result;
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

    public void fixWeights(TreeNode node, Child child) {
        if (node == null)            return;
        if (child == Child.LEFT)     fixWeights(node.left);
        if (child == Child.RIGHT)    fixWeights(node.right);
    }

    public void fixWeights(TreeNode node) {
        if (node.right == null && node.left == null) {
            node.weight = 1;
        }
        else if (node.right == null) {
            fixWeights(node.left);
            node.weight = node.left.weight + 1;
        }
        else if (node.left == null) {
            fixWeights(node.right);
            node.weight = node.right.weight + 1;
        }
        else {
            fixWeights(node.right);
            fixWeights(node.left);
            node.weight = node.left.weight + node.right.weight + 1;
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
                node.weight++;
                node = node.left;
            } else {
                if (node.right == null) break;
                node.weight++;
                node = node.right;
            }
        }

        if (key <= node.key) {
            node.weight++;
            node.left = new TreeNode(key);
        } else {
            node.weight++;
            node.right = new TreeNode(key);
        }
        keepBalance(key);
    }

    public void keepBalance(int key) {
        TreeNode node = root;

        while (true) {
            if (key <= node.key) {
                if (node.left == null)    break;
                if (!checkBalance(node.left)) {
                    rebuild(node, Child.LEFT);
                    fixWeights(node, Child.LEFT);
                    break;
                }
                node = node.left;
            }
            else {
                if (node.right == null)    break;
                if (!checkBalance(node.right)) {
                    rebuild(node, Child.RIGHT);
                    fixWeights(node, Child.RIGHT);
                    break;
                }
                node = node.right;
            }
        }
    }

    public static void main(String[] args) {
        SGTree tree = new SGTree();
        /*
        for (int i = 0; i < 100; i++) {
            tree.insert(i);
        }
        tree.rebuild(tree.root, Child.RIGHT);
         */
        tree.checkBalance(tree.root);
    }
}
