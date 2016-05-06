package image;

import java.awt.Insets;

public class Table {
	private String title;
	private int width;
	private int height;
	private int bufferHeight;
	private int bufferWidth;
	private int rows;
	private int columns;
	private int padding;
	private Insets inset;
	private String[] tableData;
	
	public Table(String title, int width, int height, int bufferHeight, int bufferWidth, int rows, int columns, int padding, Insets inset, String[] tableData) {
		this.title = title;
		this.width = width;
		this.height = height;
		this.bufferHeight = bufferHeight;
		this.bufferWidth = bufferWidth;
		this.rows = rows;
		this.columns = columns;
		this.padding = padding;
		this.inset = inset;
		this.tableData = tableData;
	}
	
	public String[] getTableData() {
		return tableData;
	}
	public void setTableData(String[] tableData) {
		this.tableData = tableData;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getBufferHeight() {
		return bufferHeight;
	}
	public void setBufferHeight(int bufferHeight) {
		this.bufferHeight = bufferHeight;
	}
	public int getBufferWidth() {
		return bufferWidth;
	}
	public void setBufferWidth(int bufferWidth) {
		this.bufferWidth = bufferWidth;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getColumns() {
		return columns;
	}
	public void setColumns(int columns) {
		this.columns = columns;
	}
	public int getPadding() {
		return padding;
	}
	public void setPadding(int padding) {
		this.padding = padding;
	}
	public Insets getInset() {
		return inset;
	}
	public void setInset(Insets inset) {
		this.inset = inset;
	}
}
