package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;

import java.util.List;

/**
 * The interface Crud service.
 *
 * @param <T> the type parameter
 */
public interface CrudService<T> {
    /**
     * Delete list.
     *
     * @param id the id
     * @return the list
     */
    List<T> delete(Integer id);

    /**
     * Save or update list.
     *
     * @param object the object
     * @return the list
     */
    List<T> saveOrUpdate(T object);

    /**
     * Find by id t.
     *
     * @param id the id
     * @return the t
     */
    T findById(Integer id);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<T>  findAll();
}
