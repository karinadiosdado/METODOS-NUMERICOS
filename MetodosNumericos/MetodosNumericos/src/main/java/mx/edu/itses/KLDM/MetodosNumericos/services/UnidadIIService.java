package mx.edu.itses.KLDM.MetodosNumericos.services;

import java.util.ArrayList;
import mx.edu.itses.KLDM.MetodosNumericos.domain.Biseccion;
import mx.edu.itses.KLDM.MetodosNumericos.domain.NewtonRaphson;
import mx.edu.itses.KLDM.MetodosNumericos.domain.PuntoFijo;
import mx.edu.itses.KLDM.MetodosNumericos.domain.ReglaFalsa;

public interface UnidadIIService {

    public ArrayList<Biseccion> AlgoritmoBiseccion(Biseccion biseccion);

    public ArrayList<ReglaFalsa> AlgoritmoReglaFalsa(ReglaFalsa reglafalsa);

        public ArrayList<PuntoFijo>  AlgoritmoPuntoFijo(PuntoFijo puntofijo);
 ArrayList<NewtonRaphson> AlgoritmoNewtonRaphson(NewtonRaphson newtonRaphson);
}
