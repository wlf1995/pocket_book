package com.ibicn.hr.util;


import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
@Data
public class PageUtil<T>   {
    private int total = 0;
    private List<T> content = new ArrayList<>();

    public PageUtil() {
    }

    public PageUtil(Page page) {
        this.content=page.getContent();
        this.total=page.getSize();
    }
    public PageUtil(Page page,List list) {
        this.content=list;
        this.total=page.getTotalPages();
    }
    public static PageUtil  getPageUtil(Page page,List list){
        PageUtil pageUtil=new PageUtil(page,list);
        return pageUtil;
    }
}
