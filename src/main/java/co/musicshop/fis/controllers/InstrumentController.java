package co.musicshop.fis.controllers;

import co.musicshop.fis.dtos.CreateInstrumentDto;
import co.musicshop.fis.models.Instrument;
import co.musicshop.fis.services.InstrumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/instruments")
public class InstrumentController {
    private final InstrumentService instrumentService;

    @Autowired
    public InstrumentController(InstrumentService instrumentService) {
        this.instrumentService = instrumentService;
    }

    @GetMapping()
    public String allInstruments(Model model) {

        List<Instrument> instruments = instrumentService.findAllInstruments();

        model.addAttribute("instruments", instruments);

        return "instruments"; // Refers to instruments.html (view)
    }

    @GetMapping("/add")
    public String addInstrumentForm(Model model) {

        model.addAttribute("createInstrumentDto", new CreateInstrumentDto());
        return "addInstrument"; // Refers to addInstrument.html (view)
    }

    @PostMapping()
    public String addInstrument(@ModelAttribute("createInstrumentDto") CreateInstrumentDto createInstrumentDto, Model model) {
        // Convert DTO to entity and save the instrument
        Instrument instrument = new Instrument(
                createInstrumentDto.getName(),
                createInstrumentDto.getType(),
                createInstrumentDto.getBrand(),
                createInstrumentDto.getPrice(),
                createInstrumentDto.getPhoto()
        );

        // Save the instrument
        instrumentService.saveInstrument(instrument);

        // Pass the instrument entity back to the view
        model.addAttribute("instrument", instrument);

        return "redirect:/instruments"; // Redirect to /instruments
    }

    @GetMapping("/{id}")
    public String getInstrumentDetails(@PathVariable Long id, Model model) {
        Instrument instrument = instrumentService.findInstrumentById(id);
        model.addAttribute("instrument", instrument);
        return "instrumentDetails";
    }


}
