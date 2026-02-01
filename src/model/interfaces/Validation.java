package model.interfaces;

public interface Validation {
    boolean isValid(); // abstract method

    default void validateOrThrow() {
        if (!isValid()) {
            throw new IllegalStateException("Invalid object");
        }
    }

    static boolean checkAllValid(Validation[] items) {
        for (Validation item : items) {
            if (!item.isValid()) return false;
        }
        return true;
    }
}