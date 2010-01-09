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

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class JpaTestCase
{
    private ClassPathXmlApplicationContext applicationContext;

    @Before
    public void startApplicationContext()
    {
        PersistenceProvider persistenceProvider = PersistenceProvider
                .valueOf(System.getProperty("persistenceProvider"));

        applicationContext = new ClassPathXmlApplicationContext(
                new String[] { "classpath:/com/jvminside/blog/experimenting_with_datanucleus/"
                        + persistenceProvider.name() + ".spring.xml" });
    }

    @After
    public void closeApplicationContext()
    {
        if (applicationContext != null)
        {
            applicationContext.close();
        }
    }

    protected PersistenceService getPersistenceService()
    {
        return (PersistenceService) applicationContext.getBean("persistenceService");
    }

    protected EntityManager getEntityManager()
    {
        return getPersistenceService().getEntityManager();
    }
}
