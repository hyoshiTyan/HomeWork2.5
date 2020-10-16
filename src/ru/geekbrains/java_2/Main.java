package ru.geekbrains.java_2;
import java.util.Arrays;

public class Main {
    static final int size = 4;
    static final int h = size / 2;

    private static float[] doArray() {
        float[] arr = new float[size];
        Arrays.fill(arr, 1);
        return arr;
    }

    public static void main(String[] args) {
        calcTime(doArray());
        calcTimeTwo(doArray());
    }

    private static void calcTime(float[] arr) {
        System.out.println(Arrays.toString(arr));
        long a = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println(Arrays.toString(arr));
        System.out.println(System.currentTimeMillis() - a);
    }

    private static void calcTimeTwo(float[] arr) {
        System.out.println(Arrays.toString(arr));
        float[] OneArray = new float[h];
        float[] TwoArray = new float[h];
        long b = System.currentTimeMillis();
        System.arraycopy(arr, 0, OneArray, 0, h);
        System.arraycopy(arr, h, TwoArray, 0, h);
        System.out.println(Arrays.toString(OneArray));
        System.out.println(Arrays.toString(TwoArray));
        Thread calcOne = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < OneArray.length; i++) {
                    OneArray[i] = (float) (OneArray[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });

        System.arraycopy(OneArray, 0, arr, 0, h);
        System.arraycopy(TwoArray, 0, arr, h, h);
        System.out.println(Arrays.toString(arr));
        System.out.println(System.currentTimeMillis() - b);

        calcOne.start();
        Thread calcTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < TwoArray.length; i++) {
                    TwoArray[i] = (float) (TwoArray[i] * Math.sin(0.2f + (i + h) / 5) * Math.cos(0.2f + (i + h) / 5) * Math.cos(0.4f + (i + h) / 2));
                }
            }
        });
        calcTwo.start();
        try {
            calcOne.join();
            calcTwo.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}

