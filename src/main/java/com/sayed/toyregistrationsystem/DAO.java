/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sayed.toyregistrationsystem;

import java.util.List;

/**
 *
 * @author sayed
 * @param <T>
 * @param <I>
 */
public interface DAO<T, I> {

    T insert(T object);

    List<T> retrieve();

    T retrieve(I data);

    T update(I data, T object);

    boolean delete(I data);

    default int count() {
        return retrieve().size();
    }

}
