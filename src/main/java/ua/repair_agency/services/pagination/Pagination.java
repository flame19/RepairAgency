package ua.repair_agency.services.pagination;

import ua.repair_agency.models.pagination.PaginationModel;

public interface Pagination {
    PaginationModel createPaginationModel(String currentUri, int currentPageNum, int totalEntities, int entitiesPageLimit);
}