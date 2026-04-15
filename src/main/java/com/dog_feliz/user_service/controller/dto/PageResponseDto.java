package com.dog_feliz.user_service.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;

import java.util.List;

public class PageResponseDto<T> {

    private List<T> data;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    public PageResponseDto() {}

    public PageResponseDto(Page<T> page) {
        this.data = page.getContent();
        this.page = page.getNumber();
        this.size = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
    }

    @JsonCreator
    public PageResponseDto(
            @JsonProperty("data") List<T> data,
            @JsonProperty("page") int page,
            @JsonProperty("size") int size,
            @JsonProperty("total_elements") long totalElements,
            @JsonProperty("total_pages") int totalPages) {
        this.data = data;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public boolean isEmpty() {
        return data == null || data.isEmpty();
    }

    public List<T> getContent() {
        return data;
    }

    public boolean isLast() {
        return page >= totalPages - 1;
    }

    public List<T> getData() {
        return data;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }


}