package org.jskat.extract;

import java.awt.Rectangle;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.batik.transcoder.TranscoderException;
import org.jskat.gui.CardExtractorGUI;

public class CardConverter {

	public static void convertAllCards(CardExtractorGUI gui,
			ExtractConfiguration conf) throws TranscoderException, IOException {

		ExecutorService threadPool = Executors.newFixedThreadPool(Runtime
				.getRuntime().availableProcessors() * 2);

		int cardWidth = conf.width / conf.columns;
		int cardHeight = conf.height / conf.rows;

		gui.setTasks(conf.columns * conf.rows);

		for (int yCard = 0; yCard < conf.columnSymbols.length(); yCard++) {
			for (int xCard = 0; xCard < conf.rowSymbols.length(); xCard++) {

				String cardName = String.valueOf(conf.columnSymbols
						.charAt(yCard))
						+ "-"
						+ String.valueOf(conf.rowSymbols.charAt(xCard))
						+ ".png";

				Rectangle areaOfInterest = new Rectangle(cardWidth * xCard,
						cardHeight * yCard, cardWidth - conf.gap, cardHeight
								- conf.gap);

				threadPool.execute(new CardExtractor(gui, conf.location,
						cardName, areaOfInterest, getScale(conf)));
			}
		}

		threadPool.shutdown();
		while (!threadPool.isTerminated()) {
		}
		System.out.println("Finished all threads");
	}

	private static float getScale(ExtractConfiguration conf) {
		return (float) conf.targetHeight / (conf.height / conf.rows);
	}
}