package mx.edu.itses.KLDM.MetodosNumericos.web;

import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import mx.edu.itses.KLDM.MetodosNumericos.domain.Biseccion;
import mx.edu.itses.KLDM.MetodosNumericos.domain.ReglaFalsa;
import mx.edu.itses.KLDM.MetodosNumericos.services.Funciones;
import mx.edu.itses.KLDM.MetodosNumericos.services.UnidadIIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class Unit2Controller {

    @Autowired
    private UnidadIIService bisectionService;
     @Autowired 
        private UnidadIIService reglafalsaService;


    @GetMapping("unit2/formbisection")
    public String formBisection(Model model) {

        Biseccion bisection = new Biseccion();

        model.addAttribute("bisection", bisection);

        return "unit2/bisection/formbisection";
    }

    @PostMapping("unit2/solvebisection")
    public String solvebisection(Biseccion bisection, Model model) {
        // double valorFX = Funciones.Ecuacion(bisection.getFX(), bisection.getXL());
        //  log.info("Valor de FX" + valorFX );
        var solveBisection = bisectionService.AlgoritmoBiseccion(bisection);

//log.info("Arreglo"+ solveBisection);
        model.addAttribute("solveBisection", solveBisection);
        return "unit2/bisection/solvebisection";
    }

// REGLA FALSA
  @GetMapping("unit2/formReglaFalsa")
    public String formReglaFalsa(Model model) {

          ReglaFalsa reglafalsa = new ReglaFalsa();

        model.addAttribute("reglafalsa", reglafalsa);
        return "unit2/reglafalsa/formReglaFalsa";
    }
@PostMapping("unit2/solveReglaFalsa")
public String solveReglaFalsa(ReglaFalsa reglafalsa, Model model) {
    var solveReglaFalsa = reglafalsaService.AlgoritmoReglaFalsa(reglafalsa);
    // CAMBIO: usar "solveReglaFalsa" en lugar de "solvereglafalsa"
    model.addAttribute("solveReglaFalsa", solveReglaFalsa);
    return "unit2/reglafalsa/solveReglaFalsa";
}
}
