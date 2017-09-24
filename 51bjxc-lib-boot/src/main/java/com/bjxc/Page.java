package com.bjxc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Page<U> implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1119108689571941587L;

	private static int DEFAULT_PAGE_SIZE = 20;
    
    // ÿҳ�ļ�¼��
    private int pageSize = DEFAULT_PAGE_SIZE;
    
    // ��ǰҳ
    private int pageNo = 1;
    
    // �ܼ�¼��
    private long rowCount;
    
    // ��ǰҳ����
    private List<U> data = Collections.EMPTY_LIST;
    
    
    /**
     * ���췽����ֻ�����ҳ.
     */
    public Page() {
        this(0, 0, DEFAULT_PAGE_SIZE, new ArrayList<U>());
    }
    
    /**
     * <Ĭ�Ϲ��캯��>
     */
    public Page(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }
    
    /**
     * Ĭ�Ϲ��췽��.
     * 
     * @param start
     *            ��ҳ���������ݿ��е���ʼλ��
     * @param totalSize
     *            ���ݿ����ܼ�¼����
     * @param pageSize
     *            ��ҳ����
     * @param data
     *            ��ҳ����������
     */
    public Page(int pageNo, int totalSize, int pageSize, List<U> data) {
        this.pageSize = pageSize;
        this.pageNo = pageNo;
        this.rowCount = totalSize;
        this.data = data;
    }
    
    /**
     * ȡ��ҳ��.
     */
    public long getTotalPageCount() {
        if (rowCount % pageSize == 0)
            return rowCount / pageSize;
        else
            return rowCount / pageSize + 1;
    }
    
    public int getStartRow(){
    	return (this.pageNo - 1) * this.pageSize;
    }
    
    public int getPageSize() {
        return pageSize;
    }
    
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
    public int getPageNo() {
        return pageNo;
    }
    
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
    
    
    public long getRowCount() {
		return rowCount;
	}

	public void setRowCount(long rowCount) {
		this.rowCount = rowCount;
	}

	public List<U> getData() {
        return data;
    }
    
    public void setData(List<U> data) {
        this.data = data;
    }

}
