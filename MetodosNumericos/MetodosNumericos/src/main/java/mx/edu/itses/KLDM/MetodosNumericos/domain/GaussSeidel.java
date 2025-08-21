/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.itses.KLDM.MetodosNumericos.domain;

import java.util.ArrayList;
import lombok.Data;

@Data
public class GaussSeidel {
     private int MN;
    private ArrayList<Double> matrizA;
    private ArrayList<Double> vectorB;
    private ArrayList<Double> vectorX;
    private ArrayList<Double> vectorInicial;
    private ArrayList<String> pasos;
    private double tolerancia;
    private int maxIteraciones;
    private int iteracionesRealizadas;
    private boolean convergio; 
}
