package me.maodud.vault.service;

import lombok.RequiredArgsConstructor;
import me.maodud.vault.exception.ResourceNotFoundException;
import me.maodud.vault.model.Folder;
import me.maodud.vault.repository.FolderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository repository;

    public Folder saveFolder(Folder folder) {
        return repository.save(folder);
    }

    public Folder getFolderById(Long folderId) {
        return repository.findById(folderId)
                .orElseThrow(() -> new ResourceNotFoundException("Folder not found with id " + folderId));
    }

    public List<Folder> getAllFolderList() {
        return repository.findAll();
    }

    public Folder updateFolder(Long folderId, Folder folderRequest) {
        Folder category = repository.findById(folderId)
                .orElseThrow(() -> new ResourceNotFoundException("Folder not found with id " + folderId));

        category.setName(folderRequest.getName());

        return repository.save(category);
    }

    public ResponseEntity<?> deleteFolder(Long folderId) {
        Folder category = repository.findById(folderId)
                .orElseThrow(() -> new ResourceNotFoundException("Folder not found with id " + folderId));

        repository.delete(category);

        return ResponseEntity.ok().build();
    }
}
