package org.randomimage;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ImageViewController {
    private final ImageService service;

    @GetMapping("/")
    public String index(Model model) {
        ImageEntry entry = service.getLatestImage();
        if (entry != null) {
            model.addAttribute("imgId", entry.getId());
            model.addAttribute("savedAt", entry.getSavedAt());
        } else {
            model.addAttribute("imgId", null);
            model.addAttribute("savedAt", null);
        }
        return "index";
    }
}
