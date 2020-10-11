package ua.repair_agency.services.pagination;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ua.repair_agency.models.pagination.Page;
import ua.repair_agency.models.pagination.PaginationModel;
import ua.repair_agency.services.pagination.impl.PagePaginationHandler;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PagePaginationHandlerTest {

    private final Pagination pagination = new PagePaginationHandler();

    @ParameterizedTest
    @CsvSource({"/somePage, 1, 5, 10",
            "/somePage, 1, 10, 15"})
    void creationPagModel_totalEntitiesLessPageEntitiesAmount_giveNullPagModel(
            String currentUri, int currentPageNum, int totalEntities, int entitiesPageLimit) {

        PaginationModel paginationModel = pagination.createPaginationModel(
                currentUri, currentPageNum, totalEntities, entitiesPageLimit);

        assertNull(paginationModel);
    }

    @ParameterizedTest
    @CsvSource({"/somePage, 1, 100, 10, ",
            "/somePage, 6, 100, 10, /somePage?page=5",
            "/somePage, 10, 100, 10, /somePage?page=9"})
    void creationPagModel_concretePage_giveCorrespondingPrevPageUri(
            String currentUri, int currentPageNum, int totalEntities, int entitiesPageLimit, String previousUri) {

        PaginationModel paginationModel = pagination.createPaginationModel(
                currentUri, currentPageNum, totalEntities, entitiesPageLimit);

        assertEquals(previousUri, paginationModel.getPreviousUri());
    }

    @ParameterizedTest
    @CsvSource({"/somePage, 1, 100, 10, /somePage?page=2",
            "/somePage, 6, 100, 10, /somePage?page=7",
            "/somePage, 10, 100, 10, "})
    void creationPagModel_concretePage_giveCorrespondingNextPageUri(
            String currentUri, int currentPageNum, int totalEntities, int entitiesPageLimit, String nextUri) {

        PaginationModel paginationModel = pagination.createPaginationModel(
                currentUri, currentPageNum, totalEntities, entitiesPageLimit);

        assertEquals(nextUri, paginationModel.getNextUri());
    }

    @ParameterizedTest
    @CsvSource({"/somePage, 1, 100, 10, current, /somePage?page=2, /somePage?page=3, ellipsis, /somePage?page=10"})
    void creationPagModel_fromFirstPage_createCurrent_NexTwo_Ellipsis_Last_Pages(
            String currentUri, int currentPageNum, int totalEntities, int entitiesPageLimit,
            String current, String nextPage, String secondNextPage, String ellipsis, String lastPage) {

        PaginationModel paginationModel = pagination.createPaginationModel(
                currentUri, currentPageNum, totalEntities, entitiesPageLimit);
        List<Page> pagPages = paginationModel.getPages();

        assertAll(
                () -> assertEquals(current, pagPages.get(0).getPageUri()),
                () -> assertEquals(nextPage, pagPages.get(1).getPageUri()),
                () -> assertEquals(secondNextPage, pagPages.get(2).getPageUri()),
                () -> assertEquals(ellipsis, pagPages.get(3).getPageUri()),
                () -> assertEquals(lastPage, pagPages.get(4).getPageUri()));
    }

    @ParameterizedTest
    @CsvSource({"/somePage, 10, 100, 10, /somePage, ellipsis, /somePage?page=8, /somePage?page=9, current"})
    void creationPagModel_fromLastPage_createFirst_Ellipsis_PrevTwo_Current_Pages(
            String currentUri, int currentPageNum, int totalEntities, int entitiesPageLimit,
            String firstPage, String ellipsis, String prevSecondPage, String prevPage, String current) {

        PaginationModel paginationModel = pagination.createPaginationModel(
                currentUri, currentPageNum, totalEntities, entitiesPageLimit);
        List<Page> pagPages = paginationModel.getPages();

        assertAll(
                () -> assertEquals(firstPage, pagPages.get(0).getPageUri()),
                () -> assertEquals(ellipsis, pagPages.get(1).getPageUri()),
                () -> assertEquals(prevSecondPage, pagPages.get(2).getPageUri()),
                () -> assertEquals(prevPage, pagPages.get(3).getPageUri()),
                () -> assertEquals(current, pagPages.get(4).getPageUri()));
    }

    @ParameterizedTest
    @CsvSource({
            "/somePage, 5, 100, 10, " +
                    "/somePage, ellipsis, /somePage?page=3, /somePage?page=4, current, " +
                    "/somePage?page=6, /somePage?page=7, ellipsis, /somePage?page=10"})
    void creationPagModel_fromMiddlePage_createFirst_Ellipsis_PrevTwo_Current_NextTwo_Ellipsis_Last_Pages(
            String currentUri, int currentPageNum, int totalEntities, int entitiesPageLimit,
            String firstPage, String firstEllipsis, String prevSecondPage, String prevPage, String current,
            String nextPage, String secondNextPage, String secondEllipsis, String lastPage) {

        PaginationModel paginationModel = pagination.createPaginationModel(
                currentUri, currentPageNum, totalEntities, entitiesPageLimit);
        List<Page> pagPages = paginationModel.getPages();

        assertAll(
                () -> assertEquals(firstPage, pagPages.get(0).getPageUri()),
                () -> assertEquals(firstEllipsis, pagPages.get(1).getPageUri()),
                () -> assertEquals(prevSecondPage, pagPages.get(2).getPageUri()),
                () -> assertEquals(prevPage, pagPages.get(3).getPageUri()),
                () -> assertEquals(current, pagPages.get(4).getPageUri()),
                () -> assertEquals(nextPage, pagPages.get(5).getPageUri()),
                () -> assertEquals(secondNextPage, pagPages.get(6).getPageUri()),
                () -> assertEquals(secondEllipsis, pagPages.get(7).getPageUri()),
                () -> assertEquals(lastPage, pagPages.get(8).getPageUri()));
    }

    @ParameterizedTest
    @CsvSource({
            "/somePage, 4, 70, 10, /somePage, /somePage?page=2, /somePage?page=3, current, " +
                    "/somePage?page=5, /somePage?page=6, /somePage?page=7"})
    void creationPagModel_pageWithoutEllipsis_createFirst_PrevTwo_Current_NextTwo_Last_Pages(
            String currentUri, int currentPageNum, int totalEntities, int entitiesPageLimit,
            String firstPage, String prevSecondPage, String prevPage, String current,
            String nextPage, String secondNextPage, String lastPage) {

        PaginationModel paginationModel = pagination.createPaginationModel(
                currentUri, currentPageNum, totalEntities, entitiesPageLimit);
        List<Page> pagPages = paginationModel.getPages();

        assertAll(
                () -> assertEquals(firstPage, pagPages.get(0).getPageUri()),
                () -> assertEquals(prevSecondPage, pagPages.get(1).getPageUri()),
                () -> assertEquals(prevPage, pagPages.get(2).getPageUri()),
                () -> assertEquals(current, pagPages.get(3).getPageUri()),
                () -> assertEquals(nextPage, pagPages.get(4).getPageUri()),
                () -> assertEquals(secondNextPage, pagPages.get(5).getPageUri()),
                () -> assertEquals(lastPage, pagPages.get(6).getPageUri()));
    }
}
