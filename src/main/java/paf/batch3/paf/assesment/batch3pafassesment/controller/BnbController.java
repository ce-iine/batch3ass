package paf.batch3.paf.assesment.batch3pafassesment.controller;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import paf.batch3.paf.assesment.batch3pafassesment.model.Accomodation;
import paf.batch3.paf.assesment.batch3pafassesment.model.Listing;
import paf.batch3.paf.assesment.batch3pafassesment.model.Reservation;
import paf.batch3.paf.assesment.batch3pafassesment.service.BnbService;
import paf.batch3.paf.assesment.batch3pafassesment.service.TransactionException;

@Controller
@RequestMapping
public class BnbController {

    @Autowired
    BnbService bnbService;

    @GetMapping("/")
    public String view1(Model model) {

        List<String> countryList = bnbService.getListOfCountry();
        Accomodation accomodation = new Accomodation();
        model.addAttribute("accomodation", accomodation);
        model.addAttribute("countryList", countryList);

        return "view1";
    }

    @GetMapping("/search")
    public String view2(@Valid @ModelAttribute("accomodation") Accomodation form, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("accomodation", new Accomodation());
            model.addAttribute("countryList", bnbService.getListOfCountry());
            return "view1";
        }

        System.out.println(form);
        // List<Document> queryResults = bnbService.getQueryDetails(form);
        List<Listing> queryResults = bnbService.getQueryDetails(form);
        model.addAttribute("country", form.getCountry());
        model.addAttribute("queryResults", queryResults);
        return "view2";
    }

    @GetMapping("/details/{id}")
    public String view3(@PathVariable String id, Model model) {

        Listing doc = bnbService.getAccomDetails(id);
        model.addAttribute("doc", doc);
        model.addAttribute("reservation", new Reservation());
        return "view3";
    }

    @PostMapping("/booking/{id}")
    public String view4(@PathVariable String id, @ModelAttribute Reservation res, Model model) throws TransactionException {

        if (!bnbService.getVacancy(res, id)) {
            System.out.println("Vacancy not available for specified duration!");
        }

        res.setResv_id(bnbService.generateResId(res));
        res.setAcc_id(id);
        System.out.println("------------------------------------------");
        System.out.println("RES LOOKS LIKE THIS NOW" + res);
        bnbService.performUpdates(res);
        model.addAttribute("id", res.getResv_id());

        return "view4";
    }

}
