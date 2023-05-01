package com.example.pas22Front.dao;

import java.util.List;
import java.util.UUID;

public interface RepositoryDao<T extends ModelDao> {
    T get(UUID id);
    List<T> getAll();
    boolean add(T t);
    boolean update(UUID id, T t);
    boolean delete(UUID id);
    int size();
}
