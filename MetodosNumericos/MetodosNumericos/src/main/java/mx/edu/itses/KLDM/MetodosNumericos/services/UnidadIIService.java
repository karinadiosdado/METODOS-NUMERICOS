package mx.edu.itses.KLDM.MetodosNumericos.services;

import java.util.ArrayList;
import mx.edu.itses.KLDM.MetodosNumericos.domain.Biseccion;
import mx.edu.itses.KLDM.MetodosNumericos.domain.NewtonRaphson;
import mx.edu.itses.KLDM.MetodosNumericos.domain.PuntoFijo;
import mx.edu.itses.KLDM.MetodosNumericos.domain.ReglaFalsa;
import mx.edu.itses.KLDM.MetodosNumericos.domain.Secante;
import mx.edu.itses.KLDM.MetodosNumericos.domain.SecanteModificado;

public interface UnidadIIService {

    public ArrayList<Biseccion> AlgoritmoBiseccion(Biseccion biseccion);

    public ArrayList<ReglaFalsa> AlgoritmoReglaFalsa(ReglaFalsa reglafalsa);

        public ArrayList<PuntoFijo>  AlgoritmoPuntoFijo(PuntoFijo puntofijo);
 ArrayList<NewtonRaphson> AlgoritmoNewtonRaphson(NewtonRaphson newtonRaphson);
   ArrayList<Secante> AlgoritmoSecante(Secante secante);
   ArrayList< SecanteModificado> AlgoritmoSecanteModificado( SecanteModificado  secantemodificado);
}
