package org.jskat.extract;

import org.apache.batik.transcoder.SVGAbstractTranscoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.jskat.gui.CardExtractorGUI;

import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;

public class CardExtractor implements Runnable {

    final String svgFileLocation;
    final String cardName;
    final Rectangle areaOfInterest;
    final Float scale;
    final CardExtractorGUI gui;

    public CardExtractor(final CardExtractorGUI gui, final String svgFileLocation,
                         final String cardName, final Rectangle aoi, final Float scale) {

        this.gui = gui;
        this.svgFileLocation = svgFileLocation;
        this.cardName = cardName;
        this.areaOfInterest = aoi;
        this.scale = scale;
    }

    @Override
    public void run() {
        try {
            tile();
        } catch (final TranscoderException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            gui.taskCompleted();
        }
    }

    public void tile() throws TranscoderException, IOException {

        final OutputStream outputStream = createOutputStream();
        final PNGTranscoder trans = createTrancoder();

        trans.transcode(createInput(), new TranscoderOutput(outputStream));

        // Flush and close the output.
        outputStream.flush();
        outputStream.close();

        System.out.println(cardName + " created");
    }

    private PNGTranscoder createTrancoder() {

        final PNGTranscoder trans = new PNGTranscoder();

        // Set hints to indicate the dimensions of the output image
        // and the input area of interest.
        trans.addTranscodingHint(SVGAbstractTranscoder.KEY_WIDTH, new Float(areaOfInterest.width) * scale);
        trans.addTranscodingHint(SVGAbstractTranscoder.KEY_HEIGHT, new Float(areaOfInterest.height) * scale);
        trans.addTranscodingHint(SVGAbstractTranscoder.KEY_AOI, areaOfInterest);

        return trans;
    }

    private TranscoderInput createInput() throws MalformedURLException {
        final String svgURI = new File(svgFileLocation).toURL().toString();
        return new TranscoderInput(svgURI);
    }

    private OutputStream createOutputStream() throws FileNotFoundException {
        return new FileOutputStream(System.getProperty("java.io.tmpdir")
                + System.getProperty("file.separator") + cardName);
    }
}
