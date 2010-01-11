package com.jvminside.blog.incremental_average_calculation;

import java.util.Random;

import junit.framework.Assert;

import org.junit.Test;

public class AverageCalculatorTest
{
    private static final int TEST_COUNT = 10000;

    private static final int NUMBER_COUNT = 10000;

    private static final int NUMBER_MAGNITUDE = 10000;

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

    private void assertAveragesEqual(Double expectedResult, double... values)
    {
        double normalResult = AverageCalculator.average(values);
        double simpleIncrementalResult = AverageCalculator.simpleIncrementalAverage(values);
        double incrementalResult = AverageCalculator.incrementalAverage(values);

        if (expectedResult != null)
        {
            Assert.assertEquals(expectedResult, normalResult);
        }

        Assert.assertEquals(normalResult, simpleIncrementalResult, 1e-10);
        Assert.assertEquals(normalResult, incrementalResult, 1e-10);
    }
}
