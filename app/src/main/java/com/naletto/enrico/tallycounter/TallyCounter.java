package com.naletto.enrico.tallycounter;

/**
 * Created by enrico on 28/10/14.
 */
public class TallyCounter {
    private int count;

    public TallyCounter() {
        count = 0;
    }

    public void increment() {
        count++;
    }

    public void decrement() {
        if (count >= 1)
            count--;
    }

    public int getCount() {
        return count;
    }

    public void reset() {
        count = 0;
    }

    @Override
    public String toString() {
        return Integer.toString(count);
    }

}
