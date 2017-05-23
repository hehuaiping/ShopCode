package com.xh.domain;

import java.io.Serializable;
import java.util.List;

public class Page<T> implements Serializable {
	// ��ҳ��
	private int countPage;
	// ������
	private int totalNum;
	// ÿҳ��ʾ��������¼
	private int rowPage;
	// ��ҳ
	private int firstPage;
	// ��һҳ
	private int upPage;
	// ��ǰҳ
	private int thisPage;
	// ��һҳ
	private int nextPage;
	// βҳ
	private int lastPage;
	// ���ݼ���
	private List<T> list;

	/**
	 * @return the countPage
	 */
	public int getCountPage() {
		return countPage;
	}

	/**
	 * @param countPage
	 *            the countPage to set
	 */
	public void setCountPage(int countPage) {
		this.countPage = countPage;
	}

	/**
	 * @return the totalNum
	 */
	public int getTotalNum() {
		return totalNum;
	}

	/**
	 * @param totalNum
	 *            the totalNum to set
	 */
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	/**
	 * @return the rowPage
	 */
	public int getRowPage() {
		return rowPage;
	}

	/**
	 * @param rowPage
	 *            the rowPage to set
	 */
	public void setRowPage(int rowPage) {
		this.rowPage = rowPage;
	}

	/**
	 * @return the firstPage
	 */
	public int getFirstPage() {
		return firstPage;
	}

	/**
	 * @param firstPage
	 *            the firstPage to set
	 */
	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}

	/**
	 * @return the upPage
	 */
	public int getUpPage() {
		return upPage;
	}

	/**
	 * @param upPage
	 *            the upPage to set
	 */
	public void setUpPage(int upPage) {
		this.upPage = upPage;
	}

	/**
	 * @return the thisPage
	 */
	public int getThisPage() {
		return thisPage;
	}

	/**
	 * @param thisPage
	 *            the thisPage to set
	 */
	public void setThisPage(int thisPage) {
		this.thisPage = thisPage;
	}

	/**
	 * @return the nextPage
	 */
	public int getNextPage() {
		return nextPage;
	}

	/**
	 * @param nextPage
	 *            the nextPage to set
	 */
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	/**
	 * @return the lastPage
	 */
	public int getLastPage() {
		return lastPage;
	}

	/**
	 * @param lastPage
	 *            the lastPage to set
	 */
	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

	/**
	 * @return the list
	 */
	public List<T> getList() {
		return list;
	}

	/**
	 * @param list
	 *            the list to set
	 */
	public void setList(List<T> list) {
		this.list = list;
	}

}
