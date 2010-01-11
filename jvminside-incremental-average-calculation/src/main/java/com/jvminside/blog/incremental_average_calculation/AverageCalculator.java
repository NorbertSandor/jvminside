package com.jvminside.blog.incremental_average_calculation;

public class AverageCalculator
{
    public static double average(double[] values)
    {
        if (values == null || values.length == 0)
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

    public static double incrementalAverage(double[] values)
    {
        if (values == null || values.length == 0)
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

    public static double simpleIncrementalAverage(double[] values)
    {
        if (values == null || values.length == 0)
        {
            throw new IllegalArgumentException("Expected non-null and non-empty array of numbers.");
        }

        double result = 0.0;

        for (int i = 0; i < values.length; i++)
        {
            result = simpleIncrementalAverageIteration(result, values[i], i);
        }

        return result;
    }

    public static double simpleIncrementalAverageIteration(double previousAverage, double value, int iterationIndex)
    {
        return ((iterationIndex * previousAverage) + value) / (iterationIndex + 1);
    }

    private AverageCalculator()
    {
    }
}
