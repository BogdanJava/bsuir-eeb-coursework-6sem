package by.bsuir.eeb.rsoicoursework.service.impl;

import by.bsuir.eeb.rsoicoursework.model.CardTransaction;
import by.bsuir.eeb.rsoicoursework.service.TransactionPDFService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

@Service
public class TransactionPDFServiceImpl implements TransactionPDFService {

    private static final String SUBJECT = "Transaction details";
    private static final String TITLE = "Transaction #";

    @Value("${doc.author}")
    private String AUTHOR;

    @Override
    public InputStream createDocumentFromTransaction(CardTransaction transaction) throws IOException {
        PDDocument document = new PDDocument();

        PDDocumentInformation info = new PDDocumentInformation();
        info.setAuthor(AUTHOR);
        info.setSubject(SUBJECT);
        info.setTitle(TITLE + transaction.getId());
        info.setCreationDate(Calendar.getInstance());
        document.setDocumentInformation(info);
        document.addPage(new PDPage());
        PDPage page = document.getPage(0);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.setLeading(20);
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 10);

        contentStream.beginText();
        contentStream.newLineAtOffset(260, 742);

        contentStream.showText(info.getTitle());
        contentStream.newLine();
        contentStream.showText("Transaction id: " + transaction.getId());
        contentStream.newLine();
        contentStream.showText("Name: " + transaction.getName());
        contentStream.newLine();
        contentStream.showText("Diff: " + transaction.getDiff());
        contentStream.newLine();
        contentStream.showText("Date: " + transaction.getDate());
        contentStream.newLine();
        contentStream.showText("Card number: " + transaction.getCard().getCardNumber());
        contentStream.newLine();
        contentStream.showText("User name: " + transaction.getCard().getUser().getFullName());
        contentStream.newLine();
        contentStream.endText();
        contentStream.close();

        File file = new File(transaction.getId() + ".pdf");
        if (!file.exists()) {
            file.createNewFile();
        }
        document.save(file);
        document.close();
        return new FileInputStream(file);
    }
}
