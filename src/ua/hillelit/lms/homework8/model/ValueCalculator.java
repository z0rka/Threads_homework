package ua.hillelit.lms.homework8.model;

import java.util.Arrays;

public class ValueCalculator {

  private static final int ARRAY_SIZE = 10_000_000;
  private static final int HALF_ARRAY_SIZE = 5_000_000;
  private static float[] array = new float[ARRAY_SIZE];
  private static float[] array_split1 = new float[HALF_ARRAY_SIZE];
  private static float[] array_split2 = new float[HALF_ARRAY_SIZE];

  public static void doCalc() {

    long start = System.currentTimeMillis();

    Arrays.fill(array,1);

    split_array();

    Thread myThread1 = new Thread(() -> format_array(array_split1));
    Thread myThread2 = new Thread(() -> format_array(array_split2));

    myThread1.start();
    myThread2.start();

    try {
      myThread1.join();
      myThread2.join();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    glue_arrays();

    System.out.println("Time needed " + (System.currentTimeMillis() - start));
  }

  private static void split_array() {
    System.arraycopy(array, 0, array_split1, 0, HALF_ARRAY_SIZE);
    System.arraycopy(array, HALF_ARRAY_SIZE, array_split2, 0, HALF_ARRAY_SIZE);
  }

  private static void format_array(float[] array_format) {
    for (int i = 0; i < array_format.length; i++) {
      array_format[i] = (float) (array_format[i] * Math.sin(0.2f + i / 5) *
          Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
    }
  }

  private static void glue_arrays() {
    System.arraycopy(array_split1, 0, array, 0, HALF_ARRAY_SIZE);
    System.arraycopy(array_split2, 0, array, HALF_ARRAY_SIZE, HALF_ARRAY_SIZE);
  }
}
