package org.jskat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

public class SaveAsJpeg {

	public static void main(String[] args) throws Exception {

		// Create a JPEG transcoder
		PNGTranscoder t = new PNGTranscoder();

		// Create the transcoder input.
		String svgURI = new File(
				"src/main/resources/net/b0n541/samples/xskat.svg").toURL()
				.toString();
		TranscoderInput input = new TranscoderInput(svgURI);

		// Create the transcoder output.
		OutputStream ostream = new FileOutputStream("out.jpg");
		TranscoderOutput output = new TranscoderOutput(ostream);

		// Save the image.
		t.transcode(input, output);

		// Flush and close the stream.
		ostream.flush();
		ostream.close();
		System.exit(0);
	}
}