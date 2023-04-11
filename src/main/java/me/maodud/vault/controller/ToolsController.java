package me.maodud.vault.controller;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.RequiredArgsConstructor;
import me.maodud.vault.enums.Type;
import me.maodud.vault.model.Folder;
import me.maodud.vault.model.LoginCredential;
import me.maodud.vault.service.FolderService;
import me.maodud.vault.service.LoginCredentialService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/tools")
@RequiredArgsConstructor
public class ToolsController {
    private final LoginCredentialService loginCredentialsService;
    private final FolderService folderService;
    @GetMapping("/export")
    public void exportCredentials(HttpServletResponse response) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        // Retrieve all login credentials from the service
        List<LoginCredential> credentials = loginCredentialsService.getAllCredentials();

        // Set response headers
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"credentials.csv\"");

        // Create CSV writer
        StatefulBeanToCsv<LoginCredential> writer = new StatefulBeanToCsvBuilder<LoginCredential>(response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withOrderedResults(true)
                .build();

        // Write credentials to CSV
        writer.write(credentials);
    }

    @PostMapping("/import")
    public String importCredentials(@RequestParam("file") MultipartFile file) throws IOException, CsvException {
        // Check if file is a CSV
        if (!file.getContentType().equals("text/csv")) {
            return "error";
        }

        // Parse CSV file
        CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()));
        List<String[]> rows = reader.readAll();

        // Map CSV rows to LoginCredentials objects
        List<LoginCredential> credentials = new ArrayList<>();
        for (String[] row : rows) {
            LoginCredential credential = new LoginCredential();
            credential.setName(row[0]);
            credential.setType(Type.valueOf(row[1]));
            credential.setUsername(row[2]);
            credential.setPassword(row[3]);
            credential.setUrl(row[4]);
            credential.setNotes(row[5]);
            // Set folder based on row[6] or create a new folder if it doesn't exist
            Folder folder = folderService.getFolderById(Long.valueOf(row[6]));
            credential.setFolder(folder);
            credentials.add(credential);
        }

        // Save credentials to database
        loginCredentialsService.saveAllCredentials(credentials);

        return "success";
    }

}
