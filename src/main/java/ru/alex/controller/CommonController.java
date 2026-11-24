package ru.alex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.alex.entity.Record;
import ru.alex.entity.RecordStatus;
import ru.alex.entity.dto.RecordsContainerDTO;
import ru.alex.service.RecordService;

import java.util.List;

@Controller
public class CommonController {

    private final RecordService recordService;

    @Autowired
    public CommonController(RecordService recordService) {
        this.recordService = recordService;
    }

    @RequestMapping("/")
        public String redirectPageHome(){
        return "redirect:/home";
        }

    @RequestMapping("/home")
    public ModelAndView getMainPage(@RequestParam(name = "filter",required = false) String filterMode){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("main-page");
        RecordsContainerDTO container = recordService.findAllRecords(filterMode);

        modelAndView.getModelMap().addAttribute("records", container.getRecords());
        modelAndView.getModelMap().addAttribute("numberOfActiveRecords",container.getNumberOfActiveRecords());
        modelAndView.getModelMap().addAttribute("numberOfDoneRecords",container.getNumberOfDoneRecords());

        return modelAndView;
    }

    @RequestMapping(value = "/add-record", method = RequestMethod.POST)
    public String addRecord(@RequestParam("title") String title) {
        recordService.saveRecord(title);
        return "redirect:/home";

    }
    @RequestMapping(value = "/make-record-done", method = RequestMethod.POST)
    public String makeRecordDone(@RequestParam("id") int id,
                                 @RequestParam(name = "filter",required = false) String filterMode) {
        recordService.updateRecordStatus(id,RecordStatus.DONE);
        return "redirect:/home" + (filterMode !=null && !filterMode.isBlank() ? "?filter=" + filterMode: "");

    }
    @RequestMapping(value = "/delete-record", method = RequestMethod.POST)
    public String deleteRecord(@RequestParam("id") int id,
                               @RequestParam(name = "filter",required = false) String filterMode) {
        recordService.deleteRecord(id);
        return "redirect:/home" + (filterMode !=null && !filterMode.isBlank() ? "?filter=" + filterMode: "");


    }
}
