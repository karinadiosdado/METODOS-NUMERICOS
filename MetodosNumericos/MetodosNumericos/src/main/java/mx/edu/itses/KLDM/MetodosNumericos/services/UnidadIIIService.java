// este es de regla de cramer
package mx.edu.itses.KLDM.MetodosNumericos.services;

import mx.edu.itses.KLDM.MetodosNumericos.domain.Gauss;
import mx.edu.itses.KLDM.MetodosNumericos.domain.GaussJordan;
import mx.edu.itses.KLDM.MetodosNumericos.domain.ReglaCramer;


public interface UnidadIIIService {
   public ReglaCramer AlgoritmoReglaCramer(ReglaCramer modelCramer);
   public Gauss AlgoritmoGauss(Gauss modelGauss);
   GaussJordan AlgoritmoGaussJordan(GaussJordan modelGaussJordan);
}
