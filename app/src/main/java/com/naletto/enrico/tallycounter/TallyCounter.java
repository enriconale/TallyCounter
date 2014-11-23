package com.naletto.enrico.tallycounter;

/**
 * @author Enrico Naletto
 * A simple tally counter class, that increments and decrements the count based on a step,
 * editable by the user.
 */
public class TallyCounter {
    //Private variables
    private int count;
    private int step;

    //Public constructors
    public TallyCounter() {
        this.count = 0;
        this.step = 1;
    }

    public TallyCounter(int savedCount, int savedStep) {
        this.count = savedCount;
        this.step = savedStep;
    }

    /**
     * Increments the count of the tally counter, based on a step.
     */
    public void increment() {
        count += step;
    }

    /**
     * Decrements the count of the tally counter, if the count is greater than the step.
     */
    public void decrement() {
        if (this.count >= this.step)
            this.count -= this.step;
    }

    /**
     * Returns the count of the tally counter.
     * @return the count of the tally counter
     */
    public int getCount() {
        return this.count;
    }

    /**
     * Sets a new step
     * @param step the new step to be set
     * @throws IllegalArgumentException if the step is less than 0
     */
    public void setStep(int step) throws IllegalArgumentException {
        if (step < 0)
            throw new IllegalArgumentException();
        this.step = step;
    }

    /**
     * Reset the count to 0
     */
    public void reset() {
        this.count = 0;
    }

    /**
     * Returns the count, formatted to a string.
     * @return a string containing the count
     */
    @Override
    public String toString() {
        return Integer.toString(this.count);
    }

}
