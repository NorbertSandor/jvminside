package com.jvminside.blog.experimenting_with_datanucleus;

import junit.framework.Assert;

import org.junit.Test;

public class DetachedEntityTest extends JpaTestCase
{
    @Test
    public void doTest()
    {
        //
        // Populate database
        //

        final Parent[] detached = new Parent[1];
        final int originalVersion;

        getPersistenceService().executeTransactionally(new Runnable()
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
        // Merge unmodified
        //

        getPersistenceService().executeTransactionally(new Runnable()
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

        getPersistenceService().executeTransactionally(new Runnable()
        {
            @Override
            public void run()
            {
                Parent parent = getEntityManager().find(Parent.class, 100);
                Assert.assertEquals(originalVersion + 1, parent.getVersion());
            }
        });
    }
}
