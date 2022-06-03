package org.reftch;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.reftch.parser.XmlHandler;
import org.reftch.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 *  Parser for the XML
 *  Author: reftch
 */
public class Parser {

    private static final Logger LOG = LoggerFactory.getLogger(Parser.class);

    private static final String PATTERN = ".*\\.xml";
    private static final String DIR = "xml";
    private static final String OUTPUT_DIR = "target/markdown/";

    public static void main(String[] args) {
        Instant start = Instant.now();

        try {
            // make output directory
            var output = new File(OUTPUT_DIR);
            output.mkdir();

            var files = new HashSet<File>();
            Utils.traverseDirectory(PATTERN, DIR, files);

            for (var child : files) {
                var input = child.getName();
                LOG.info("Processing file: {}", input);

                var inputFile = new File(child.getAbsolutePath());
                var factory = SAXParserFactory.newInstance();
                var saxParser = factory.newSAXParser();
                var xmlHandler = new XmlHandler(false);
                saxParser.parse(inputFile, xmlHandler);

                // save to file
                Utils.saveFile(OUTPUT_DIR + input, xmlHandler.getMarkdown());
            }
        } catch (IOException | SAXException | ParserConfigurationException e) {
            LOG.error(e.getMessage());
        }

        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        LOG.info("Time elapsed: {} ms", timeElapsed.toMillis());
    }

}
