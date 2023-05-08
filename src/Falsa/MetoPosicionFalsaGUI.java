package falsa;

import Vista.frmPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.script.*;

public class MetoPosicionFalsaGUI extends JFrame implements ActionListener {
  frmPrincipal VistaPrincipal;
    
  private static final long serialVersionUID = 1L;
  
  private JTextField txtA, txtB, txtTol;
  private JButton btnCalcular;
  
  public MetoPosicionFalsaGUI() {

  }

    public MetoPosicionFalsaGUI(frmPrincipal VistaPrincipal) {
        this.VistaPrincipal = VistaPrincipal;
        
        this.VistaPrincipal.setVisible(true);
    }
  
  

  @Override
  public void actionPerformed(ActionEvent e) {
    double a = Double.parseDouble(this.VistaPrincipal.txtX1.getText());
    double b = Double.parseDouble(this.VistaPrincipal.txtXu.getText());
    double tol = Double.parseDouble(this.VistaPrincipal.txtTol.getText());

    String funcion = this.VistaPrincipal.txtFuncion.getText();
    Funcion f = new Funcion(funcion);
    
    double x1, x2, f1, f2, xm, fm, error;
    int iter = 0;
    
    do {
      f1 = f.eval(a);
      f2 = f.eval(b);
      x1 = a;
      x2 = b;
      xm = (f2 * x1 - f1 * x2) / (f2 - f1);
      fm = f.eval(xm);
      error = Math.abs((xm - x2) / xm);
      if (fm * f1 > 0) {
        a = xm;
      } else {
        b = xm;
      }
      iter++;
    } while (error > tol);
    
    System.out.println("Raíz: " + xm);
    System.out.println("Iteraciones: " + iter);
  }
   
  class Funcion {
    private String funcion;

    public Funcion(String funcion) {
        this.funcion = funcion;
    }

    public double eval(double x) {
    String expr = funcion.replaceAll("x", String.valueOf(x));
    Object result = new Object() {
        public Object eval() {
            try {
                return new javax.script.ScriptEngineManager().getEngineByName("JavaScript").eval(expr + "");
            } catch (Exception e) {
                return Double.NaN;
            }
        }
    }.eval();

    if (result instanceof Number) {
        return ((Number) result).doubleValue();
    } else {
        return Double.NaN;
    }
    }
  }
}
