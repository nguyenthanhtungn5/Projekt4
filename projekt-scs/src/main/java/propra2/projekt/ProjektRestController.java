package propra2.projekt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("projekte-rest")
public class ProjektRestController {
    @Autowired
    ProjektRepository projektRepository;

    @GetMapping("/all")
    public List<Projekt> all() {
        List<Projekt> projekts = projektRepository.findAll();
        return projekts;
    }
}
