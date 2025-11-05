package pl.ptemich.jerdy.graph;

import lombok.EqualsAndHashCode;

import java.util.Set;

public class Graph {

    public void addNode() {

    }

    @EqualsAndHashCode(onlyExplicitlyIncluded = true)
    public static class Node {
        @EqualsAndHashCode.Include
        private String id;

        private Set<Node> connections;
    }

}
