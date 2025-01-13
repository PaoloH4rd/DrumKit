package org.paolo.drumkit_.service.def;

import java.util.List;

public interface GeneralService<T> {
    void add(T t);
    void update(T t);
    List<T> getAll();
    T getById(long id);
    void setIsDisattivatoTrue(long id);
}
