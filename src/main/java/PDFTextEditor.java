import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import static org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode.APPEND;


public class PDFTextEditor {
    /**
     * The path of the text file that need to add to PDF file
     */
    private static final String TEXT_LIST_PROPERTY = "NameListPath";
    /**
     * PDF file path
     */
    private static final String PDF_PATH_PROPERTY = "PDFPath";
    /**
     * X coordinate for the adding text
     */
    private static final String X_COORDINATE = "Xtranslation";
    /**
     * Y coordinate for the adding text
     */
    private static final String Y_COORDINATE = "Ytranslation";

    /**
     * Output directory path
     */
    private static final String OUTPUT_DIRECTORY = "OutputDirectory";


    public static void main(String[] args) throws IOException {

        // load the properties
        InputStream input = Files.newInputStream(Paths.get("src/main/resources/config.properties"));
        Properties properties = new Properties();
        properties.load(input);

        //read the text list
        try (BufferedReader br = new BufferedReader(new FileReader((String) properties.get(TEXT_LIST_PROPERTY)))) {
            String line;
            while ((line = br.readLine()) != null) {

                //Loading an existing document
                PDDocument doc = PDDocument.load(new File((String) properties.get(PDF_PATH_PROPERTY)));
                //Creating a PDF Document
                PDPage page = doc.getPage(0);
                PDPageContentStream contentStream = new PDPageContentStream(doc, page, APPEND, true);
                //Begin the Content stream
                contentStream.beginText();

                //Setting the font to the Content stream
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                contentStream.newLineAtOffset(Float.parseFloat((String) properties.get(X_COORDINATE)), Float.parseFloat((String) properties.get(Y_COORDINATE)));

                //Adding text in the form of string
                contentStream.showText(line);

                //Ending the content stream
                contentStream.endText();

                //Closing the content stream
                contentStream.close();

                //Saving the document
                doc.save(new File((String) properties.get(OUTPUT_DIRECTORY)) + line + ".pdf");
                doc.close();
            }

        }


    }
}

