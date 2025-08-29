package mx.edu.itses.KLDM.MetodosNumericos.web;

import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import mx.edu.itses.KLDM.MetodosNumericos.domain.DDNewton;
import mx.edu.itses.KLDM.MetodosNumericos.domain.Lagrange;
import mx.edu.itses.KLDM.MetodosNumericos.services.UnidadIVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class Unit4Controller {

    @Autowired
    private UnidadIVService unidadIVsrv;

    // Página principal  IV
    @GetMapping("/unit4")
    public String index(Model model) {
        return "unit4/index";
    }

    // === NEWTON DIFERENCIAS DIVIDIDAS ===
    @GetMapping("/unit4/formddnewton")
    public String formDDNewton(Model model) {
        DDNewton modelDDNewton = new DDNewton();
        model.addAttribute("modelDDNewton", modelDDNewton);
        return "unit4/ddnewton/formddnewton";
    }

    @PostMapping("/unit4/solveddnewton")
    public String solveDDNewton(DDNewton modelDDNewton, Errors errores, Model model) {
        ArrayList<Double> puntosX = modelDDNewton.getPuntosX();
        var solveDDNewton = unidadIVsrv.AlgoritmoDDNewton(modelDDNewton);
        model.addAttribute("modelDDNewton", solveDDNewton);
        return "unit4/ddnewton/solveddnewton";
    }
// === LAGRANGE ===

    @GetMapping("/unit4/formlagrange")
    public String formLagrange(Model model) {
        Lagrange modelLagrange = new Lagrange();
        model.addAttribute("modelLagrange", modelLagrange);
        return "unit4/lagrange/formlagrange";
    }

    @PostMapping("/unit4/solvelagrange")
    public String solveLagrange(Lagrange modelLagrange, Errors errores, Model model) {
        var solveLagrange = unidadIVsrv.AlgoritmoLagrange(modelLagrange);
        model.addAttribute("modelLagrange", solveLagrange);
        return "unit4/lagrange/solvelagrange";
    }
}
