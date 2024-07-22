package com.inventory.repositories.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BookInventoryVo {
	
	private String branchId;
	private String bookCode;
	private String bookName;
	private int inventory;
	private Date inDate;
	private Date outDate;
	private int price;
	private String kindCode;
	private int sumInInventory;
	private int sumOutInventory;
	
	public BookInventoryVo() {
		
	}


	public BookInventoryVo(String branchId, String bookCode, String bookName, Integer inventory, Date inDate,
			Date outDate, int price, String kindCode, int sumInInventory, int sumOutInventory) {

		this.branchId = branchId;
		this.bookCode = bookCode;
		this.bookName = bookName;
		this.inventory = inventory;
		this.price = price;
		this.kindCode = kindCode;
		this.inDate = inDate;
		this.outDate = outDate;
		this.sumInInventory = sumInInventory;
		this.sumOutInventory = sumOutInventory;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getBookCode() {
		return bookCode;
	}

	public void setBookCode(String bookCode) {
		this.bookCode = bookCode;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public int getInventory() {
		return inventory;
	}

	public void setInventory(int inventory) {
		this.inventory = inventory;
	}

	@Override
	public String toString() {
		return "BookInventoryVo [branchId=" + branchId + ", bookCode=" + bookCode + ", bookName=" + bookName
				+ ", inventory=" + inventory + ", inDate=" + inDate + ", outDate=" + outDate + "]";
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getInDate() {
		if (inDate == null) {
			return "";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yy/MM/dd HH:mm");
		return dateFormat.format(inDate);
	}

	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}

	public String getOutDate() {
		if (outDate == null) {
			return "";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yy/MM/dd HH:mm");
		return dateFormat.format(outDate);
	}

	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}

	public String getKindCode() {
		return kindCode;
	}

	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}


	public int getSumInInventory() {
		return sumInInventory;
	}


	public void setSumInInventory(int sumInInventory) {
		this.sumInInventory = sumInInventory;
	}


	public int getSumOutInventory() {
		return sumOutInventory;
	}


	public void setSumOutInventory(int sumOutInventory) {
		this.sumOutInventory = sumOutInventory;
	}
	
	
}
