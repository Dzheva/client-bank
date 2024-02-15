package spring.clientbank.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

public interface DAO<T> {
    T save(T obj);

    boolean delete(T obj);

    void deleteAll(List<T> entities);

    void saveAll(List<T> entities);

    boolean deleteById(Long id);

    T getOne(Long id);
}
