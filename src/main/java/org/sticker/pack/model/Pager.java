package org.sticker.pack.model;

/**
 * Created by Mikhail on 16.05.2017.
 */
public class Pager {

    private int buttonsToShow = 5;

    private int startPage;

    private int endPage;

    public Pager(int totalPages, int currentPage) {

        int halfPagesToShow = buttonsToShow / 2;

        if (totalPages <= buttonsToShow) {
            this.startPage = 1;
            this.endPage = totalPages;
        } else if (currentPage - halfPagesToShow <= 0) {
            this.startPage = 1;
            this.endPage = buttonsToShow;
        } else if (currentPage + halfPagesToShow == totalPages) {
            this.startPage = currentPage - halfPagesToShow;
            this.endPage  = totalPages;
        } else if (currentPage + halfPagesToShow > totalPages) {
            this.startPage = totalPages - buttonsToShow + 1;
            this.endPage = totalPages;
        } else {
            this.startPage = currentPage - halfPagesToShow;
            this.endPage = currentPage + halfPagesToShow;
        }

    }

    public int getButtonsToShow() {
        return buttonsToShow;
    }


    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    @Override
    public String toString() {
        return "Pager [startPage=" + startPage + ", endPage=" + endPage + "]";
    }

}
