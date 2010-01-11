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

import java.util.Random;

import junit.framework.Assert;

import org.junit.Test;

public class AverageCalculatorTest
{
    private static final int TEST_COUNT = 10000;

    private static final int NUMBER_COUNT = 10000;

    private static final int NUMBER_MAGNITUDE = 10000;

    private static final double ACCEPTANCE_DELTA = 1e-10;

    @Test
    public void testWithDataset()
    {
        assertAveragesEqual(0.0, 0);
        assertAveragesEqual(1.0, 1);
        assertAveragesEqual(5.0, 5);

        assertAveragesEqual(0.5, 0, 1);
        assertAveragesEqual(1.0, 1, 1);
        assertAveragesEqual(5.0, 5, 5);
        assertAveragesEqual(3.0, 1, 5);

        assertAveragesEqual(1.0, 1, 1, 1);
        assertAveragesEqual(2.0, 2, 2, 2);
        assertAveragesEqual(2.0, 3, 1, 2);
    }

    @Test
    public void testWithRandomNumbers()
    {
        Random random = new Random();

        for (int testCount = 0; testCount < TEST_COUNT; testCount++)
        {
            int count = random.nextInt(NUMBER_COUNT) + 1;

            double[] numbers = new double[count];
            for (int i = 0; i < count; i++)
            {
                numbers[i] = NUMBER_MAGNITUDE * random.nextDouble();
            }

            assertAveragesEqual(null, numbers);
        }
    }

    @Test
    public void testNumericalStabililty()
    {
        Assert.assertEquals(Double.MAX_VALUE / 4.0, AverageCalculator.incrementalAverage(Double.MAX_VALUE / 4.0,
                Double.MAX_VALUE / 4.0, Double.MAX_VALUE / 4.0, Double.MAX_VALUE / 4.0, Double.MAX_VALUE / 4.0,
                Double.MAX_VALUE / 4.0), ACCEPTANCE_DELTA);
        Assert.assertEquals(Double.POSITIVE_INFINITY, AverageCalculator.average(Double.MAX_VALUE / 4.0,
                Double.MAX_VALUE / 4.0, Double.MAX_VALUE / 4.0, Double.MAX_VALUE / 4.0, Double.MAX_VALUE / 4.0,
                Double.MAX_VALUE / 4.0), ACCEPTANCE_DELTA);
    }

    private void assertAveragesEqual(Double expectedResult, double... values)
    {
        double normalResult = AverageCalculator.average(values);
        double incrementalResult = AverageCalculator.incrementalAverage(values);

        if (expectedResult != null)
        {
            Assert.assertEquals(expectedResult, normalResult);
        }

        Assert.assertEquals(normalResult, incrementalResult, ACCEPTANCE_DELTA);
    }
}
