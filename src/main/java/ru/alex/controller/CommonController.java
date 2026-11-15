package ru.alex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.alex.entity.Record;
import ru.alex.entity.RecordStatus;
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
    public String getMainPage(Model model){
        List<Record> records = recordService.findAllRecords();
        int numberOfActiveRecords = (int) records.stream().filter(record -> record.getStatus() == RecordStatus.ACTIVE).count();
        int numberOfDoneRecords = (int) records.stream().filter(record -> record.getStatus() == RecordStatus.DONE).count();

        model.addAttribute("records",records);

        model.addAttribute("numberOfActiveRecords",numberOfActiveRecords);
        model.addAttribute("numberOfDoneRecords",numberOfDoneRecords);

        return "main-page";
    }

    @RequestMapping(value = "/add-record", method = RequestMethod.POST)
    public String addRecord(@RequestParam("title") String title) {
        recordService.saveRecord(title);
        return "redirect:/home";

    }
    @RequestMapping(value = "/make-record-done", method = RequestMethod.POST)
    public String makeRecordDone(@RequestParam("id") int id) {
        recordService.updateRecordStatus(id,RecordStatus.DONE);
        return "redirect:/home";

    }
    @RequestMapping(value = "/delete-record", method = RequestMethod.POST)
    public String deleteRecord(@RequestParam("id") int id) {
        recordService.deleteRecord(id);
        return "redirect:/home";

    }
}
