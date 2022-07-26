package model.figure;

import model.exception.IllegalStateOfGameException;

public interface IMovable {
    void move(int offset) throws IllegalStateOfGameException, InterruptedException;
}
