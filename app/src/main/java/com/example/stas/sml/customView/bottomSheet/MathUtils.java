package com.example.stas.sml.customView.bottomSheet;

public class MathUtils {
    static int clamp(int amount, int low, int high) {
        return amount < low ? low : (amount > high ? high : amount);
    }

    static float clamp(float amount, float low, float high) {
        return amount < low ? low : (amount > high ? high : amount);
    }
}
