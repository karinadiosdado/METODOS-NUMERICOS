package mx.edu.itses.KLDM.MetodosNumericos.services;

import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import mx.edu.itses.KLDM.MetodosNumericos.domain.Biseccion;
import mx.edu.itses.KLDM.MetodosNumericos.domain.NewtonRaphson;
import mx.edu.itses.KLDM.MetodosNumericos.domain.PuntoFijo;
import mx.edu.itses.KLDM.MetodosNumericos.domain.ReglaFalsa;
import mx.edu.itses.KLDM.MetodosNumericos.domain.Secante;
import mx.edu.itses.KLDM.MetodosNumericos.domain.SecanteModificado;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UnidadIIServiceImpl implements UnidadIIService {

    @Override
    public ArrayList<Biseccion> AlgoritmoBiseccion(Biseccion biseccion) {
        ArrayList<Biseccion> respuesta = new ArrayList<>();
        double XL, XU, XRa, XRn, FXL, FXU, FXR, Ea;

        XL = biseccion.getXL();
        XU = biseccion.getXU();
        XRa = 0;
        Ea = 100;
        // Verificamos que en el intervalo definido haya un cambio de signo
        FXL = Funciones.Ecuacion(biseccion.getFX(), XL);
        FXU = Funciones.Ecuacion(biseccion.getFX(), XU);
        if (FXL * FXU < 0) {
            for (int i = 1; i <= biseccion.getIteracionesMaximas(); i++) {
                XRn = (XL + XU) / 2;
                FXL = Funciones.Ecuacion(biseccion.getFX(), XL);
                FXU = Funciones.Ecuacion(biseccion.getFX(), XU);
                FXR = Funciones.Ecuacion(biseccion.getFX(), XRn);
                if (i != 1) {
                    Ea = Funciones.ErrorRelativo(XRn, XRa);
                }
                Biseccion renglon = new Biseccion();
                renglon.setXL(XL);
                renglon.setXU(XU);
                renglon.setXR(XRn);
                renglon.setFXL(FXL);
                renglon.setFXU(FXU);
                renglon.setFXR(FXR);
                renglon.setEa(Ea);
                if (FXL * FXR < 0) {
                    XU = XRn;
                } else if (FXL * FXR > 0) {
                    XL = XRn;
                } else if (FXL * FXR == 0) {
                    break;
                }
                XRa = XRn;
                respuesta.add(renglon);
                if (Ea <= biseccion.getEa()) {
                    break;
                }
            }
        } else {
            Biseccion renglon = new Biseccion();
            //renglon.setIntervaloInvalido(true);
            respuesta.add(renglon);
        }

        return respuesta;
    }

    @Override

  public ArrayList<ReglaFalsa> AlgoritmoReglaFalsa(ReglaFalsa reglafalsa) {
    ArrayList<ReglaFalsa> respuestaReglaFalsa = new ArrayList<>();
    double XL, XU, XRa = 0, XRn, FXL, FXU, FXR, Ea = 100;
    XL = reglafalsa.getXL();
    XU = reglafalsa.getXU();

    FXL = Funciones.Ecuacion(reglafalsa.getFX(), XL);
    FXU = Funciones.Ecuacion(reglafalsa.getFX(), XU);

    if (FXL * FXU < 0) {
        for (int i = 1; i <= reglafalsa.getIteracionesMaximas(); i++) {
            // Fórmula de Regla Falsa
            XRn = XU - (FXU * (XL - XU)) / (FXL - FXU);
            FXR = Funciones.Ecuacion(reglafalsa.getFX(), XRn);

            if (i != 1) {
                Ea = Funciones.ErrorRelativo(XRn, XRa);
            }

            ReglaFalsa renglon = new ReglaFalsa();
            renglon.setIteracionesMaximas(i);
            renglon.setXL(XL);
            renglon.setXU(XU);
            renglon.setXR(XRn);
            renglon.setFXL(FXL);
            renglon.setFXU(FXU);
            renglon.setFXR(FXR);
            renglon.setEa(Ea);

            // Calcular el producto para determinar qué regla aplicar
            double productoFXL_FXR = FXL * FXR;
            renglon.setProductoFXL_FXR(productoFXL_FXR);

            // Aplicar las reglas de Cramer 2, 3 y 4
            if (productoFXL_FXR < 0) {
                // Regla 2: La raíz está entre XL y XR
                XU = XRn;
                FXU = FXR;
            } else if (productoFXL_FXR > 0) {
                // Regla 3: La raíz está entre XR y XU
                XL = XRn;
                FXL = FXR;
            } else {
                // Regla 4: FXR = 0, se encontró la raíz exacta
                renglon.setEa(0.0); 
                XRa = XRn;
                respuestaReglaFalsa.add(renglon);
                break; // Terminar el algoritmo ya que se encontró la raíz exacta
            }

            XRa = XRn;
            respuestaReglaFalsa.add(renglon);

            // Verificar si se alcanzó la tolerancia deseada
            if (Ea <= reglafalsa.getEa()) {
                break;
            }
        }
    } else {
        // No hay cambio de signo, no se puede aplicar el método
        ReglaFalsa error = new ReglaFalsa();
        error.setIteracionesMaximas(-1); 
        respuestaReglaFalsa.add(error);
    }
    return respuestaReglaFalsa;
}

    @Override

    public ArrayList<PuntoFijo> AlgoritmoPuntoFijo(PuntoFijo puntofijo) {
        ArrayList<PuntoFijo> respuestaPuntoFijo = new ArrayList<>();
        double Xi, XiAnterior = 0, GXi, Ea = 100;

        // Obtener el valor inicial
        Xi = puntofijo.getXi();

        // Iteraciones del método de punto fijo
        for (int i = 1; i <= puntofijo.getIteracionesMaximas(); i++) {
            // Evaluar g(xi) - aquí necesitas la función g(x)
            GXi = Funciones.Ecuacion(puntofijo.getFX(), Xi);

            // Calcular error relativo (excepto en la primera iteración)
            if (i != 1) {
                Ea = Funciones.ErrorRelativo(GXi, XiAnterior);
            }

            PuntoFijo renglon = new PuntoFijo();
            renglon.setIteracionesMaximas(i);
            renglon.setXi(Xi);
            renglon.setGXi(GXi);
            renglon.setEa(Ea);

            respuestaPuntoFijo.add(renglon);

            // Verificar criterio de convergencia
            if (i > 1 && Ea <= puntofijo.getEa()) {  //
                break;
            }

            // Preparar para la siguiente iteración
            XiAnterior = Xi;
            Xi = GXi;  // Para punto fijo: xi+1 = g(xi)
        }

        return respuestaPuntoFijo;
    }

    @Override
    public ArrayList<NewtonRaphson> AlgoritmoNewtonRaphson(NewtonRaphson newtonraphson) {
        ArrayList<NewtonRaphson> respuesta = new ArrayList<>();

        double Xi = newtonraphson.getXi(); // valor inicial
        double Xi1 = 0;           // siguiente Xi
        double Ea = 100;            // error inicial
        double h = 0.0001; // paso pequeño para aproximar derivada

        int maxIteraciones = newtonraphson.getIteracionesMaximas();

        for (int i = 1; i <= maxIteraciones; i++) {
            double FXi = Funciones.Ecuacion(newtonraphson.getFX(), Xi);
            double FdXi = (Funciones.Ecuacion(newtonraphson.getFX(), Xi + h) - FXi) / h;
            if (FdXi == 0) {
                System.out.println("Derivada cercana a cero, deteniendo iteración");
                break;
            }
            // Xi+1
            Xi1 = Xi - (FXi / FdXi);

            // Calcular el error relativo aproximado
            Ea = Funciones.ErrorRelativo(Xi1, Xi);

            // Guardar datos de la iteración
            NewtonRaphson iteracion = new NewtonRaphson();
            iteracion.setXi(Xi);
            iteracion.setFXi(FXi);
            iteracion.setDFXi(String.valueOf(FdXi));
            iteracion.setXi1(Xi1);
            iteracion.setEa(Ea);
            iteracion.setIteracionesMaximas(i);

            respuesta.add(iteracion);

            // Verificar si el error ya está por debajo del deseado
            // Preparar para siguiente iteración
            Xi = Xi1;
        }

        return respuesta;

    }
    @Override
    public ArrayList<Secante> AlgoritmoSecante(Secante secante) {
        ArrayList<Secante> respuesta = new ArrayList<>();

        double Xi_1 = secante.getXi_1();
        double Xi = secante.getXi();
        double Xi1;
        double F_Xi_1, F_Xi, Ea = 100;

        int maxIteraciones = secante.getIteracionesMaximas();

        for (int i = 1; i <= maxIteraciones; i++) {
            F_Xi_1 = Funciones.Ecuacion(secante.getFX(), Xi_1);
            F_Xi = Funciones.Ecuacion(secante.getFX(), Xi);

            if ((F_Xi_1 - F_Xi) == 0) {
                System.out.println("Denominador cercano a cero, deteniendo iteración.");
                break;
            }

            Xi1 = Xi - (F_Xi * (Xi_1 - Xi)) / (F_Xi_1 - F_Xi);

            // Calcular error relativo
            if (i != 1) {
                Ea = Math.abs((Xi1 - Xi) / Xi1) * 100;
            }

            // Crear iteración
            Secante iter = new Secante();
            iter.setXi(Xi);
            iter.setXi_1(Xi_1);
            iter.setXi1(Xi1);
            iter.setFXi(F_Xi);
            iter.setF_Xi_1(F_Xi_1);
            iter.setEa(Ea);
            iter.setIteracionesMaximas(i);
            iter.setFX(secante.getFX());

            // Guardar resultados de esta iteración
            respuesta.add(iter);

            // Verificar si error deseado se alcanzó
            if (Ea <= secante.getEa()) {
                break;
            }

            // Desplazar valores para siguiente iteración
            Xi_1 = Xi;
            Xi = Xi1;
        }

        return respuesta;
    }

    @Override
    public ArrayList<SecanteModificado> AlgoritmoSecanteModificado(SecanteModificado secantemodificado) {
        ArrayList<SecanteModificado> respuesta = new ArrayList<>();

        double Xi = secantemodificado.getXi();
        double Xi1;
        double Ea = 100;
        int maxIteraciones = secantemodificado.getIteracionesMaximas();
        double sigma = secantemodificado.getSigma();

        for (int i = 1; i <= maxIteraciones; i++) {
            double deltaXi = sigma * Xi;
            double FXi = Funciones.Ecuacion(secantemodificado.getFX(), Xi);
            double FXiSigma = Funciones.Ecuacion(secantemodificado.getFX(), Xi + deltaXi);

            double denominador = FXiSigma - FXi;

            // 🔍 Imprimir depuración completa
            System.out.printf(
                    "Iteración %d:\n  Xi=%.6f\n  FXi=%.6f\n  FXi+deltaXi=%.6f\n  DeltaXi=%.6f\n  Denominador=%.6f\n",
                    i, Xi, FXi, FXiSigma, deltaXi, denominador
            );

            if (Double.isNaN(FXi) || Double.isNaN(FXiSigma)) {
                System.out.println(" Se detectó NaN en la evaluación de la función. Revisa la expresión ingresada.");
                break;
            }

            if (Math.abs(denominador) < 1e-8) {
                System.out.println(" Denominador muy pequeño, deteniendo para evitar división por cero.");
                break;
            }

            Xi1 = Xi - (deltaXi * FXi) / denominador;

            if (i != 1) {
                Ea = Funciones.ErrorRelativo(Xi1, Xi);
            }

            System.out.printf("  Xi+1=%.6f\n  Error=%.6f%%\n\n", Xi1, Ea);

            // Guardar resultados
            SecanteModificado iter = new SecanteModificado();
            iter.setXi(Xi);
            iter.setXi1(Xi1);
            iter.setFXi(FXi);
            iter.setFXiSigma(FXiSigma);
            iter.setEa(Ea);
            iter.setIteracionesMaximas(i);
            iter.setFX(secantemodificado.getFX());
            iter.setSigma(sigma);

            respuesta.add(iter);

            if (Ea <= secantemodificado.getEa()) {
                break;
            }

            Xi = Xi1;
        }

        return respuesta;
    }

}
