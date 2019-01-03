package propra2.projekt;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
@Data
@Controller
public class ProjektController {
	@Autowired
    ProjektRepository projektRepository;

	@GetMapping("/")
	public String mainPage(Model model) {
		List<Projekt> projekts = projektRepository.findAll();
		model.addAttribute("projekts", projekts);

		return "index";
	}

	@GetMapping("/addProjekt")
    public String addProjektPage() {
	    return "addProjekt";
    }

    @RequestMapping("/add")
    public String addToDatabase(@RequestParam("name") String name,
                                @RequestParam("description") String description,
                                @RequestParam("budget") String budget,
                                @RequestParam("startdatum") String startdatum,
                                @RequestParam("laufzeit") String laufzeit,
                                Model model) {
        Projekt newProjekt = new Projekt();
        newProjekt.setName(name);
        newProjekt.setDescription(description);
        newProjekt.setBudget(budget);
        newProjekt.setStartdatum(startdatum);
        newProjekt.setLaufzeit(laufzeit);
        projektRepository.save(newProjekt);

        model.addAttribute("projekt", newProjekt);

	    return "confirmationAdd";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id) {
        Optional<Projekt> projekt = projektRepository.findById(id);

        if (!projekt.isPresent()) {
            throw new ProjektNichtVorhanden();
        }
        model.addAttribute("projekt", projekt);
        return "edit";
	}

	@RequestMapping("/saveChanges/{id}")
    public String saveChanges(@RequestParam("name") String name,
                              @RequestParam("description") String description,
                              @RequestParam("budget") String budget,
                              @RequestParam("startdatum") String startdatum,
                              @RequestParam("laufzeit") String laufzeit,
                              @PathVariable Long id,
                              Model model) {
        Optional<Projekt> projekt = projektRepository.findById(id);
        projekt.get().setName(name);
        projekt.get().setDescription(description);
        projekt.get().setBudget(budget);
        projekt.get().setStartdatum(startdatum);
        projekt.get().setLaufzeit(laufzeit);

        projektRepository.save(projekt.get());
        model.addAttribute("projekt", projekt);

        return "confirmationEdit";
	}
}
