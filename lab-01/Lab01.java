public class Lab01 {
    public static void main(String[] args) {
        int size_a = 12, size_x = 19;
        long[] a = new long[size_a];
        int k = 23;
        for (int i = 0; i < size_a; i++) {
            a[i] = k;
            k -= 2;
        }
        float[] x = new float[size_x];
        float max = 8.0f, min = -12.0f;
        for (int i = 0; i < size_x; i++)
            x[i] = (float) Math.random() * (max - min) + min;
        double[][] c = new double[size_a][size_x];
        for (int i = 0; i < size_a; i++) {
            for (int j = 0; j < size_x; j++) {
                if (a[i] == 21)
                    c[i][j] = Math.cbrt(Math.tan(x[j]));
                else if (a[i] == 9 || a[i] == 11 || a[i] == 13 || a[i] == 15 || a[i] == 19 || a[i] == 23)
                    c[i][j] = Math.sin(Math.cbrt(Math.tan(x[j])));
                else
                    c[i][j] = Math.pow(Math.asin(0.25 * 0.1 * (x[j] - 2) / 2 * Math.E + 1) / 2, Math.log(Math.acos(Math.sin(x[j]))));
                System.out.printf("%7.3f", c[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
