package me.maodud.vault.controller;

import lombok.RequiredArgsConstructor;
import me.maodud.vault.model.Folder;
import me.maodud.vault.service.FolderService;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/folders")
@RequiredArgsConstructor
public class FolderController {
    private final FolderService folderService;

    @GetMapping("")
    public String getFolders(Model model) {
        List<Folder> folderList = folderService.getAllFolderList();
        model.addAttribute("folderList", folderList);
        return "folder/list";
    }

    @GetMapping("/find-by-id/{id}")
    @ResponseBody
    public ModelMap getFolderList(@PathVariable("id") Long id) {
        Folder folder = folderService.getFolderById(id);
        ModelMap map = new ModelMap();
        map.put("folder", folder);
        return map;
    }

    @GetMapping("/new")
    public String newFolder(Model model) {
        model.addAttribute("folder", new Folder());
        return "folder/new-form";
    }

    @GetMapping("/edit/{id}")
    public String editFolder(@PathVariable("id") Long id, Model model) {
        Folder folder = folderService.getFolderById(id);
        model.addAttribute("folder", folder);
        return "folder/new-form";
    }

    @PostMapping("")
    public String saveFolder(Folder folder, BindingResult result) {
        try {
            if (!result.hasErrors()) {
                folderService.saveFolder(folder);
                System.out.println("created");
            } else {
                System.out.println("not created");
            }
        } catch (Exception e) {
            System.out.println("not created. cause: " + e.getMessage());
        }
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String deleteFolder(@PathVariable("id") Long id) {
        folderService.deleteFolder(id);
        return "redirect:/folders";
    }
}
