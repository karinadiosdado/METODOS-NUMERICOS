
package mx.edu.itses.KLDM.MetodosNumericos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class MainController {
    @GetMapping("/") // ruta pricipal
    public String inicio(Model model){
        int i = 1;
log.info("Mensaje de salida : {}",i);
model.addAttribute("Valori", i);
        return "index";// lo que va a abuscar el web
    }
}
