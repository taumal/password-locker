package me.maodud.vault.component;

import lombok.RequiredArgsConstructor;
import me.maodud.vault.model.Folder;
import me.maodud.vault.repository.FolderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FolderDataLoader implements CommandLineRunner {
    private final FolderRepository repository;
    @Override
    public void run(String... args) throws Exception {
        Folder folder = new Folder();
        folder.setName("No Folder");
        repository.save(folder);
    }
}
