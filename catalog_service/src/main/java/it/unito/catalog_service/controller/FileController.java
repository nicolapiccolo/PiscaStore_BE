package it.unito.catalog_service.controller;

import it.unito.catalog_service.exception.ImageNotFoundException;
import it.unito.catalog_service.messaging.ResponseMessage;
import it.unito.catalog_service.service.StorageService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class FileController {

    //root path for image files (from dockercompose in volume)
    private String FILE_PATH_ROOT = "catalog/";

    @Autowired
    StorageService storageService;


    @GetMapping("image/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable("filename") String filename) {
        byte[] image = new byte[0];
        try {
            image = FileUtils.readFileToByteArray(new File(FILE_PATH_ROOT+filename));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new ImageNotFoundException();
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }

    @GetMapping("image/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {

        Resource file = storageService.load(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("image/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            String filename = storageService.save(file);
            message = filename;
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!\n" + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("image/deleteAll")
    public ResponseEntity<String> deleteAll(){
        storageService.init();
        storageService.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).body("All deleted");

    }

    @GetMapping("image/delete/{filename}")
    public ResponseEntity<ResponseMessage> deleteFile(@PathVariable String filename){
        boolean result = storageService.delete(filename);
        if(result) return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("File deleted"));
        else return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("Could not delete the file"));
    }


    @GetMapping("image/files")
    public ResponseEntity<List<String>> getListFiles() {
        List<String> fileInfos = storageService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FileController.class, "getFile", path.getFileName().toString()).build().toString();
            String file = filename + url;
            return file;
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }


}