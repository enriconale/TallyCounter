package com.naletto.enrico.tallycounter;

/**
 * @author Enrico Naletto
 * A simple tally counter class, that increments and decrements the count based on a step,
 * editable by the user.
 */
public class TallyCounter {
    //Private variables
    private int mCount;
    private int mStep;

    //Public constructors
    public TallyCounter() {
        mCount = 0;
        mStep = 1;
    }

    public TallyCounter(int count, int step) {
        mCount = count;
        mStep = step;
    }

    /**
     * Increments the count of the tally counter, based on a step.
     */
    public void increment() {
        mCount += mStep;
    }

    /**
     * Decrements the count of the tally counter, if the count is greater than the step.
     */
    public void decrement() {
        if (mCount >= mStep)
            mCount -= mStep;
    }

    /**
     * Returns the count of the tally counter.
     * @return the count of the tally counter
     */
    public int getCount() {
        return mCount;
    }

    /**
     * Sets a new step
     * @param step the new step to be set
     * @throws IllegalArgumentException if the step is less than 0
     */
    public void setStep(int step) throws IllegalArgumentException {
        if (step <= 0)
            throw new IllegalArgumentException();
        mStep = step;
    }

    /**
     * Reset the count to 0
     */
    public void reset() {
        mCount = 0;
    }

    /**
     * Returns the count, formatted to a string.
     * @return a string containing the count
     */
    @Override
    public String toString() {
        return Integer.toString(mCount);
    }

}
