package com.jvminside.blog.experimenting_with_datanucleus;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import junit.framework.Assert;

import org.datanucleus.util.NucleusLogger;

public class DetachedEntityTestForDatanucleusForum
{
    private static EntityManagerFactory emf;

    private static EntityManager em;

    public static void main(String[] args)
    {
        emf = Persistence.createEntityManagerFactory("forDatanucleusForum", null);

        try
        {

            //
            // Populate database
            //

            final Parent[] detached = new Parent[1];
            final int originalVersion;

            executeTransactionally(new Runnable()
            {
                @Override
                public void run()
                {
                    Parent parent = (Parent) new Parent().presetId(100);

                    parent.getChildren().add((Child) new Child(0).presetId(101));
                    parent.getChildren().add((Child) new Child(1).presetId(102));
                    parent.getChildren().add((Child) new Child(2).presetId(103));
                    getEntityManager().persist(parent);

                    getEntityManager().flush();

                    detached[0] = parent;
                }
            });

            originalVersion = detached[0].getVersion();

            final Child deleted = detached[0].getChildren().iterator().next();
            detached[0].getChildren().remove(deleted);

            //
            // Merge modified
            //

            executeTransactionally(new Runnable()
            {
                @Override
                public void run()
                {
                    Parent merged = getEntityManager().merge(detached[0]);
                    Assert.assertEquals(2, merged.getChildren().size());
                }
            });

            //
            // Check result
            //

            executeTransactionally(new Runnable()
            {
                @Override
                public void run()
                {
                    Assert
                            .assertEquals("The detached instance was probably persisted as a new instance: ", 1,
                                    ((Number) getEntityManager().createQuery("SELECT COUNT(p) FROM Parent p")
                                            .getSingleResult()).intValue());

                    Parent parent = getEntityManager().find(Parent.class, 100);
                    Assert.assertEquals("Version number wasn't incremented as expected: ", originalVersion + 1, parent
                            .getVersion());
                    Assert.assertEquals("Parent should have 2 children because one of them was removed: ", 2, parent
                            .getChildren().size());
                }
            });
        }
        finally
        {
            emf.close();
        }
    }

    private static void executeTransactionally(Runnable runnable)
    {
        em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try
        {
            tx.begin();

            runnable.run();

            tx.commit();
        }
        catch (Exception e)
        {
            NucleusLogger.GENERAL.info(">> Exception thrown persisting objects : " + e.getMessage());
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }

            em.close();
            em = null;
        }
    }

    private static EntityManager getEntityManager()
    {
        return em;
    }
}
