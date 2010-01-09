/*
 * Copyright 2009 Norbert Sándor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
