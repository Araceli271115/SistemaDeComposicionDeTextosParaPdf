package composicionpdf1;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import javax.swing.JOptionPane;

public class Etiquetas {

    Font formato = new Font();
    Paragraph parrafo = new Paragraph();

    public void documento(Object a, Document documento) {
        try {
            documento.add((Element) a);
            parrafo.clear();
        } catch (DocumentException ex) {
            JOptionPane.showMessageDialog(null, "Error en el sistema");
        }
    }

    public void t(String cadena, Document documento) {
        Paragraph titulo = new Paragraph();
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        titulo.setFont(FontFactory.getFont("Times New Roman", 24, Font.BOLD, BaseColor.BLACK));
        titulo.add(cadena);
        documento(titulo, documento);
    }

    public void p(String cadena, Document documento, boolean escribir) {
        formato.setStyle(Font.UNDEFINED);
        parrafo.setFont(new Font(formato));
        parrafo.add(cadena);
        if (escribir) {
            documento(parrafo, documento);
        }
    }

    public void etI(String cadena, Document documento) {
        try {
            cadena = cadena.trim();
            Image imagen = Image.getInstance(cadena);
            imagen.setAlignment(Element.ALIGN_CENTER);
            documento(imagen, documento);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se encontro la imagen");
        }
    }

    public void c(String cadena, Document documento) {
        Paragraph capitulo = new Paragraph();
        capitulo.setAlignment(Paragraph.ALIGN_LEFT);
        capitulo.setFont(FontFactory.getFont("Tahoma", 20, Font.BOLD, BaseColor.BLACK));
        capitulo.add(cadena);
        documento(capitulo, documento);
    }

    public void s(String cadena, Document documento) {
        Paragraph tituloCapitulo = new Paragraph();
        tituloCapitulo.setAlignment(Paragraph.ALIGN_LEFT);
        tituloCapitulo.setFont(FontFactory.getFont("Arial", 16, Font.NORMAL, BaseColor.BLACK));
        tituloCapitulo.add(cadena);
        documento(tituloCapitulo, documento);
    }

    public void b(String cadena, Document documento) {
        formato.setStyle(Font.BOLD);
        parrafo.setFont(new Font(formato));
        parrafo.add(cadena);
    }

    public void eti(String cadena, Document documento) {
        formato.setStyle(Font.ITALIC);
        parrafo.setFont(new Font(formato));
        parrafo.add(cadena);
    }

    public void u(String cadena, Document documento) {
        formato.setStyle(Font.UNDERLINE);
        parrafo.setFont(new Font(formato));
        parrafo.add(cadena);
    }

    public void n(Document documento) {
        p("\n", documento, false);
    }
}
