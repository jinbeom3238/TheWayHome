package com.btc.thewayhome.page;

public class PageMakerDto {

    private int startPage;          // 시작 페이지
    private int endPage;            // 끝 페이지
    private boolean prev, next;     // 이전 페이지 또는 다음 페이지 출력 유/무
    private int total;              // 전체 게시물 개수
    private int totalPage;          // 전체 페이지 수
    private Criteria criteria;      // 현재 페이지, 페이지당 게시물 정보

    public PageMakerDto(Criteria criteria, int total) {

        this.criteria = criteria;
        this.total = total;

        // 버튼 5개씩
        this.endPage = (int) (Math.ceil(criteria.getPageNum() / 5.0)) * 5;
        this.startPage = this.endPage - 4;

        int realEnd = (int) (Math.ceil(total * 1.0 / criteria.getAmount()));

        if (realEnd < this.endPage)
            this.endPage = realEnd;

        this.prev = this.startPage > 1;
        this.next = this.endPage < realEnd;

        this.totalPage = total / criteria.getAmount();
        if (total % criteria.getAmount() != 0)
            totalPage += 1;

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

    public boolean isPrev() {
        return prev;
    }

    public void setPrev(boolean prev) {
        this.prev = prev;
    }

    public boolean isNext() {
        return next;
    }

    public void setNext(boolean next) {
        this.next = next;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public Criteria getCriteria() {
        return criteria;
    }

    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public String toString() {
        return "PageMakerDto{" +
                "startPage=" + startPage +
                ", endPage=" + endPage +
                ", prev=" + prev +
                ", next=" + next +
                ", total=" + total +
                ", totalPage=" + totalPage +
                ", criteria=" + criteria +
                "}";
    }
}
