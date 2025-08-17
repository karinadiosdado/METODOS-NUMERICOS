package mx.edu.itses.KLDM.MetodosNumericos.web;

import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import mx.edu.itses.KLDM.MetodosNumericos.domain.Gauss;
import mx.edu.itses.KLDM.MetodosNumericos.domain.GaussJordan;
import mx.edu.itses.KLDM.MetodosNumericos.domain.ReglaCramer;
import mx.edu.itses.KLDM.MetodosNumericos.services.UnidadIIIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class Unit3Controller {

    @Autowired
    private UnidadIIIService unidadIIIsrv;
    // ReglaCramer

    @GetMapping("/unit3")
    public String index(Model model) {
        return "unit2/index";
    }

    @GetMapping("/unit2/formcramer")
    public String formCramer(Model model) {
        ReglaCramer modelCramer = new ReglaCramer();
        model.addAttribute("modelCramer", modelCramer);
        return "unit2/reglacramer/formcramer";
    }

    @PostMapping("/unit3/solvecramer")
    public String solveCramer(ReglaCramer modelCramer, Errors errores, Model model) {
        //log.info("OBJECTOS:" + modelCramer.getMatrizA());
        ArrayList<Double> A = modelCramer.getMatrizA();
        var solveCramer = unidadIIIsrv.AlgoritmoReglaCramer(modelCramer);
        //log.info("Solución: " + solveCramer.getVectorX());

        //log.info("Determinantes: " + solveCramer.getDeterminantes());
        model.addAttribute("solveCramer", solveCramer);
        return "unit2/reglacramer/solvecramer";
    }
    //////////////////////////////////////GAUSS/////////////////////////
     @GetMapping("/unit3/formgauss")
    public String formGauss(Model model) {
        Gauss modelGauss = new Gauss();
        model.addAttribute("modelGauss", modelGauss);
        return "unit3/gauss/formgauss";
    }
    
    @PostMapping("/unit3/solvegauss")
    public String solveGauss(Gauss modelGauss, Errors errores, Model model) {
        //log.info("OBJECTOS:" + modelGauss.getMatrizA());
        ArrayList<Double> A = modelGauss.getMatrizA();
        var solveGauss = unidadIIIsrv.AlgoritmoGauss(modelGauss);
        //log.info("Solución: " + solveGauss.getVectorX());
        //log.info("Pasos: " + solveGauss.getPasos());
        model.addAttribute("solveGauss", solveGauss);
        return "unit3/gauss/solvegauss";
    }
    
    // === GAUSS-JORDAN ===
    @GetMapping("/unit3/formgaussjordan")
    public String formGaussJordan(Model model) {
        GaussJordan modelGaussJordan = new GaussJordan();
        model.addAttribute("modelGaussJordan", modelGaussJordan);
        return "unit3/gaussjordan/formgaussjordan";
    }
    
    @PostMapping("/unit3/solvegaussjordan")
    public String solveGaussJordan(GaussJordan modelGaussJordan, Errors errores, Model model) {
        //log.info("OBJECTOS:" + modelGaussJordan.getMatrizA());
        ArrayList<Double> A = modelGaussJordan.getMatrizA();
        var solveGaussJordan = unidadIIIsrv.AlgoritmoGaussJordan(modelGaussJordan);
        //log.info("Solución: " + solveGaussJordan.getVectorX());
        //log.info("Pasos: " + solveGaussJordan.getPasos());
        model.addAttribute("solveGaussJordan", solveGaussJordan);
        return "unit3/gaussjordan/solvegaussjordan";
    }
}
