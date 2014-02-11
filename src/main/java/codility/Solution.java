package codility;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Solution {

    int counter = 0;

    int nMax    = 90000;
    int nMin    = 0;

    public int[] solution(int K, int[] T) {
        int[] result = new int[T.length];
        int resultIndex = 0;
        boolean[] visited = new boolean[T.length];
        Node start = traverseTree(T, K);

        result[resultIndex++] = start.id;

        // result = step(start, visited, result);

        while (!taskComplete(visited)) {
            start = travel(findPath(start, visited), visited);
            result[resultIndex++] = start.id;
        }

        return result;
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
        Result result = findPath(root, root, visited);
        result.path.addFirst(root);
        return result.path;
    }

    private Result findPath(Node root, Node prev, boolean[] visited) {
        counter++;

        if (root.neighbours.size() == 1 && root.neighbours.contains(prev)) {
            Result result = new Result();
            result.path = new LinkedList<Node>();
            result.quality = visited[root.id] ? 0 : 1;
            result.path.addLast(root);
            return result;
        } else {
            Result bestPath = null;
            for (Node next : root.neighbours) {
                if (!next.equals(prev)) {
                    Result result = findPath(next, root, visited);
                    result.path.addFirst(root);
                    result.quality += visited[root.id] ? 0 : 1;
                    if (bestPath == null) {
                        bestPath = result;
                    } else {
                        bestPath = getBest(bestPath, result);
                    }
                }
            }
            return bestPath;
        }
    }

    private Result getBest(Result pathA, Result pathB) {
        if (pathA.quality > pathB.quality) {
            return pathA;
        } else if (pathB.quality > pathA.quality) {
            return pathB;
        } else if (pathA.path.getLast().id < pathB.path.getLast().id) {
            return pathA;
        } else {
            return pathB;
        }
    }

    public class Result {
        LinkedList<Node> path;
        int              quality = 0;
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
