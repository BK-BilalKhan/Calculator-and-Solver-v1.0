
package CaS.MatrixSolver;

import java.text.NumberFormat;
import java.util.StringTokenizer;
import javax.swing.JLabel;
import javax.swing.JTextArea;

/*
    Author Bilal (K132314)
                            */

public class Matrix_Functions {
    
    private final boolean DEBUG = true;
    private final boolean INFO = true;
    private final int max = 100;
    private int n = 4;
    private int iDF = 0;
    private static NumberFormat nf;
    
    public double[][] ReadInMatrix(JTextArea Text, JLabel statusBar) throws Exception {
        if (DEBUG) {
            System.out.println("Reading In Matrix");
        }

        /* == Parse Text Area == */
        String rawText = Text.getText();
        String val = "";
        int i = 0;
        int j = 0;
        int[] rsize = new int[max];

        /* == Determine Matrix Size/Valid == */
        StringTokenizer ts = new StringTokenizer(rawText, "\n");
        while (ts.hasMoreTokens()) {
            StringTokenizer ts2 = new StringTokenizer(ts.nextToken());
            while (ts2.hasMoreTokens()) {
                ts2.nextToken();
                j++;
            }
            rsize[i] = j;
            i++;
            j = 0;
        }
        statusBar.setText("Matrix Size: " + i);
        if ((DEBUG) || (INFO)) {
            System.out.println("Matrix Size: " + i);
        }
        for (int c = 0; c < i; c++) {
            if (DEBUG) {
                System.out.println("i = " + i + "  j = " + rsize[c] + "   Column: "
                        + c);
            }

            if (rsize[c] != i) {
                statusBar.setText("Invalid Matrix Entered. i.e: Size Mismatch");
                throw new Exception("Invalid Matrix Entered. i.e: Size Mismatch");
            }
        }
        
        /* == set matrix size == */
        n = i;

        double matrix[][] = new double[n][n];
        i = j = 0;
        val = "";

        /* == Do the actual parsing of the text now == */
        StringTokenizer st = new StringTokenizer(rawText, "\n");
        while (st.hasMoreTokens()) {
            StringTokenizer st2 = new StringTokenizer(st.nextToken());
            while (st2.hasMoreTokens()) {
                val = st2.nextToken();
                try {
                    matrix[i][j] = Float.valueOf(val).floatValue();
                } catch (Exception exception) {
                    statusBar.setText("Invalid Number Format");
                }
                j++;
            }
            i++;
            j = 0;
        }
        if (DEBUG) {
            System.out.println("Matrix Read::");
            for (i = 0; i < n; i++) {
                for (j = 0; j < n; j++) {
                    System.out.print("m[" + i + "][" + j + "] = "
                            + matrix[i][j] + "   ");
                }
                System.out.println();
            }
        }
        return matrix;
    }
    
    public double[][] ReadInMatrixNotSquare(JTextArea Text)
            throws Exception {
        if (DEBUG) {
            System.out.println("Reading In Matrix");
        }

        /* == Parse Text Area == */
        String rawtext = Text.getText();

        /* == Determine Matrix Size/Valid == */
        StringTokenizer ts = new StringTokenizer(rawtext, "\n");
        if (DEBUG) {
            System.out.println("Rows: " + ts.countTokens());
        }
        double matrix[][] = new double[ts.countTokens()][];
        StringTokenizer st2;
        int row = 0;
        int col = 0;
        //making sure rows are same length
        int last = -5;
        int curr = -5;
        while (ts.hasMoreTokens()) {
            st2 = new StringTokenizer(ts.nextToken(), " ");
            last = curr;
            curr = st2.countTokens();
            if (last != -5 && curr != last) {
                throw new Exception("Rows not of equal length");
            }
            if (DEBUG) {
                System.out.println("Cols: " + st2.countTokens());
            }
            matrix[row] = new double[st2.countTokens()];
            while (st2.hasMoreElements()) {
                matrix[row][col++] = Float.parseFloat(st2.nextToken());
            }
            row++;
            col = 0;
        }
        System.out.println();
        return matrix;
    }
    
    public void DisplayMatrix(double[][] matrix, JTextArea Text) {
        if (DEBUG) {
            System.out.println("Displaying Matrix");
        }
        String result = "";
        for (double[] matrix1 : matrix) {
            for (int j = 0; j < matrix1.length; j++) {
                result = result.concat(String.valueOf(matrix1[j]) + "\t");
            }
            result = result.concat("\n");
        }
        Text.setText(result);
    }
    
    public double[][] AddMatrix(double[][] a, double[][] b, JLabel statusBar) throws Exception {
        int sizeA = a.length;
        int sizeB = b.length;
        if (sizeA != sizeB) {
            statusBar.setText("Matrix Size Mismatch");
        }

        double matrix[][] = new double[sizeA][sizeA];

        for (int i = 0; i < sizeA; i++) {
            for (int j = 0; j < sizeB; j++) {
                matrix[i][j] = a[i][j] + b[i][j];
            }
        }
        return matrix;
    }
    
    public double[][] SubABMatrix(double[][] a, double[][] b, JLabel statusBar) throws Exception {
        int sizeA = a.length;
        int sizeB = b.length;
        if (sizeA != sizeB) {
            statusBar.setText("Matrix Size Mismatch");
        }

        double matrix[][] = new double[sizeA][sizeA];

        for (int i = 0; i < sizeA; i++) {
            for (int j = 0; j < sizeB; j++) {
                matrix[i][j] = a[i][j] - b[i][j];
            }
        }
        return matrix;
    }
    
    public double[][] SubBAMatrix(double[][] a, double[][] b, JLabel statusBar) throws Exception {
        int sizeA = a.length;
        int sizeB = b.length;
        if (sizeA != sizeB) {
            statusBar.setText("Matrix Size Mismatch");
        }

        double matrix[][] = new double[sizeA][sizeA];

        for (int i = 0; i < sizeA; i++) {
            for (int j = 0; j < sizeB; j++) {
                matrix[i][j] = b[i][j] - a[i][j];
            }
        }
        return matrix;
    }
    
    public double[][] MultiplyMatrix(double[][] a, double[][] b, JLabel statusBar) throws Exception {

        if (a[0].length != b.length) {
            //statusBar.setText("Matrices incompatible for multiplication. i.e: Matrix Size Mismatch");
            throw new Exception("Matrices incompatible for multiplication");
        }
        double matrix[][] = new double[a.length][b[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[i].length; j++) {
                matrix[i][j] = 0;
            }
        }

        //cycle through answer matrix
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = calculateRowColumnProduct(a, i, b, j);
            }
        }
        return matrix;
    }

    public double calculateRowColumnProduct(double[][] a, int row, double[][] b, int col) {
        double product = 0;
        for (int i = 0; i < a[row].length; i++) {
            product += a[row][i] * b[i][col];
        }
        return product;
    }
    
    public double[][] Transpose(double[][] a) {
        if (INFO) {
            System.out.println("Performing Transpose...");
        }
        double matrix[][] = new double[a[0].length][a.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                matrix[j][i] = a[i][j];
            }
        }
        return matrix;
    }
    
    public double Determinant(double[][] matrix) {
        if (INFO) {
            System.out.println("Getting Determinant...");
        }
        int tms = matrix.length;
        double det = 1;
        matrix = UpperTriangle(matrix);
        for (int i = 0; i < tms; i++) {
            det = det * matrix[i][i];
        } // multiply down diagonal
        det = det * iDF; // adjust w/ determinant factor
        if (INFO) {
            System.out.println("Determinant: " + det);
        }
        return det;
    }
    
    public double[][] UpperTriangle(double[][] m) {
        if (INFO) {
            System.out.println("Converting to Upper Triangle...");
        }
        double f1 = 0;
        double temp = 0;
        int tms = m.length; // get This Matrix Size
        int v = 1;
        iDF = 1;
        for (int col = 0; col < tms - 1; col++) {
            for (int row = col + 1; row < tms; row++) {
                v = 1;
                outahere:
                while (m[col][col] == 0) // check if 0 in diagonal
                { // if so switch until not
                    if (col + v >= tms) // check if switched all rows
                    {
                        iDF = 0;
                        break outahere;
                    } else {
                        for (int c = 0; c < tms; c++) {
                            temp = m[col][c];
                            m[col][c] = m[col + v][c]; // switch rows
                            m[col + v][c] = temp;
                        }
                        v++; // count row switchs
                        iDF = iDF * -1; // each switch changes determinant
                        // factor
                    }
                }
                if (m[col][col] != 0) {
                    if (DEBUG) {
                        System.out.println("tms = " + tms + "   col = " + col
                                + "   row = " + row);
                    }

                    try {
                        f1 = (-1) * m[row][col] / m[col][col];
                        for (int i = col; i < tms; i++) {
                            m[row][i] = f1 * m[col][i] + m[row][i];
                        }
                    } catch (Exception e) {
                        System.out.println("Still Here!!!");
                    }

                }

            }
        }
        return m;
    }
    
    public double[][] Adjoint(double[][] a) throws Exception {
        if (INFO) {
            System.out.println("Performing Adjoint...");
        }
        int tms = a.length;
        double m[][] = new double[tms][tms];
        int ii, jj, ia, ja;
        double det;
        for (int i = 0; i < tms; i++) {
            for (int j = 0; j < tms; j++) {
                ia = ja = 0;
                double ap[][] = new double[tms - 1][tms - 1];
                for (ii = 0; ii < tms; ii++) {
                    for (jj = 0; jj < tms; jj++) {

                        if ((ii != i) && (jj != j)) {
                            ap[ia][ja] = a[ii][jj];
                            ja++;
                        }
                    }
                    if ((ii != i) && (jj != j)) {
                        ia++;
                    }
                    ja = 0;
                }
                det = Determinant(ap);
                m[i][j] = (double) Math.pow(-1, i + j) * det;
            }
        }
        m = Transpose(m);
        return m;
    }
    
    public double[][] Inverse(double[][] a, JLabel statusBar) throws Exception {
        if (INFO) {
            System.out.println("Performing Inverse...");
        }
        int tms = a.length;
        double m[][] = new double[tms][tms];
        double mm[][] = Adjoint(a);
        double det = Determinant(a);
        double dd = 0;
        if (det == 0) {
            statusBar.setText("Determinant Equals 0, Not Invertible");
            if (INFO) {
                System.out.println("Determinant Equals 0, Not Invertible");
            }
        } else {
            dd = 1 / det;
        }
        for (int i = 0; i < tms; i++) {
            for (int j = 0; j < tms; j++) {
                m[i][j] = dd * mm[i][j];
            }
        }
        return m;
    }
    
}
