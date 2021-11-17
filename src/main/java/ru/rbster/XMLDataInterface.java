package ru.rbster;

import java.util.List;

public interface XMLDataInterface {
    public <T> void saveAll(List<T> elements);
    public void reform();
}
