package me.yekki.dep;

import java.util.Random;

public class RandomTest {

    public static void main(String[] args) {
        Random random = new Random(1);
        
        for (int i=0; i < 100; i++) {
            System.out.println("## " + (1 + random.nextInt(2 + 1)) + " ##");
        }
    }
}
