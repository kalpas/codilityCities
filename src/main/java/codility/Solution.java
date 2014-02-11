package codility;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Solution {

    int counter = 0;

    int nMax = 90000;
    int nMin = 0;

    public int[] solution(int K, int[] T) {
        LinkedList<Integer> result = new LinkedList<Integer>();
        boolean[] visited = new boolean[T.length];
        Node start = traverseTree(T, K);

        result.addLast(start.id);

        result = step(start, visited, result);

        return composeResult(result);
    }

    private int[] composeResult(LinkedList<Integer> list) {
        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
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
            if (!to.equals(from)) {
                from.neighbours.add(to);
                to.neighbours.add(from);
            }
        }

        return nodes[K];

    }

    private LinkedList<Integer> step(Node start, boolean[] visited, LinkedList<Integer> result) {
        start = travel(findPath(start, visited), visited);
        result.addLast(start.id);
        if (taskComplete(visited)) {
            return result;
        } else {
            return step(start, visited, result);
        }
    }

    private boolean taskComplete(boolean[] visited) {
        for (boolean b : visited) {
            if (!b)
                return false;
        }
        return true;
    }

    private Node travel(LinkedList<Node> path, boolean[] visited) {
        for (Node node : path) {
            visited[node.id] = true;
        }
        return path.getLast();
    }

    private LinkedList<Node> findPath(Node root, boolean[] visited) {
        LinkedList<Node> path = findPath(root, root, visited);
        path.addFirst(root);
        return path;
    }

    private LinkedList<Node> findPath(Node root, Node prev, boolean[] visited) {
        counter++;

        if (root.neighbours.size() == 1 && root.neighbours.contains(prev)) {
            LinkedList<Node> path = new LinkedList<Node>();
            path.addLast(root);
            return path;
        } else {
            LinkedList<Node> bestPath = null;
            for (Node next : root.neighbours) {
                if (!next.equals(prev)) {
                    LinkedList<Node> path = findPath(next, root, visited);
                    path.addFirst(root);
                    if (bestPath == null) {
                        bestPath = path;
                    } else {
                        bestPath = getBest(bestPath, path, visited);
                    }
                }
            }
            return bestPath;
        }
    }

    private LinkedList<Node> getBest(LinkedList<Node> pathA, LinkedList<Node> pathB, boolean[] visited) {
        if (getPathQuality(pathA, visited) > getPathQuality(pathB, visited)) {
            return pathA;
        } else if (getPathQuality(pathB, visited) > getPathQuality(pathA, visited)) {
            return pathB;
        } else if (pathA.getLast().id < pathB.getLast().id) {
            return pathA;
        } else {
            return pathB;
        }
    }

    private int getPathQuality(LinkedList<Node> path, boolean[] visited) {
        int result = 0;

        for (Node node : path) {
            if (!visited[node.id]) {
                result++;
            }
        }
        return result;
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

        @Override
        public String toString() {
            return id.toString();
        }

    }

}
