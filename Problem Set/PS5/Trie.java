import java.util.ArrayList;

public class Trie {
    final char WILDCARD = '.';

    private class TrieNode {
        char key;
        boolean end;
        TrieNode[] children = new TrieNode[75];

        TrieNode() {
            this.key = '-';
            this.end = false;
        }

        TrieNode(char key) {
            this.key = key;
            this.end = false;
        }

        TrieNode find(char key) {
            return children[key - 48];
        }

        boolean contains(char key) {
            return children[key - 48] != null;
        }

        void insert(TrieNode node) {
            children[node.key - 48] = node;
        }

        public void markEnd() {
            this.end = true;}
    }

    TrieNode root = new TrieNode();

    public Trie() {}

    void insert(String s) {
        TrieNode cur = root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (cur.contains(c))    cur = cur.find(c);
            else {
                TrieNode node = new TrieNode(c);
                cur.insert(node);
                cur = node;
            }
        }
        cur.markEnd();
    }

    boolean contains(String s) {
        TrieNode cur = root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ((int) c < 48 || (int) c > 122)    return false;
            if (!cur.contains(c))                 return false;
            cur = cur.find(c);
        }
        return cur.end;
    }

    void prefixSearch(String s, ArrayList<String> results, int limit) {
        TrieNode cur = root;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == WILDCARD) {
                // Using StringBuilder can lower the time further.
                String beg = s.substring(0, i);
                String end = s.substring(i + 1);
                for (int j = 0; j < 75; j++) {
                    if (cur.children[j] != null)
                        prefixSearch(beg + (char) (j + 48) + end, results, limit);
                }
                break;
            }
            if (cur.contains(c))    cur = cur.find(c);
            else                    return;
        }

        //Store possible words.
        if (results.size() < limit)  {
            if (cur.end && contains(s))   results.add(s);
            if (!s.contains("."))         helper(s, results, cur, limit);
        }
    }

    void helper(String s, ArrayList<String> results, TrieNode cur, int limit) {
        for (int i = 0; i < 75; i++) {
            TrieNode node = cur.children[i];
            if (node == null)       continue;
            if (!node.end)          helper(s + node.key, results, node, limit);
            if (node.end && !results.contains(s) && results.size() < limit) {
                results.add(s + node.key);
                helper(s + node.key, results, node, limit);
            }
        }
    }

    String[] prefixSearch(String s, int limit) {
        ArrayList<String> results = new ArrayList<String>();
        prefixSearch(s, results, limit);
        return results.toArray(new String[0]);
    }

    public static void main(String[] args) {
        Trie t = new Trie();
        t.insert("peter");
        t.insert("piper");
        t.insert("picked");
        t.insert("a");
        t.insert("peck");
        t.insert("of");
        t.insert("pickled");
        t.insert("peppers");
        t.insert("pepppito");
        t.insert("pepi");
        t.insert("pik");

        String[] result1 = t.prefixSearch("pe", 10);
        String[] result2 = t.prefixSearch("pe.", 10);
        // result1 should be:
        // ["peck", "pepi", "peppers", "pepppito", "peter"]
        // result2 should contain the same elements with result1 but may be ordered arbitrarily
    }
}
