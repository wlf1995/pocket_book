package com.ibicn.hr.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PageResult<T> {
    //总记录数
    private int total = 0;
    //数据
    private List<T> content = new ArrayList<>();

    public PageResult() {
    }

    public PageResult(Page page) {
        this.content = page.getContent();
        this.total = (int) page.getTotalElements();
    }

    public PageResult(Page page, List list) {
        this.content = list;
        this.total = page.getTotalPages();
    }

    public PageResult(PageResult page, List list) {
        this.content = list;
        this.total = page.getTotal();
    }


    public static PageResult getPageResult(PageResult page, List list) {
        PageResult pageUtil = new PageResult(page, list);
        return pageUtil;
    }

    public static PageResult getPageResult(Page page, List list) {
        PageResult pageUtil = new PageResult(page, list);
        return pageUtil;
    }

    public static PageResult getPageResult(Page page) {
        PageResult pageUtil = new PageResult(page);
        return pageUtil;
    }
}
