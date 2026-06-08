package it.unicam.model.persistence;

public interface PersistenceContext<T> {

    public T load();

    public void save(T t);

}
