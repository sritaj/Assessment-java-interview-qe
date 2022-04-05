package com.mendeley.interview;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Catalogue {

    ArrayList<Document> documentCatalogue;

    Catalogue() {
        this.documentCatalogue = new ArrayList<>();
    }

    public int size() {
        return this.documentCatalogue.size();
    }

    public void addDocument(Document document) {
        documentCatalogue.add(document);
    }

    public List<Document> getSortedDocuments(Comparator<Document> comparable) {
        documentCatalogue.sort(comparable);
        return documentCatalogue;
    }

    public List<Document> getDocumentsByYear(int year) {

        return documentCatalogue.stream().filter((document -> document.getPublicationYear() == year)).collect(Collectors.toList());
    }
}
