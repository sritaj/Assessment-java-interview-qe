package com.mendeley.interview;

import com.github.javafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CatalogueTest extends BaseTest {

    Catalogue cat = new Catalogue();
    Faker faker = new Faker();
    Random random = new Random();
    Document doc;

    @Test(testName = "Validate empty catalogue", priority = 0)
    public void validateCatalogueCreation() {
        int defaultCatalogueSize = cat.size();
        Assert.assertEquals(defaultCatalogueSize, 0);
    }

    @Test(testName = "Validate catalogue after addition of documents", priority = 1)
    public void validateAdditionOfDocuments() {
        int min = 1800;
        int max = 2200;
        int random_year = (int) Math.floor(Math.random() * (max - min + 1) + min);

        cat.addDocument(new Document(faker.book().title(), random_year));
        int catalogueSizeAfterDocAddition = cat.size();
        Assert.assertEquals(catalogueSizeAfterDocAddition, 1);
    }

    @Test(testName = "Validate the retrieved documents are the ones which were added", priority = 2)
    public void validateRetrivalOfDocuments() {
        int min = 1800;
        int max = 2200;
        int random_year = (int) Math.floor(Math.random() * (max - min + 1) + min);
        String bookTitle = faker.book().title();
        String bookTitle2 = faker.book().title();

        cat.addDocument(new Document(bookTitle, random_year));
        cat.addDocument(new Document(bookTitle2, random_year));

        List<String> expectedDocs = new ArrayList<>();
        expectedDocs.add(bookTitle);
        expectedDocs.add(bookTitle2);

        List<Document> docs = cat.getDocumentsByYear(random_year);
        List<String> actualDocs = new ArrayList<>();

        for (Document d : docs) {
            actualDocs.add(d.getTitle());
        }

        Collections.sort(actualDocs);
        Collections.sort(expectedDocs);
        boolean bool = actualDocs.equals(expectedDocs);

        Assert.assertTrue(bool);
    }

    @Test(testName = "Validate the retrieved document title matches as per the title added", priority = 3)
    public void validateDocumentTitle() {
        int min = 1800;
        int max = 2200;
        int random_year = (int) Math.floor(Math.random() * (max - min + 1) + min);
        String bookTitle = faker.book().title();

        cat.addDocument(new Document(bookTitle, random_year));

        List<Document> docs = cat.getDocumentsByYear(random_year);
        List<String> actualDocsTitle = new ArrayList<>();

        for (Document d : docs) {
            actualDocsTitle.add(d.getTitle());
        }

        String actualBookTitle = actualDocsTitle.get(0);

        boolean bool = actualBookTitle.equals(bookTitle);
        Assert.assertTrue(bool);
    }

    @Test(testName = "Validate the retrieved document publication year matches as per the publication year added", priority = 4)
    public void validateDocumentPublicationYear() {
        int min = 1800;
        int max = 2200;
        int bookPublicationYear = (int) Math.floor(Math.random() * (max - min + 1) + min);
        String bookTitle = faker.book().title();

        cat.addDocument(new Document(bookTitle, bookPublicationYear));

        List<Document> docs = cat.getDocumentsByYear(bookPublicationYear);
        List<Integer> actualDocsPublicationYear = new ArrayList<>();

        for (Document d : docs) {
            actualDocsPublicationYear.add(d.getPublicationYear());
        }

        Integer actualBookPublicationYear = actualDocsPublicationYear.get(0);

        boolean bool = actualBookPublicationYear.equals(bookPublicationYear);
        Assert.assertTrue(bool);
    }

    @Test(testName = "Validate the retrieved documents are in Sorted Order", priority = 5)
    public void validateRetrivalOfDocumentsIsSorted() {
        int min = 1800;
        int max = 2200;
        int random_year = (int) Math.floor(Math.random() * (max - min + 1) + min);
        String bookTitle = faker.book().title();
        String bookTitle2 = faker.book().title();

        Catalogue cat2 = new Catalogue();
        cat2.addDocument(new Document(bookTitle, random_year));
        cat2.addDocument(new Document(bookTitle2, random_year));

        List<String> expectedDocs = new ArrayList<>();
        expectedDocs.add(bookTitle);
        expectedDocs.add(bookTitle2);

        List<Document> docs = cat2.getSortedDocuments((o1, o2) -> 0);
        List<String> actualDocs = new ArrayList<>();

        for (Document d : docs) {
            actualDocs.add(d.getTitle());
        }

        boolean bool = actualDocs.equals(expectedDocs);

        Assert.assertTrue(bool);
    }

    @Test(testName = "Validate the Document Title is Mandatory", expectedExceptions = {NullPointerException.class}, priority = 6)
    public void validateDocumentTitleIsMandatory() {
        cat.addDocument(new Document(null, 2023));
    }

}
