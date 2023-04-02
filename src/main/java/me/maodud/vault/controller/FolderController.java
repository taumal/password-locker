package me.maodud.vault.controller;

import lombok.RequiredArgsConstructor;
import me.maodud.vault.model.Folder;
import me.maodud.vault.service.FolderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/new")
    public String newFolder(Model model) {
        model.addAttribute("folder", new Folder());
        return "folder/form";
    }

    @GetMapping("/{id}/edit")
    public String editFolder(@PathVariable("id") Long id, Model model) {
        Folder category = folderService.getFolderById(id);
        model.addAttribute("category", category);
        return "folder/form";
    }

    @PostMapping("")
    public String saveFolder(@ModelAttribute("folder") Folder folder) {
        folderService.saveFolder(folder);
        return "redirect:/folders";
    }

    @DeleteMapping("/{id}")
    public String deleteFolder(@PathVariable("id") Long id) {
        folderService.deleteFolder(id);
        return "redirect:/folders";
    }
}
