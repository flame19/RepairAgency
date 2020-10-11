package ua.repair_agency.services.pagination.impl;

import ua.repair_agency.constants.CommonConstants;
import ua.repair_agency.models.pagination.Page;
import ua.repair_agency.models.pagination.PaginationModel;
import ua.repair_agency.services.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;

public class PagePaginationHandler implements Pagination {

    public PagePaginationHandler() {

    }

    public PaginationModel createPaginationModel(String currentUri, int currentPageNum,
                                                 int totalEntities, int entitiesPageLimit) {
        if (totalEntities <= entitiesPageLimit) {
            return null;
        }

        String previousUrl = createPreviousUri(currentUri, currentPageNum);
        String nextUrl = createNextUri(currentUri, currentPageNum, totalEntities, entitiesPageLimit);
        int totalPages = countTotalPages(totalEntities, entitiesPageLimit);
        List<Page> pages = new ArrayList<>();
        if (currentPageNum > 4 && currentPageNum < totalPages - 3) {
            formMiddlePaginationModel(currentUri, currentPageNum, totalPages, pages);
        } else if (currentPageNum > 4) {
            formLeftPaginationModel(currentUri, currentPageNum, totalPages, pages);
        } else if (currentPageNum < totalPages - 3) {
            formRightPaginationModel(currentUri, currentPageNum, totalPages, pages);
        } else {
            createSidePages(pages, currentUri, currentPageNum, 1, totalPages);
        }

        return new PaginationModel(previousUrl, nextUrl, pages);
    }

    private void formMiddlePaginationModel(String currentUri, int currentPageNum, int totalPages, List<Page> pages) {
        formLeftPaginationModel(currentUri, currentPageNum, currentPageNum + 2, pages);
        createSinglePage(pages, CommonConstants.ELLIPSIS, -1);
        createSinglePage(pages, currentUri, totalPages);
    }

    private void formLeftPaginationModel(String currentUri, int currentPageNum, int totalPages, List<Page> pages) {
        createSinglePage(pages, currentUri, 1);
        createSinglePage(pages, CommonConstants.ELLIPSIS, -1);
        createSidePages(pages, currentUri, currentPageNum, currentPageNum - 2, totalPages);
    }

    private void formRightPaginationModel(String currentUri, int currentPageNum, int totalPages, List<Page> pages) {
        createSidePages(pages, currentUri, currentPageNum, 1, currentPageNum + 2);
        createSinglePage(pages, CommonConstants.ELLIPSIS, -1);
        createSinglePage(pages, currentUri, totalPages);
    }

    private String createPreviousUri(String currentUri, int currentPageNum) {
        if (currentPageNum > 1) {
            return setPageUri(currentUri, currentPageNum - 1);
        } else {
            return null;
        }
    }

    private String createNextUri(String currentUri, int currentPageNum, int totalEntities, int entitiesPageLimit) {
        if (totalEntities > currentPageNum * entitiesPageLimit) {
            return setPageUri(currentUri, currentPageNum + 1);
        } else {
            return null;
        }
    }

    private String setPageUri(String currentUri, int pageNum) {
        if (pageNum > 1 && !currentUri.equals(CommonConstants.CURRENT)) {
            return currentUri + CommonConstants.PAGE_EQUAL + pageNum;
        } else {
            return currentUri;
        }
    }

    private int countTotalPages(int totalEntities, int entitiesPageLimit) {
        int totalPages = totalEntities / entitiesPageLimit;
        if (totalEntities % entitiesPageLimit != 0) {
            totalPages++;
        }
        return totalPages;
    }

    private void createSinglePage(List<Page> pages, String currentUri, int pageNum) {
        pages.add(new Page(setPageUri(currentUri, pageNum), pageNum));
    }

    private void createSidePages(List<Page> pages, String currentUri, int currentPage, int startPage, int totalPages) {
        for (int i = startPage; i <= totalPages; i++) {
            if (i == currentPage) {
                createSinglePage(pages, CommonConstants.CURRENT, i);
            } else {
                createSinglePage(pages, currentUri, i);
            }
        }
    }
}