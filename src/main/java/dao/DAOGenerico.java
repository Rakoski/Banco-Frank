package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.text.html.parser.Entity;

public abstract class DAOGenerico<T> implements IDAOGenerico<T> {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bancoPU");
    private final Class<T> classeEntidade;

    public DAOGenerico(Class<T> classEntidade) {
        this.classeEntidade = classEntidade;
    }

    protected EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public T inserir(T entidade) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entidade);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return entidade;
    }

    @Override
    public T alterar(T entidade) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(entidade);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return entidade;
    }
}
