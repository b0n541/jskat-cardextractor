package org.jskat.extract;

import org.apache.batik.transcoder.TranscoderException;
import org.jskat.gui.CardExtractorGUI;

import java.awt.*;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CardConverter {

    public static void convertAllCards(final CardExtractorGUI gui,
                                       final ExtractConfiguration conf) throws TranscoderException, IOException {

        final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime
                .getRuntime().availableProcessors() * 2);

        final int columns = conf.columnSymbols.length();
        final int rows = conf.rowSymbols.length();
        final int cardWidth = conf.width / columns;
        final int cardHeight = conf.height / rows;

        gui.setTasks(columns * rows);

        for (int yCard = 0; yCard < columns; yCard++) {
            for (int xCard = 0; xCard < rows; xCard++) {

                final String cardName = String.valueOf(conf.rowSymbols.charAt(xCard))
                        + "-"
                        + String.valueOf(conf.columnSymbols.charAt(yCard))
                        + ".png";

                final Rectangle areaOfInterest = new Rectangle(cardWidth * yCard,
                        cardHeight * xCard, cardWidth - conf.gap, cardHeight
                        - conf.gap);

                threadPool.execute(
                        new CardExtractor(gui, conf.location, cardName, areaOfInterest, getScale(conf.targetHeight, conf.height, rows)));
            }
        }

        threadPool.shutdown();
        while (!threadPool.isTerminated()) {
        }
        System.out.println("Finished all threads");
    }

    private static float getScale(final int targetHeight, final int height, final int rows) {
        return (float) targetHeight / (height / rows);
    }
}