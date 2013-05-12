package org.jskat;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.batik.transcoder.SVGAbstractTranscoder;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

public class SaveAsSeparateCards {

	PNGTranscoder trans = new PNGTranscoder();

	static Map<Integer, String> suits = new HashMap<>();
	static Map<Integer, String> ranks = new HashMap<>();

	public void tile(String inputFilename, String outputFilename,
			Rectangle aoi, int scale) throws Exception {
		// Set hints to indicate the dimensions of the output image
		// and the input area of interest.
		trans.addTranscodingHint(SVGAbstractTranscoder.KEY_WIDTH, new Float(
				aoi.width) * scale);
		trans.addTranscodingHint(SVGAbstractTranscoder.KEY_HEIGHT, new Float(
				aoi.height) * scale);
		trans.addTranscodingHint(SVGAbstractTranscoder.KEY_AOI, aoi);

		// Transcode the file.
		String svgURI = new File(inputFilename).toURL().toString();
		TranscoderInput input = new TranscoderInput(svgURI);
		OutputStream ostream = new FileOutputStream(outputFilename);
		TranscoderOutput output = new TranscoderOutput(ostream);
		trans.transcode(input, output);

		// Flush and close the output.
		ostream.flush();
		ostream.close();
	}

	public static void main(String[] args) throws Exception {
		// Rasterize the samples/anne.svg document and save it
		// as tiles.
		SaveAsSeparateCards p = new SaveAsSeparateCards();

		// String in =
		// "src/main/resources/net/b0n541/samples/fixed/xskat_german.svg";
		// int documentWidth = 1872;
		// int documentHeight = 1120;
		// int xOffset = 0;
		// int yOffset = 0;
		// int scale = 1;

		// String in =
		// "src/main/resources/net/b0n541/samples/fixed/william-tell_german.svgz";
		// int documentWidth = 1664;
		// int documentHeight = 1005;
		// int xOffset = 0;
		// int yOffset = 0;
		// int scale = 1;

		// String in =
		// "src/main/resources/net/b0n541/samples/fixed/dondorf_french.svg";
		// int documentWidth = 1027;
		// int documentHeight = 615;
		// int xOffset = 0;
		// int yOffset = 0;
		// int scale = 1;

		// String in =
		// "src/main/resources/net/b0n541/samples/fixed/ornamental_french"
		// + ".svg";
		// int documentWidth = 1807;
		// int documentHeight = 945;
		// int xOffset = 0;
		// int yOffset = 0;
		// int scale = 1;

		// String in =
		// "src/main/resources/net/b0n541/samples/fixed/tango_french.svg";
		// int documentWidth = 1885;
		// int documentHeight = 975;
		// int xOffset = 0;
		// int yOffset = 0;
		// int scale = 1;

		String in = "src/main/resources/net/b0n541/samples/fixed/tigullio-modern_tournament.svg";
		int documentWidth = 820;
		int documentHeight = 440;
		int xOffset = 0;
		int yOffset = 0;
		int scale = 10;

		int cardWidth = documentWidth / 13;
		int cardHeight = documentHeight / 5;

		suits.put(0, "D");
		suits.put(1, "H");
		suits.put(2, "S");
		suits.put(3, "C");

		ranks.put(0, "A");
		ranks.put(1, "2");
		ranks.put(2, "3");
		ranks.put(3, "4");
		ranks.put(4, "5");
		ranks.put(5, "6");
		ranks.put(6, "7");
		ranks.put(7, "8");
		ranks.put(8, "9");
		ranks.put(9, "T");
		ranks.put(10, "J");
		ranks.put(11, "Q");
		ranks.put(12, "K");

		for (int yCard = 0; yCard < 5; yCard++) {
			for (int xCard = 0; xCard < 13; xCard++) {
				String cardName = suits.get(yCard) + "-" + ranks.get(xCard)
						+ ".png";
				p.tile(in, cardName, new Rectangle(cardWidth * xCard + xOffset,
						cardHeight * yCard + yOffset, cardWidth, cardHeight),
						scale);
				System.out.println(cardName + " created");
			}
		}
		System.exit(0);
	}
}