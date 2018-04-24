package by.bsuir.eeb.rsoicoursework.service;

import by.bsuir.eeb.rsoicoursework.model.CardTransaction;

import java.io.IOException;
import java.io.InputStream;

public interface TransactionPDFService {
    InputStream createDocumentFromTransaction(CardTransaction transaction) throws IOException;
}
