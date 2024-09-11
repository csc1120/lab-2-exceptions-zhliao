/*
 * Course: CSC1020
 * Lab 2 - Exceptions
 * DieNotRolledException class
 * Name: Zhonghao Liao
 * Last Updated: 09/11/2024
 */
package liao;

public class DieNotRolledException extends RuntimeException {
    public DieNotRolledException(String errorMessage) {
        super(errorMessage);
    }

    public String getMessage() {
        return super.getMessage() != null ? super.getMessage() : "The die has not been rolled yet!";
    }
}
