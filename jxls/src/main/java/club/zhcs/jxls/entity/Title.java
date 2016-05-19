package club.zhcs.jxls.entity;

import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.WritableCellFormat;
import club.zhcs.jxls.Formats;

public class Title {
	private Point start;
	private String title;
	private int row;
	private int column;
	private WritableCellFormat format;

	public Title(Point start, String title, int row, int column, WritableCellFormat format) {
		this.start = start;
		this.title = title;
		this.row = row;
		this.column = column;

		this.format = format;
	}

	public WritableCellFormat getFormat() {
		return this.format;
	}

	public void setFormat(WritableCellFormat format) {
		this.format = format;
	}

	public Point getStart() {
		return this.start;
	}

	public void setStart(Point start) {
		this.start = start;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getRow() {
		return this.row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return this.column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public Title(Point start, String title, int row, int column) {
		this.start = start;
		this.title = title;
		this.row = row;
		this.column = column;
		this.format = Formats.createFormat(20, true, false, Alignment.CENTRE, VerticalAlignment.CENTRE, false);
	}

	public Title() {
	}
}
