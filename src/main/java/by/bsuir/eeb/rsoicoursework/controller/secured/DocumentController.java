package by.bsuir.eeb.rsoicoursework.controller.secured;

import by.bsuir.eeb.rsoicoursework.security.ResourceAccessResolver;
import by.bsuir.eeb.rsoicoursework.service.CardManagementService;
import by.bsuir.eeb.rsoicoursework.service.TransactionPDFService;
import org.apache.pdfbox.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

@Controller
@RequestMapping("/api/document")
public class DocumentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    private TransactionPDFService transactionPDFService;

    @Autowired
    private CardManagementService cardManagementService;

    @Autowired
    private ResourceAccessResolver accessResolver;

    @RequestMapping(method = RequestMethod.GET, value = "/transactions/{transactionId}/doc")
    public void getTransactionPdf(@PathVariable long transactionId, HttpServletResponse response) {
        try {
            if (!accessResolver.checkUserSpecificResourceAccess(cardManagementService
                    .getUserIdByCardId(cardManagementService.getTransaction(transactionId).getCard().getId()))) {
                throw new Exception("Access denied");
            }
            InputStream inputStream = transactionPDFService.createDocumentFromTransaction(cardManagementService.getTransaction(transactionId));
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            LOGGER.debug(e.getMessage(), e);
        }
    }
}