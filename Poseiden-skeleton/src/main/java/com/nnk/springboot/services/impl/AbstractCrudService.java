package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CrudService;
import com.nnk.springboot.services.CurvePointService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

public abstract class AbstractCrudService<T> implements CrudService<T> {
    protected abstract JpaRepository<T, Integer> getRepository();
    @Override
    public List<T> delete(Integer id) {
        getRepository().deleteById(id);
        return getRepository().findAll();
    }

    @Override
    public List<T> saveOrUpdate(T curvePoint) {
        getRepository().save(curvePoint);
        return getRepository().findAll();
    }

    @Override
    public T findById(Integer id) {
        return getRepository().findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid object Id:" + id));
    }

    @Override
    public List<T> findAll() {
       return getRepository().findAll();
    }
}
