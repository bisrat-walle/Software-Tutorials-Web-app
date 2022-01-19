package com.groupproject.softwaretu;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TutorialsController {
    
    @GetMapping
    public String tutorials(){
        return "tutorials";
    }
}
