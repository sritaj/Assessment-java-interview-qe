package com.mendeley.interview;

public class Document {

    private String title;
    private final int publicationYear;

    Document(String title, int publicationYear) {
        this.title = title;
        this.publicationYear = publicationYear;
    }

    public String getTitle() {
        return this.title;
    }

    public int getPublicationYear() {
        return this.publicationYear;
    }
}
