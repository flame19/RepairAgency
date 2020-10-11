package ua.repair_agency.models.pagination;

import ua.repair_agency.constants.CommonConstants;

public class Page {

    private final String pageUri;
    private final int pageNum;

    public Page(String pageUri, int pageNum) {
        this.pageUri = pageUri;
        this.pageNum = pageNum;
    }

    public String getPageUri() {
        return pageUri;
    }

    public int getPageNum() {
        return pageNum;
    }

    public boolean isEllipsis() {
        return pageUri.contains(CommonConstants.ELLIPSIS);
    }

    public boolean isCurrent() {
        return pageUri.contains(CommonConstants.CURRENT);
    }
}
