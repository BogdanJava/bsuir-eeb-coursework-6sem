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
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@Service
public class TransactionPDFServiceImpl implements TransactionPDFService {

    private static final String SUBJECT = "Transaction details";
    private static final String TITLE = "Transaction #";

    @Value("${doc.author}")
    private String AUTHOR;

    @Override
    public InputStream createDocumentFromTransaction(CardTransaction transaction) throws IOException {
        File transactionsDir = new File("transactions");
        if(!transactionsDir.exists()) transactionsDir.mkdir();
        File userDir = new File("transactions/" + transaction.getCard().getUser().getId());
        if (!userDir.exists()) userDir.mkdir();
        File file = new File("transactions/" + transaction.getCard().getUser().getId() + "/" + transaction.getId() + ".pdf");
        if (!file.exists()) {
            file.createNewFile();
        } else {
            return new FileInputStream(file);
        }

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
        contentStream.showText("Type: " + transaction.getTransactionType());
        contentStream.newLine();
        contentStream.showText("Card number: " + transaction.getCard().getCardNumber());
        contentStream.newLine();
        contentStream.showText("User name: " + transaction.getCard().getUser().getFullName());
        contentStream.newLine();
        List<String> words = Arrays.asList(transaction.getDescription().split(" "));
        int currentWord = 7;
        if (words.size() < 7) {
            contentStream.showText("Description: " + String.join(" ", words.subList(0, words.size())));
        } else {
            contentStream.showText("Description: " + String.join(" ", words.subList(0, 7)));
            while (currentWord + 7 <= words.size()) {
                contentStream.newLine();
                contentStream.showText(String.join(" ", words.subList(currentWord, currentWord + 7)));
                currentWord = currentWord + 7;
            }
            contentStream.newLine();
            contentStream.showText(String.join(" ", words.subList(currentWord, words.size())));
        }


        contentStream.endText();
        contentStream.close();


        document.save(file);
        document.close();
        return new FileInputStream(file);
    }
}
