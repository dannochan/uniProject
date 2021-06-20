package de.uniba.wiai.dsg.ajp.assignment3;

public enum Category {
    REGULAR(0),
    NEW_RELEASE(1),
    CHILDRENS(2);

    private final int value;

    public int getValue() {
        return value;
    }

    Category(int value) {
        this.value = value;
    }
}