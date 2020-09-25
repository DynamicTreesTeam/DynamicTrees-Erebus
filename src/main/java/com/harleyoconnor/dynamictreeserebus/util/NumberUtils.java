package com.harleyoconnor.dynamictreeserebus.util;

import java.util.Random;

/**
 * Common static util methods to handle numbers.
 *
 * @author Harley O'Connor
 */
public final class NumberUtils {

    /** Get Random Integer Between
     * @return A random number between the minimum and maximum parameters inclusive.
     */
    public static int getRandomIntBetween (final int min, final int max) {
        return new Random().nextInt((max + 1) - min) + min;
    }

}
