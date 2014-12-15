package com.naletto.enrico.tallycounter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Enrico Naletto
 * A simple tally counter class, that increments and decrements the count based on a step,
 * editable by the user.
 */
public class TallyCounter {

    private final String JSON_COUNT = "count";
    private final int MAX_VALUE = 10000000;

    //Private variables
    private int mCount;
    private int mStep;

    public TallyCounter(int count, int step) {
        mCount = count;
        mStep = step;
    }

    /**
     * Increments the count of the tally counter, based on a step.
     */
    public boolean increment() {
        if (canIncrease()) {
            mCount += mStep;
            return true;
        }
        return false;
    }

    /**
     * Decrements the count of the tally counter, if the count is greater than the step.
     */
    public boolean decrement() {
        if (canDecrease()) {
            mCount -= mStep;
            return true;
        }
        return false;

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
        if (step < 0)
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

    /**
     * Parses the tally counter object to a JSON Object
     * @return the JSON file that contains the count
     * @throws JSONException
     */
    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_COUNT, mCount);
        return json;
    }

    /**
     * Verifies if the count of the tally counter can decrease
     * @return True if the count of the tally counter can decrease with the current step, false
     * otherwise
     */
    public boolean canDecrease() {
        return (mCount >= mStep);
    }

    /**
     * Verifies if the count of the tally counter can increase
     * @return True if the count of the tally counter can increase with the current step, false
     * otherwise
     */
    public boolean canIncrease() {
        return ((mCount + mStep) <= MAX_VALUE);
    }

}
