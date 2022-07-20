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

    public static void main(String[] args) throws IOException {
        InputStream input = Files.newInputStream(Paths.get("/home/lahiru/IdeaProjects/PDFTextEditor/src/main/resources/config.properties"));
        Properties properties = new Properties();

        // load a properties file
        properties.load(input);
        //read the name list
        try (BufferedReader br = new BufferedReader(new FileReader((String) properties.get("NameListPath")))) {
            String line;
            while ((line = br.readLine()) != null) {

                //Loading an existing document
                PDDocument doc = PDDocument.load(new File((String) properties.get("PDFPath")));
                //Creating a PDF Document
                PDPage page = doc.getPage(0);
                PDPageContentStream contentStream = new PDPageContentStream(doc, page, APPEND, true);
                //Begin the Content stream
                contentStream.beginText();

                //Setting the font to the Content stream
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                contentStream.newLineAtOffset(Float.parseFloat((String) properties.get("Xtranslation")), Float.parseFloat((String) properties.get("Ytranslation")));

                //Adding text in the form of string
                contentStream.showText(line);

                //Ending the content stream
                contentStream.endText();
                System.out.println("Content added");

                //Closing the content stream
                contentStream.close();

                //Saving the document
                doc.save(new File((String) properties.get("OutputDirectory")) + line + ".pdf");
                doc.close();
            }
            //Closing the document

        }


    }
}

