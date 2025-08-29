
package mx.edu.itses.KLDM.MetodosNumericos.domain;

import java.util.ArrayList;
import lombok.Data;

@Data
public class DDNewton {
      private int orden;  // Orden del polinomio (1, 2, 3, 4)
    private ArrayList<Double> puntosX;  // Puntos X conocidos
    private ArrayList<Double> puntosY;  // Puntos Y conocidos (f(x))
    private double valorX;  // Valor de X para evaluar
    private double resultado;  // f(x) interpolado
    private double[][] tablaDD;  // Tabla de diferencias divididas
    private String polinomio;  // Expresión del polinomio
    private ArrayList<Double> coeficientes;  // Coeficientes del polinomio
    
}
