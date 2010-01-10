/*
   Copyright 2010 Norbert Sándor
   
   This code is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.
    
   This software is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
    
   You should have received a copy of the GNU General Public License
   along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */
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
                Assert.assertEquals("The detached instance was probably persisted as a new instance: ", 1,
                        ((Number) getEntityManager().createQuery("SELECT COUNT(p) FROM Parent p").getSingleResult())
                                .intValue());

                Parent parent = getEntityManager().find(Parent.class, 100);
                Assert.assertEquals("Version number wasn't incremented as expected: ", originalVersion + 1, parent
                        .getVersion());
                Assert.assertEquals("Parent should have 2 children because one of them was removed: ", 2, parent
                        .getChildren().size());
            }
        });
    }
}
