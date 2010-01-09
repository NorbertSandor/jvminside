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
