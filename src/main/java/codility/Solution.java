package codility;

import java.util.HashSet;
import java.util.Set;

public class Solution {

    int nMax = 90000;
    int nMin = 0;

    public int[] solution(int K, int[] T) {
        Node root = traverseTree(T, K);

        return T;
    }

    private boolean connected(int a, int b, int[] T) {
        return (T[a] == b || T[b] == a) && a != b;
    }

    // return head of the tree
    private Node traverseTree(int[] T, int K) {
        Node[] nodes = new Node[T.length];

        for (int i = 0; i < T.length; i++) {
            Node from = nodes[i];
            if (from == null) {
                from = nodes[i] = new Node(i);
            }
            Node to = nodes[T[i]];
            if (to == null) {
                to = nodes[T[i]] = new Node(T[i]);
            }
            from.neighbours.add(to);
            to.neighbours.add(from);
        }

        return nodes[K];

    }

    private Node findPath(Node root) {

        return null;
    }

    private Node findPath(Node root, Node prev) {
        if (root.neighbours.size() == 1 && root.neighbours.contains(prev)) {
            return root;
        } else {
            for (Node next : root.neighbours) {
                if (!next.equals(prev)) {
                    findPath(next, root);
                }
            }
        }

        return null;
    }

    public class Node {

        public Node() {
        }

        public Node(int id) {
            this.id = id;
        }

        public Integer   id;

        public Set<Node> neighbours = new HashSet<Node>();

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + ((id == null) ? 0 : id.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Node other = (Node) obj;
            if (!getOuterType().equals(other.getOuterType()))
                return false;
            if (id == null) {
                if (other.id != null)
                    return false;
            } else if (!id.equals(other.id))
                return false;
            return true;
        }

        private Solution getOuterType() {
            return Solution.this;
        }

    }

}
