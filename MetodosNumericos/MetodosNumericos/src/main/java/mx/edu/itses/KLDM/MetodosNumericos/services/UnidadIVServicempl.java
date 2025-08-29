package mx.edu.itses.KLDM.MetodosNumericos.services;

// CORREGIR ESTOS IMPORTS - Los que tienes están mal
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import mx.edu.itses.KLDM.MetodosNumericos.domain.DDNewton;
import mx.edu.itses.KLDM.MetodosNumericos.domain.Lagrange;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UnidadIVServicempl implements UnidadIVService { 

    @Override 
    public DDNewton AlgoritmoDDNewton(DDNewton modelDDNewton) {
        ArrayList<String> pasos = new ArrayList<>();
        ArrayList<Double> coeficientes = new ArrayList<>();
        
        ArrayList<Double> puntosX = modelDDNewton.getPuntosX();
        ArrayList<Double> puntosY = modelDDNewton.getPuntosY();
        
        int numPuntos = puntosX.size();
        
        double valorX = modelDDNewton.getValorX();
        
        pasos.add("=== DIFERENCIAS DIVIDIDAS DE NEWTON ===");
        pasos.add("Puntos dados:");
        for (int i = 0; i < numPuntos; i++) {
            pasos.add("(" + puntosX.get(i) + ", " + puntosY.get(i) + ")");
        }
        pasos.add("Valor a evaluar: x = " + valorX);
        pasos.add("");
        
        try {
            // Crear tabla de diferencias divididas
            double[][] tabla = new double[numPuntos][numPuntos];
            
            // Primera columna: valores de Y
            for (int i = 0; i < numPuntos; i++) {
                tabla[i][0] = puntosY.get(i);
            }
            
            pasos.add("Tabla de Diferencias Divididas:");
            pasos.add("Columna 0 (f[xi]): " + formatearColumna(tabla, 0, numPuntos));
            
            // Calcular diferencias divididas
            for (int j = 1; j < numPuntos; j++) {
                for (int i = 0; i < numPuntos - j; i++) {
                    double numerador = tabla[i + 1][j - 1] - tabla[i][j - 1];
                    double denominador = puntosX.get(i + j) - puntosX.get(i);
                    tabla[i][j] = numerador / denominador;
                }
                pasos.add("Columna " + j + " (f[xi,...,xi+" + j + "]): " + formatearColumna(tabla, j, numPuntos - j));
            }
            
            // Los coeficientes son la primera fila de la tabla
            StringBuilder polinomio = new StringBuilder();
            polinomio.append("P(x) = ").append(String.format("%.6f", tabla[0][0]));
            coeficientes.add(tabla[0][0]);
            
            for (int i = 1; i < numPuntos; i++) {
                coeficientes.add(tabla[0][i]);
                
                if (tabla[0][i] >= 0) {
                    polinomio.append(" + ").append(String.format("%.6f", tabla[0][i]));
                } else {
                    polinomio.append(" ").append(String.format("%.6f", tabla[0][i]));
                }
                
                // Agregar los factores (x - xi)
                for (int j = 0; j < i; j++) {
                    polinomio.append("(x - ").append(puntosX.get(j)).append(")");
                }
            }
            
            pasos.add("");
            pasos.add("Polinomio de Newton:");
            pasos.add(polinomio.toString());
            pasos.add("");
            
            // Evaluar el polinomio en valorX
            double resultado = tabla[0][0];  // Primer término
            double terminoActual = 1.0;
            
            pasos.add("Evaluando P(" + valorX + "):");
            pasos.add("Término 0: " + String.format("%.6f", tabla[0][0]));
            
            for (int i = 1; i < numPuntos; i++) {
                terminoActual *= (valorX - puntosX.get(i - 1));
                double termino = tabla[0][i] * terminoActual;
                resultado += termino;
                
                pasos.add("Término " + i + ": " + String.format("%.6f", tabla[0][i]) + 
                         " × " + String.format("%.6f", terminoActual) + " = " + String.format("%.6f", termino));
            }
            
            pasos.add("");
            pasos.add("Resultado final: P(" + valorX + ") = " + String.format("%.8f", resultado));
            
            // Establecer resultados
            modelDDNewton.setTablaDD(tabla);
            modelDDNewton.setResultado(resultado);
            modelDDNewton.setPolinomio(polinomio.toString());
            modelDDNewton.setCoeficientes(coeficientes);
            
            
            log.info("Newton DD completado: P(" + valorX + ") = " + resultado);
            
        } catch (Exception e) {
            log.error("Error en Newton DD: " + e.getMessage());
            pasos.add("Error: " + e.getMessage());
            throw new RuntimeException("Error en Diferencias Divididas de Newton: " + e.getMessage());
        }
        
        return modelDDNewton;
    }
    
    // Método auxiliar para formatear columnas de la tabla
    private String formatearColumna(double[][] tabla, int columna, int filas) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < filas; i++) {
            if (i > 0) sb.append(", ");
            sb.append(String.format("%.6f", tabla[i][columna]));
        }
        sb.append("]");
        return sb.toString();
    }

   @Override
public Lagrange AlgoritmoLagrange(Lagrange modelLagrange) {
    ArrayList<String> pasos = new ArrayList<>();
    ArrayList<Double> coeficientes = new ArrayList<>();
    ArrayList<String> terminosLagrange = new ArrayList<>();

    ArrayList<Double> puntosX = modelLagrange.getPuntosX();
    ArrayList<Double> puntosY = modelLagrange.getPuntosY();

    int numPuntos = puntosX.size();
    double valorX = modelLagrange.getValorX();

    pasos.add("=== POLINOMIO DE INTERPOLACIÓN DE LAGRANGE ===");
    pasos.add("Puntos dados:");
    for (int i = 0; i < numPuntos; i++) {
        pasos.add("(" + puntosX.get(i) + ", " + puntosY.get(i) + ")");
    }
    pasos.add("Valor a evaluar: x = " + valorX);
    pasos.add("");

    try {
        double resultado = 0.0;
        StringBuilder polinomio = new StringBuilder("P(x) = ");

        // Calcular cada término de Lagrange
        for (int i = 0; i < numPuntos; i++) {
            double Li_val = 1.0; // para evaluación en valorX
            StringBuilder Li_str = new StringBuilder();

            for (int j = 0; j < numPuntos; j++) {
                if (i != j) {
                    double denominador = puntosX.get(i) - puntosX.get(j);
                    Li_val *= (valorX - puntosX.get(j)) / denominador; // cálculo numérico
                    Li_str.append(" * (x - ").append(puntosX.get(j)).append(")/")
                          .append(String.format("%.6f", denominador));
                }
            }

            // Valor numérico del término
            double terminoEval = puntosY.get(i) * Li_val;
            resultado += terminoEval;
            coeficientes.add(terminoEval);

            // Guardar explicación
            terminosLagrange.add("L" + i + "(x) = " + puntosY.get(i) + Li_str.toString());
            pasos.add("Término L" + i + "(x): " + puntosY.get(i) + Li_str.toString());
            pasos.add("Valor de L" + i + "(" + valorX + ") = " + String.format("%.6f", terminoEval));

            // Construcción simbólica del polinomio
            if (i > 0) polinomio.append(" + ");
            polinomio.append(String.format("%.6f", puntosY.get(i))).append(Li_str);
        }

        pasos.add("");
        pasos.add("Suma de términos: P(" + valorX + ") = " + String.format("%.8f", resultado));
        pasos.add("");
        pasos.add("Polinomio de Lagrange completo:");
        pasos.add(polinomio.toString());

        // Establecer resultados
        modelLagrange.setResultado(resultado);
        modelLagrange.setPolinomio(polinomio.toString());
        modelLagrange.setCoeficientes(coeficientes);
        modelLagrange.setTerminosLagrange(terminosLagrange);

        log.info("Lagrange completado: P(" + valorX + ") = " + resultado);

    } catch (Exception e) {
        log.error("Error en Lagrange: " + e.getMessage());
        pasos.add("Error: " + e.getMessage());
        throw new RuntimeException("Error en Polinomio de Lagrange: " + e.getMessage());
    }

    return modelLagrange;
}
}