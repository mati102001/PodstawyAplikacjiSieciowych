package com.example.pas22.repositories;

import com.example.pas22.dao.ModelDao;
import com.example.pas22.dao.RepositoryDao;

import java.util.*;

import static java.util.Collections.synchronizedList;


public abstract class Repository<T extends ModelDao> implements RepositoryDao<T> {
    final private List<T> collection = synchronizedList(new ArrayList());

    @Override
    public boolean add(T obj) {
        if (obj == null) {
            return false;
        }

        for (T currentObj: collection) {
            if (currentObj.getId().equals(obj.getId())) return false;
        }

        collection.add(obj);
        return true;
    }

    @Override
    public boolean delete(UUID id) {
        return collection.removeIf(obj -> obj.getId().equals(id));
    }

    @Override
    public T get(UUID id) {
        for (T object : collection) {
            if(object.getId().equals(id)) {
                return object;
            }
        }
        return null;
    }

    @Override
    public List<T> getAll() {
        return collection;
    }

    @Override
    public int size() {
        return collection.size();
    }
}