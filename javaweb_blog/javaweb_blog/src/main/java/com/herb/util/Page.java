package com.herb.util;

import lombok.Data;

import java.util.List;

/**
 * @author: herb
 * @Date: 2023/3/8
 * @Description: TODO 分页工具类
 * @version: 1.0
 */
@Data
public class Page<T> {
    //当前页 （前台传递的页数， 如果没传递，默认是第一页）
    private Integer pageNum;

    //每页显示的数量
    private Integer pageSize;

    //总记录数
    private long totalCount;

    //总页数
    private Integer totalPages;
    //上一页
    private Integer prePage;
    //下一页
    private Integer nextPage;

    //导航开始页（当前页 -5， 如果当前页 -5 小于1 ，开始页就是1，结束页=开始页+9， 如果开始页+9 》 总结数， 导航结束页 = 总页数）
    private Integer startNavPage;

    //导航结束页 （当前页+ 4， 如果当前页+4大于总页数，导航结束页=总页数， 此时导航开始页=导航结束页 -9， 如果 导航结束页 -9 <1 , 导航开始页 = 1）
    private Integer endNavPage;

    //当前页的数据集
    private List<T> dataList;


    public Page(Integer pageNum, Integer pageSize, long totalCount) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalCount = totalCount;

        //总页数 （总记录数/每页显示的数量；向上取整）
        this.totalPages = (int) Math.ceil(totalCount/(pageSize * 1.0));

        //上一页
        this.prePage = pageNum - 1 < 1 ? 1 : pageNum - 1;

        //下一页
        this.nextPage = pageNum + 1 > totalPages ? totalPages : pageNum + 1;

        //导航开始页
        this.startNavPage = pageNum - 5;

        //导航结束页
        this.endNavPage = pageNum + 4;

        //导航开始页
        if (this.startNavPage < 1) {
            this.startNavPage = 1;
            this.endNavPage = this.startNavPage + 9 > totalPages ? totalPages : this.startNavPage + 9;
        }

        //导航结束页
        if (this.endNavPage > totalPages) {
            this.endNavPage = totalPages;
            this.startNavPage = this.endNavPage - 9 < 1 ? 1 : this.endNavPage - 9 ;
        }
    }
}
