package org.emailverification;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

import org.emailverification.entityF.Document;
import org.emailverification.repository.DocumentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)

class EmailVerificationFileApplicationTests {

	@Autowired
	DocumentRepository docRepo;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	@Rollback(false)
	void testInsertDocument() throws IOException {
	File file = new File("C:\\Users\\HP Notebook\\Documents\\FAB CV 4.docx");
	Document document = new Document();
	document.setName(file.getName());
	byte[] bytes = Files.readAllBytes(file.toPath());
	document.setContent(bytes);
	long fileSize = bytes.length;
	document.setSize(fileSize);
	document.setUploadTime(new Date());	
	
	Document saveDoc = docRepo.save(document);
	
	Document existDoc = entityManager.find(Document.class, saveDoc.getId());
	
	assertThat(existDoc.getSize()).isEqualTo(fileSize);

}
	
}
