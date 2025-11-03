package app.dao;

public interface IDAO<T, D> {

    public T create(D dto);

    public T findById(int id);

    public T update(D dto);

    public void delete(int id);
}
