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

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

@MappedSuperclass
public class BaseEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    private int id;

    private int version;

    @Id
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public BaseEntity presetId(int id)
    {
        setId(id);
        return this;
    }

    @Version
    public int getVersion()
    {
        return version;
    }

    public void setVersion(int version)
    {
        this.version = version;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof BaseEntity))
        {
            return false;
        }

        BaseEntity other = (BaseEntity) obj;

        if (getId() == 0 || other.getId() == 0)
        {
            throw new IllegalStateException("ID should be assigned for both entities before calling equals()");
        }

        return getId() == other.getId();
    }

    @Override
    public int hashCode()
    {
        if (getId() == 0)
        {
            throw new IllegalStateException("ID should be assigned before calling hashCode()");
        }

        return new Long(getId()).hashCode();
    }

    @Override
    public String toString()
    {
        return getClass().getName() + "-" + getId();
    }

    @Transient
    public boolean isTransient()
    {
        return getId() == 0;
    }
}
