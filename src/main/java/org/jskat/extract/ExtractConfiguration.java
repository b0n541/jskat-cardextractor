package org.jskat.extract;

public enum ExtractConfiguration {

	DONDORF_GERMAN(
			"src/main/resources/org/jskat/samples/fixed/dondorf_german.svg",
			1027, 615, 5, 13, "A23456789TJQK", "CDHSB", 0, 246),

	DONDORF_FRENCH(
			"src/main/resources/org/jskat/samples/fixed/dondorf_french.svg",
			1027, 615, 5, 13, "A23456789TJQK", "CDHSB", 0, 246),

	DONDORF_TOURNAMENT(
			"src/main/resources/org/jskat/samples/fixed/dondorf_tournament.svg",
			1027, 615, 5, 13, "A23456789TJQK", "CDHSB", 0, 246),

	ISS_GERMAN("src/main/resources/org/jskat/samples/fixed/iss_german.svg",
			1027, 615, 5, 13, "A23456789TJQK", "CDHSB", 0, 246),

	ISS_FRENCH("src/main/resources/org/jskat/samples/fixed/iss_french.svg",
			1027, 615, 5, 13, "A23456789TJQK", "CDHSB", 0, 246),

	ISS_TOURNAMENT(
			"src/main/resources/org/jskat/samples/fixed/iss_tournament.svg",
			1027, 615, 5, 13, "A23456789TJQK", "CDHSB", 0, 246),

	ORNAMENTAL_FRENCH(
			"src/main/resources/org/jskat/samples/fixed/ornamental_french.svg",
			1807, 945, 5, 13, "A23456789TJQK", "CDHSB", 0, 246),

	TANGO_FRENCH("src/main/resources/org/jskat/samples/fixed/tango_french.svg",
			1885, 975, 5, 13, "A23456789TJQK", "CDHSB", 4, 246),

	WILLIAMTELL_GERMAN(
			"src/main/resources/org/jskat/samples/fixed/william-tell_german.svgz",
			1664, 1005, 5, 13, "AKQJT98765432", "HDSCB", 0, 246),

	XSKAT_GERMAN("src/main/resources/org/jskat/samples/fixed/xskat_german.svg",
			1872, 1120, 5, 13, "AKQJT98765432", "HDSCB", 0, 246);

	public String location = "";
	public int width = 0;
	public int height = 0;
	public int rows = 0;
	public int columns = 0;
	public String rowSymbols = "";
	public String columnSymbols = "";
	public int gap = 0;
	public int targetHeight = 0;

	ExtractConfiguration(String location, int width, int height, int rows,
			int columns, String rowSymbols, String columnSymbols, int gap,
			int targetHeight) {
		this.location = location;
		this.width = width;
		this.height = height;
		this.rows = rows;
		this.columns = columns;
		this.rowSymbols = rowSymbols;
		this.columnSymbols = columnSymbols;
		this.gap = gap;
		this.targetHeight = targetHeight;
	}
}
