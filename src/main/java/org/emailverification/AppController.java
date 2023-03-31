package org.emailverification;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.emailverification.entityF.Document;
import org.emailverification.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
public class AppController {
	
	@Autowired
	DocumentRepository docRepo;
	
	@GetMapping("/")
	public String UpLoadFile(Model model){
		List<Document>listDoc = docRepo.findAll();
		model.addAttribute("listDoc", listDoc);
		return "uploadfile";
		
	}
	
	

	@PostMapping("/upLoad")
	public String Home(@RequestParam("document") MultipartFile multipartFile, RedirectAttributes ra ) throws IOException {
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		Document document = new Document();
		document.setName(fileName);
		document.setContent(multipartFile.getBytes());
		document.setSize(multipartFile.getSize());
		document.setUploadTime(new Date());
		
		docRepo.save(document);
		
		ra.addFlashAttribute("message", "The file has been loaded successfully");
		
		
		return "redirect:/";
	}

	@GetMapping("/download")
	public void downloadFile(@Param("id") Long id, HttpServletResponse response) throws Exception {
		Optional<Document> result = docRepo.findById(id);
		if(!result.isPresent()) {
			throw new Exception("could not find document with ID:" + id);
		}
		
		Document document = result.get();
		
		response.setContentType("application/octet-stream");
		
		String headerKey = "Content-Disposition";
		
		String headerValue = "attachment; fileName=" + document.getName();
		
		response.setHeader(headerKey, headerValue);
		ServletOutputStream outputStream = response.getOutputStream();
		
		outputStream.write(document.getContent());
		outputStream.close();
	}

	/*
	private static String encodeImage(String imgPath, String savePath) throws Exception{
		FileInputStream imageStream = new FileInputStream(imgPath);
		byte[] data = imageStream.readAllBytes();
		String imageString = Base64.getEncoder().encodeToString(data);
		
		FileWriter fileWriter = new FileWriter(savePath);
		fileWriter.write(imageString);

		fileWriter.close();
		imageStream.close();
		return imageString;
	}
	*/
	

	
	private static String encodeImage(String document, String savePath) throws Exception{
		FileInputStream imageStream = new FileInputStream(document);
		byte[] data = imageStream.readAllBytes();
		String imageString = Base64.getEncoder().encodeToString(data);
		
		FileWriter fileWriter = new FileWriter(savePath);
		fileWriter.write(imageString);

		fileWriter.close();
		imageStream.close();
		System.out.print(imageString);
		return imageString;
	}
}
