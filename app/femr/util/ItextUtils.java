package femr.util;

import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;

public class ItextUtils {

    public static Paragraph centeredParagraph(String text, Font font) {
        Paragraph paragraph = new Paragraph(text, font);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        return paragraph;
    }

    public static Paragraph withSpacingBefore(Paragraph p, int spacing) {
        p.setSpacingBefore(spacing);
        return p;
    }

}
