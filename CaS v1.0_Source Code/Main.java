
package CaS;

/*
    Author Bilal (K13-2314)
                             */

public class Main {
    
    public static void main(String args[]) {
        
        Splash S = new Splash();
        
        S.setVisible(true);
        try {
            Thread.sleep(3400);                 // 1000 milliseconds is one second.
        } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
        }
        S.dispose();
        
        new CaS().setVisible(true);
        
    }
}
