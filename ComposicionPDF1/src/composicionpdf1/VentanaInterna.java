package composicionpdf1;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class VentanaInterna extends JInternalFrame {

    JTextArea texto;
    JScrollPane scroll;
    JButton guardar;
    private Document documento;
    private Etiquetas etiqueta;
    private final String etiquetas[];
    private String nombre;

    public VentanaInterna(String titulo, boolean tamaño, boolean cerrar, boolean maximizar, boolean minimizar, String nombre) {
        super(titulo, tamaño, cerrar, maximizar, minimizar);
        etiquetas = new String[]{"T", "P", "I", "C", "S", "b", "i", "u", "n"};
        setLayout(new BorderLayout());
        texto = new JTextArea();
        scroll = new JScrollPane(texto);
        add(scroll, BorderLayout.CENTER);
        EventosInternos eventosInternos = new EventosInternos();
        guardar = new JButton("Guardar");
        guardar.addActionListener(eventosInternos);
        add(guardar, BorderLayout.SOUTH);
        this.nombre = nombre;
    }

    public void tipoEtiqueta(int n, String cadena, boolean escribir) {

        switch (n) {
            case 0:
                etiqueta.t(cadena, documento);
                break;
            case 1:
                etiqueta.p(cadena, documento, escribir);
                break;
            case 2:
                etiqueta.etI(cadena, documento);
                break;
            case 3:
                etiqueta.c(cadena, documento);
                break;
            case 4:
                etiqueta.s(cadena, documento);
                break;
            case 5:
                etiqueta.b(cadena, documento);
                break;
            case 6:
                etiqueta.eti(cadena, documento);
                break;
            case 7:
                etiqueta.u(cadena, documento);
                break;
            case 8:
                etiqueta.n(documento);
                break;
            default:
                JOptionPane.showMessageDialog(null, "Esta etiqueta no esta disponible");
        }
    }

    public void buscar(String busqueda, String cadena, int limite) {
        for (int i = 0; i < limite; i++) {
            if (busqueda.equals(etiquetas[i])) {
                tipoEtiqueta(i, cadena, false);
                break;
            }
        }
    }

    public class EventosInternos implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == guardar) {
                try {
                    String textoVentana = texto.getText();
                    FileWriter ficheroTxt = new FileWriter(nombre);;
                    PrintWriter pw = new PrintWriter(ficheroTxt);
                    for (int i = 0; i < textoVentana.length(); i++) {
                        if (textoVentana.charAt(i) == '\n') {
                            pw.print("");
                        }
                        pw.print(textoVentana.charAt(i));
                    }
                    ficheroTxt.close();
                    nombre = nombre.substring(0, nombre.length() - 4);
                    nombre = nombre + ".PDF";
                    documento = new Document();
                    PdfWriter.getInstance(documento, new FileOutputStream(nombre));
                    documento.open();
                    documento.add(new Paragraph(" "));
                    etiqueta = new Etiquetas();
                    String cadena = "";
                    boolean falla = false;
                    boolean interna = false;
                    String cadenaInterna = "";
                    String busqueda = "";
                    String busquedaInterna = "";
                    int numCaracter = -1;
                    while (numCaracter < textoVentana.length() - 1) {
                        numCaracter++;
                        char caracter = textoVentana.charAt(numCaracter);
                        if (caracter != 123) {
                            if (interna) {
                                cadenaInterna = cadenaInterna + caracter;
                            } else {
                                cadena = cadena + caracter;
                            }
                        } else {
                            numCaracter++;
                            char c1 = textoVentana.charAt(numCaracter);
                            numCaracter++;
                            char c2 = textoVentana.charAt(numCaracter);
                            numCaracter++;
                            char c3 = textoVentana.charAt(numCaracter);
                            if (c1 == 35 && c3 == 125) {
                                if (c2 != 80 && c2 != 98 && c2 != 105 && c2 != 117) {
                                    if (busqueda.equals(c2 + "")) {
                                        buscar(busqueda, cadena, 5);
                                        cadena = "";
                                    } else {
                                        falla = true;
                                    }
                                } else {
                                    if (busquedaInterna.equals(c2 + "") && busqueda.equals("P")) {
                                        buscar(busquedaInterna, cadenaInterna, 9);
                                        cadenaInterna = "";
                                        interna = false;
                                    } else {
                                        if (c2 == 80) {
                                            tipoEtiqueta(1, cadena, true);
                                            cadena = "";
                                        } else {
                                            falla = true;
                                        }
                                    }
                                }
                            } else {
                                if (c2 == 125) {
                                    if (c1 != 98 && c1 != 105 && c1 != 117) {
                                        if (c1 == 110 && busqueda.equals("P")) {
                                            tipoEtiqueta(1, cadena, false);
                                            cadena = "";
                                            tipoEtiqueta(8, "", false);
                                        } else {
                                            busqueda = c1 + "";
                                        }
                                    } else {
                                        if (interna == false) {
                                            tipoEtiqueta(1, cadena, false);
                                            cadena = "";
                                        }
                                        interna = true;
                                        busquedaInterna = c1 + "";
                                    }
                                } else {
                                    cadena = cadena + c1 + c2 + c3;
                                }
                            }
                        }
                    }
                    if (falla) {
                        JOptionPane.showMessageDialog(null, "                                     Verifique sus etiquetas\n"
                                + "ya que puede haber algunas que no se esten utilizando correctamente");
                    }
                    documento.close();
                } catch (FileNotFoundException | DocumentException ex) {
                    JOptionPane.showMessageDialog(null, "Error al crear el archivo");
                } catch (IOException ex) {
                    Logger.getLogger(VentanaInterna.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
