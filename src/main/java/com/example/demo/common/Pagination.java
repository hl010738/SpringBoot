package com.example.demo.common;

import java.io.Serializable;
import java.util.List;

/**
 * Pagination Entity
 *
 * @author Bamboo
 * @date 2017/2/17
 */
public class Pagination<T>  {
    private int pageNo = 1;
    private int pageSize = 20;
    private int totalRecord;
    private List<T> list;

    public Pagination(){super();}

    public Pagination(int pageNo){
        this.pageNo = pageNo;
    }

    public Pagination(int pageNo,int pageSize){
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    /**
     * Pagination Construct
     * @param pageNo pageNo
     * @param pageSize pageSize
     * @param list List Data
     */
    public Pagination(int pageNo,int pageSize,List<T> list){
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.list = list;
    }

    public Pagination(List<T> list){
        this.pageNo = pageNo;
        this.pageNo = pageSize;
        this.list = list;

    }

    public Pagination(int totalRecord,List<T> list){
        this.pageNo = pageNo;
        this.pageNo = pageSize;
        this.totalRecord = totalRecord;
        this.list = list;

    }

    public Pagination(int pageNo,int pageSize, int totalRecord, List<T> list){
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.list = list;
        this.totalRecord = totalRecord;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
