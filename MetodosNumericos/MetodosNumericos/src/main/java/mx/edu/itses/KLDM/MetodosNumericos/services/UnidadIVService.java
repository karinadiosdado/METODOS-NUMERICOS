/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mx.edu.itses.KLDM.MetodosNumericos.services;

import mx.edu.itses.KLDM.MetodosNumericos.domain.DDNewton;
import mx.edu.itses.KLDM.MetodosNumericos.domain.Lagrange;

/**
 *
 * @author karin
 */
public interface UnidadIVService {
DDNewton AlgoritmoDDNewton(DDNewton modelDDNewton);
    Lagrange AlgoritmoLagrange(Lagrange modelLagrange);

}
