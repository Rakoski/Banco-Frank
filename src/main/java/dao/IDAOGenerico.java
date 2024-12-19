package dao;

public interface IDAOGenerico<T> {
    T inserir (T entidade);
    T alterar (T entidade);
}
