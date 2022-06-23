package model.figure;

import model.exception.IllegalStateOfGameException;

public interface IMoveable {
    void move(int offset) throws IllegalStateOfGameException;
}
