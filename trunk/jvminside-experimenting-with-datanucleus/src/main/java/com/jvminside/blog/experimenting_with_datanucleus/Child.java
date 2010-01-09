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

import java.math.BigDecimal;

import javax.persistence.Entity;

@Entity
public class Child extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private int value;

    private BigDecimal bigValue;

    public Child()
    {
    }

    public Child(int value)
    {
        setValue(value);
        setBigValue(new BigDecimal(value));
    }

    public int getValue()
    {
        return value;
    }

    public void setValue(int value)
    {
        this.value = value;
    }

    public BigDecimal getBigValue()
    {
        return bigValue;
    }

    public void setBigValue(BigDecimal bigValue)
    {
        this.bigValue = bigValue;
    }
}
