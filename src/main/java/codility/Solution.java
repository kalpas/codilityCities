package codility;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class Solution {

    int nMax = 90000;
    int nMin = 0;

    // TODO use tack instead of recursion
    public int[] solution(int K, int[] T) {
        if (T.length == 1) {
            return new int[] { 0 };
        }

        LinkedList<Integer> result = new LinkedList<Integer>();
        boolean[] visited = new boolean[T.length];
        Node start = traverseTree(T, K);

        result.addLast(start.id);

        while (!taskComplete(visited)) {
            start = travel(findPath(start, visited).nodes, visited);
            result.addLast(start.id);

            Node old = start;
            start = start.neighbours.get(0);
            start.neighbours.remove(old);
        }

        return composeResult(result);
    }

    public int[] solution2(int K, int[] T) {
        if (T.length == 1) {
            return new int[] { 0 };
        }

        LinkedList<Integer> result = new LinkedList<Integer>();
        boolean[] visited = new boolean[T.length];
        Node start = traverseTree(T, K);

        result.addFirst(start.id);

        while (!taskComplete(visited)) {
            start = travel(findPath2(start, visited).nodes, visited);
            result.addLast(start.id);

            // Node old = start;
            // start = start.neighbours.get(0);
            // start.neighbours.remove(old);
        }

        return composeResult(result);
    }

    private Path findPath2(Node root, boolean[] visited) {
        Stack<Node> stack = new Stack<Node>();
        stack.push(root);

        visited[root.id] = true;

        Path best = null;
        Path path = new Path();
        path.nodes.addLast(root);
        path.quality += 1;

        boolean[] traversed = new boolean[visited.length];
        traversed[root.id] = true;

        Node prev = null;
        Node current = null;

        while (!stack.empty()) {
            prev = current;
            current = stack.peek();
            if (stack.size() == 1 && prev != null && traversed[current.id]) {
                break;
            }
            traversed[current.id] = true;
            if (current.neighbours.size() == 1 && prev != null && current.neighbours.contains(prev)) {
                if (best == null) {
                    best = path.clone();
                } else {
                    best = getBest(best, path, visited).clone();
                }

                while (stack.peek().neighbours.size() <= 2) {
                    pop(visited, stack, path);
                }
                continue;
            } else {
                boolean pop = true;
                for (Node next : current.neighbours) {
                    if (!traversed[next.id]) {
                        stack.push(next);

                        path.nodes.addLast(next);
                        path.quality += visited[next.id] ? 0 : 1;
                        pop = false;
                        break;
                    }
                }
                if (pop)
                    pop(visited, stack, path);
            }
        }

        return best;

    }

    private void pop(boolean[] visited, Stack<Node> stack, Path current) {
        Node removed = stack.pop();
        current.quality -= visited[removed.id] ? 0 : 1;
        current.nodes.removeLast();
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

    private boolean taskComplete(boolean[] visited) {
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                System.out.println(i * 100. / visited.length);
                return false;
            }
        }
        return true;
    }

    private Node travel(LinkedList<Node> path, boolean[] visited) {
        for (Node node : path) {
            visited[node.id] = true;
        }
        return path.getLast();
    }

    private Path findPath(Node root, boolean[] visited) {
        Path path = findPath(root, root, visited);
        path.nodes.addFirst(root);
        return path;
    }

    private Path findPath(Node root, Node prev, boolean[] visited) {
        if (root.neighbours.size() == 1 && root.neighbours.contains(prev)) {
            Path path = new Path();
            path.nodes = new LinkedList<Node>();
            path.nodes.addLast(root);
            path.quality += visited[root.id] ? 0 : 1;

            return path;
        } else {
            Path bestPath = null;
            for (Node next : root.neighbours) {
                if (!next.equals(prev)) {
                    Path path = findPath(next, root, visited);
                    path.nodes.addFirst(root);
                    path.quality += visited[root.id] ? 0 : 1;
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

    private Path getBest(Path pathA, Path pathB, boolean[] visited) {
        if (pathA.quality > pathB.quality) {
            return pathA;
        } else if (pathB.quality > pathA.quality) {
            return pathB;
        } else if (pathA.nodes.getLast().id < pathB.nodes.getLast().id) {
            return pathA;
        } else {
            return pathB;
        }
    }

    public class Path {
        public LinkedList<Node> nodes   = new LinkedList<Node>();

        public int              quality = 0;

        public Path clone() {
            Path result = new Path();
            result.nodes = new LinkedList<Node>(this.nodes);
            result.quality = this.quality;
            return result;
        }
    }

    public class Node {

        public Node() {
        }

        public Node(int id) {
            this.id = id;
        }

        public Integer         id;

        public ArrayList<Node> neighbours = new ArrayList<Node>();

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
