package com.example.bob.health_helper.Util;

import android.graphics.Color;

import java.util.Random;

public class RandomColorUtil {
    private static Integer[] colors = {Color.parseColor("#757575"), Color.parseColor("#242524"), Color.parseColor("#49617e"),
            Color.parseColor("#965e75"), Color.parseColor("#3b9a58"), Color.parseColor("#05596e"),
            Color.parseColor("#943e4f"), Color.parseColor("#0a5d17")};


    public static int getRandomColor(Random random) {
        return colors[random.nextInt(colors.length)];
    }
}
