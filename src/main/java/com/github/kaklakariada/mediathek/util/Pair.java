package com.github.kaklakariada.mediathek.util;

public class Pair<A, B> {
    private final A first;
    private final B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public int hashCode() {
        final int hashFirst = first != null ? first.hashCode() : 0;
        final int hashSecond = second != null ? second.hashCode() : 0;

        return (hashFirst + hashSecond) * hashSecond + hashFirst;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Pair) {
            @SuppressWarnings("unchecked")
            final Pair<A, B> otherPair = (Pair<A, B>) other;
            return ((this.first == otherPair.first ||
                    (this.first != null && otherPair.first != null &&
                            this.first.equals(otherPair.first)))
                    &&
                    (this.second == otherPair.second ||
                            (this.second != null && otherPair.second != null &&
                                    this.second.equals(otherPair.second))));
        }

        return false;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }
}
