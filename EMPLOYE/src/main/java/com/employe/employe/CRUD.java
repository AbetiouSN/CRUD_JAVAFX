package com.employe.employe;
import java.util.List;
import java.util.Optional;

public interface CRUD<T,PK> {
    public boolean Create(T object);
    public List<T> all();
    Optional<T> Read(PK Id);
    boolean Update(T object , PK Id);
    boolean Delete(PK Id);
    Long Count();

}
