
package mx.edu.itses.KLDM.MetodosNumericos.domain;

import lombok.Data;

@Data  
public class PuntoFijo {
    private String FX;// funcion ecvaluar
    private double Xi;
    private double GXi;
    private int IteracionesMaximas;
      private double Ea;  
          private String gx; 
}
