package org.jskat.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.gvt.GVTTreeRendererAdapter;
import org.apache.batik.swing.gvt.GVTTreeRendererEvent;
import org.apache.batik.swing.svg.GVTTreeBuilderAdapter;
import org.apache.batik.swing.svg.GVTTreeBuilderEvent;
import org.apache.batik.swing.svg.SVGDocumentLoaderAdapter;
import org.apache.batik.swing.svg.SVGDocumentLoaderEvent;
import org.apache.batik.transcoder.TranscoderException;
import org.jskat.extract.ExtractConfiguration;
import org.jskat.extract.SaveAsSeparateCards;

public class CardExtractorGUI extends JFrame {

	// The "Load" button, which displays up a file chooser upon clicking.
	protected JButton loadButton = new JButton("Load...");

	// Starts the extraction
	protected JButton extractButton = new JButton("Extract cards...");

	protected JComboBox<ExtractConfiguration> cardSets = new JComboBox<>();

	// The status label.
	protected JLabel label = new JLabel();

	// The SVG canvas.
	protected JSVGCanvas svgCanvas = new JSVGCanvas();

	public CardExtractorGUI() {
		setTitle("Card Extractor");
		setMinimumSize(new Dimension(600, 400));

		setContentPane(createComponents());

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public JComponent createComponents() {
		// Create a panel and add the button, status label and the SVG canvas.
		final JPanel panel = new JPanel(new BorderLayout());

		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));

		for (ExtractConfiguration conf : ExtractConfiguration.values()) {
			cardSets.addItem(conf);
		}

		p.add(cardSets);
		p.add(loadButton);
		p.add(extractButton);
		p.add(label);

		panel.add("North", p);
		panel.add("Center", svgCanvas);

		// Set the button action.
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				ExtractConfiguration conf = (ExtractConfiguration) cardSets
						.getSelectedItem();
				File f = new File(conf.location);
				try {
					svgCanvas.setURI(f.toURL().toString());
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});

		extractButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SaveAsSeparateCards saveAsSeparateCards = new SaveAsSeparateCards();
				try {
					saveAsSeparateCards
							.convertAllCards((ExtractConfiguration) cardSets
									.getSelectedItem());
				} catch (TranscoderException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		// Set the JSVGCanvas listeners.
		svgCanvas.addSVGDocumentLoaderListener(new SVGDocumentLoaderAdapter() {
			@Override
			public void documentLoadingStarted(SVGDocumentLoaderEvent e) {
				label.setText("Document Loading...");
			}

			@Override
			public void documentLoadingCompleted(SVGDocumentLoaderEvent e) {
				label.setText("Document Loaded.");
			}
		});

		svgCanvas.addGVTTreeBuilderListener(new GVTTreeBuilderAdapter() {
			@Override
			public void gvtBuildStarted(GVTTreeBuilderEvent e) {
				label.setText("Build Started...");
			}

			@Override
			public void gvtBuildCompleted(GVTTreeBuilderEvent e) {
				label.setText("Build Done.");
				pack();
			}
		});

		svgCanvas.addGVTTreeRendererListener(new GVTTreeRendererAdapter() {
			@Override
			public void gvtRenderingPrepare(GVTTreeRendererEvent e) {
				label.setText("Rendering Started...");
			}

			@Override
			public void gvtRenderingCompleted(GVTTreeRendererEvent e) {
				label.setText("");
			}
		});

		return panel;
	}
}
