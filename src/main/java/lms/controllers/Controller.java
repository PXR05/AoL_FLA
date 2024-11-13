package lms.controllers;

import java.util.List;

public abstract class Controller <T> {
    public abstract void add(T t);
    public abstract void remove(T t);
    public abstract T find(String id);
    public abstract List<T> getAll();
}
