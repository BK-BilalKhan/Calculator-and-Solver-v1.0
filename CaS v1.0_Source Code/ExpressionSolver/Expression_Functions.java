package CaS.ExpressionSolver;

import java.util.*;

public class Expression_Functions {
    /*
     * Strings used for storing expression.
     */

    private String expression, x;

    /*
     * Term evaluator for number literals.
     */
    double term() {
        double ans = 0;
        StringBuffer temp = new StringBuffer();
        while (expression.length() > 0 && Character.isDigit(expression.charAt(0))) {
            temp.append(Integer.parseInt("" + expression.charAt(0)));
            expression = expression.substring(1);
        }
        if (expression.length() > 0 && expression.charAt(0) == '.') {
            temp.append('.');
            expression = expression.substring(1);
            while (expression.length() > 0 && Character.isDigit(expression.charAt(0))) {
                temp.append(Integer.parseInt("" + expression.charAt(0)));
                expression = expression.substring(1);
            }
        }
        if (expression.length() > 0 && (expression.charAt(0) == 'e' || expression.charAt(0) == 'E')) {
            temp.append('e');
            expression = expression.substring(1);
            temp.append(expression.charAt(0));
            expression = expression.substring(1);
            while (expression.length() > 0 && Character.isDigit(expression.charAt(0))) {
                temp.append(Integer.parseInt("" + expression.charAt(0)));
                expression = expression.substring(1);
            }
        }
        ans = Double.valueOf(temp.toString()).doubleValue();
        return ans;
    }

    /*
     * Parentheses solver.
     */
    double paren() {
        double ans;
        if (expression.charAt(0) == '(') {
            expression = expression.substring(1);
            ans = add();
            expression = expression.substring(1);
        } else {
            ans = term();
        }
        return ans;
    }

    /*
     * Exponentiation solver.
     */
    double exp() {
        boolean neg = false;
        if (expression.charAt(0) == '-') {
            neg = true;
            expression = expression.substring(1);
        }
        double ans = paren();
        while (expression.length() > 0) {
            if (expression.charAt(0) == '^') {
                expression = expression.substring(1);
                boolean expNeg = false;
                if (expression.charAt(0) == '-') {
                    expNeg = true;
                    expression = expression.substring(1);
                }
                double e = paren();
                if (ans < 0) {		// if it'expression negative
                    double x = 1;
                    if (Math.ceil(e) == e) {	// only raise to an integer
                        if (expNeg) {
                            e *= -1;
                        }
                        if (e == 0) {
                            ans = 1;
                        } else if (e > 0) {
                            for (int i = 0; i < e; i++) {
                                x *= ans;
                            }
                        } else {
                            for (int i = 0; i < -e; i++) {
                                x /= ans;
                            }
                        }
                        ans = x;
                    } else {
                        ans = Math.log(-1);	// otherwise make it NaN
                    }
                } else if (expNeg) {
                    ans = Math.exp(-e * Math.log(ans));
                } else {
                    ans = Math.exp(e * Math.log(ans));
                }
            } else {
                break;
            }
        }
        if (neg) {
            ans *= -1;
        }
        return ans;
    }

    /*
     * Trigonometric function solver.
     */
    double trig() {
        double ans = 0;
        boolean found = false;
        if (expression.indexOf("sin") == 0) {
            expression = expression.substring(3);
            ans = Math.sin(trig());
            found = true;
        } else if (expression.indexOf("cos") == 0) {
            expression = expression.substring(3);
            ans = Math.cos(trig());
            found = true;
        } else if (expression.indexOf("tan") == 0) {
            expression = expression.substring(3);
            ans = Math.tan(trig());
            found = true;
        } else if (expression.indexOf("log") == 0) {
            expression = expression.substring(3);
            ans = Math.log10(trig());
            found = true;
        } else if (expression.indexOf("ln") == 0) {
            expression = expression.substring(2);
            ans = Math.log(trig());
            found = true;
        } else if (expression.indexOf("sqrt") == 0) {
            expression = expression.substring(4);
            ans = Math.sqrt(trig());
            found = true;
        }
        if (!found) {
            ans = exp();
        }
        return ans;
    }

    /*
     * Multiplication, division expression solver.
     */
    double mul() {
        double ans = trig();
        if (expression.length() > 0) {
            while (expression.length() > 0) {
                if (expression.charAt(0) == '*' || expression.charAt(0) == '×') {
                    expression = expression.substring(1);
                    ans *= trig();
                } else if (expression.charAt(0) == '/' || expression.charAt(0) == '÷') {
                    expression = expression.substring(1);
                    ans /= trig();
                } else if (expression.charAt(0) == '%' || expression.charAt(0) == '%') {
                    expression = expression.substring(1);
                    ans %= trig();
                } else {
                    break;
                }
            }
        }
        return ans;
    }

    /*
     * Addition, subtraction expression solver.
     */
    private double add() {
        double ans = mul();
        while (expression.length() > 0) {
            if (expression.charAt(0) == '+') {
                expression = expression.substring(1);
                ans += mul();
            } else if (expression.charAt(0) == '-') {
                expression = expression.substring(1);
                ans -= mul();
            } else {
                break;
            }
        }
        //System.out.println(ans);
        return ans;
    }

    /*
     * Factorial expression solver.
     */
    private String factorial(String expression) {
        int i = 0;
        while (i < expression.length()) {
            if (expression.charAt(i) == '!') {
                int j = i - 1;
                String tmp = "";
                expression = expression.substring(0, i) + expression.substring(i + 1);
                while (expression.charAt(j) >= '0' && expression.charAt(j) <= '9') {
                    tmp += expression.charAt(j);
                    expression = this.replace(expression, j, ' ');
                    //System.out.println(expression.charAt(j) + " " + expression);
                    j--;
                }
                Double result = Factorial(Double.parseDouble(tmp));
                expression = expression.substring(0, j + 1) + result + expression.substring(j + 2);
                //System.out.println(tmp);
            }
            i++;
        }
        //System.out.println("Result" + expression);
        return String.valueOf(expression);
    }

    private double Factorial(double n) {
        try {
            if (n == 0) {
                return 1;
            } else {
                return n * Factorial(n - 1);
            }
        } catch (Exception e) {
            return 0;
        }
    }

    /*
     * Public access method to evaluate this expression.
     */
    public double evaluate() {
        expression = x;
        double last = add();
        return last;
    }

    /*
     * Creates new Expression.
     */
    public Expression_Functions(String expression) {
        // remove white space, assume only spaces or tabs
        //System.out.println(expression);
        expression = factorial(expression);         // Solve Factorial
        StringBuffer b = new StringBuffer();
        StringTokenizer t = new StringTokenizer(expression, " ");
        while (t.hasMoreElements()) {
            b.append(t.nextToken());
        }
        t = new StringTokenizer(b.toString(), "\t");
        b = new StringBuffer();
        while (t.hasMoreElements()) {
            b.append(t.nextToken());
        }

        x = b.toString();
        //System.out.println(x);
    }

    /*
     * The String value of this Expression.
     */
    public String toString() {
        return x.intern();
    }

    public String replace(String str, int index, char replace) {
        if (str == null) {
            return str;
        } else if (index < 0 || index >= str.length()) {
            return str;
        }
        char[] chars = str.toCharArray();
        chars[index] = replace;
        return String.valueOf(chars);
    }

//    /*
//     * Test our Expression class by evaluating the command-line
//     * argument and then returning.
//     */
//    public static void main(String[] args) {
//        Expression_Functions e = new Expression_Functions("3 + 4^2*7.5E-1*sin(22)");
//        System.out.println(e + " = " + e.evaluate());
//    }
}
