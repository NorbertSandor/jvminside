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
package com.jvminside.blog.incremental_average_calculation;

public class AverageCalculator
{
    public static double average(double... values)
    {
        if (values.length == 0)
        {
            throw new IllegalArgumentException("Expected non-null and non-empty array of numbers.");
        }

        double result = 0.0;

        for (int i = 0; i < values.length; i++)
        {
            result += values[i];
        }

        result /= values.length;

        return result;
    }

    public static double incrementalAverage(double... values)
    {
        if (values.length == 0)
        {
            throw new IllegalArgumentException("Expected non-null and non-empty array of numbers.");
        }

        double result = 0.0;

        for (int i = 0; i < values.length; i++)
        {
            result = incrementalAverageIteration(result, values[i], i);
        }

        return result;
    }

    public static double incrementalAverageIteration(double previousAverage, double value, int iterationIndex)
    {
        return ((value - previousAverage) / (iterationIndex + 1)) + previousAverage;
    }

    private AverageCalculator()
    {
    }
}
