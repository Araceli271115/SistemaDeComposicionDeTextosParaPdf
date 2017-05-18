package composicionpdf1;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Interfaz extends JFrame {

    private final JDesktopPane escritorio;
    private final JMenuBar menu;
    private final JMenu crearVentana;
    private final JMenuItem abrirPdf;
    private final JMenuItem editarTxt;
    private int contPdf = 0;
    private int contTxt = 0;
    private int posicion = 10;

    public Interfaz() {
        escritorio = new JDesktopPane();
        add(escritorio);
        Eventos eventos = new Eventos();
        abrirPdf = new JMenuItem("ABRIR PDF");
        abrirPdf.setBackground(Color.DARK_GRAY);
        abrirPdf.setForeground(Color.WHITE);
        abrirPdf.addActionListener(eventos);
        editarTxt = new JMenuItem("EDITAR TXT");
        editarTxt.setBackground(Color.DARK_GRAY);
        editarTxt.setForeground(Color.WHITE);
        editarTxt.addActionListener(eventos);
        crearVentana = new JMenu("MENU");
        crearVentana.setBackground(Color.DARK_GRAY);
        crearVentana.setForeground(Color.WHITE);
        crearVentana.add(abrirPdf);
        crearVentana.add(editarTxt);
        menu = new JMenuBar();
        menu.setBackground(Color.DARK_GRAY);
        setJMenuBar(menu);
        menu.add(crearVentana);

    }

    public class Eventos implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == abrirPdf) {
                JFileChooser abrir = new JFileChooser();
                abrir.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                FileNameExtensionFilter filtroPdf = new FileNameExtensionFilter("*,PDF", "pdf");
                abrir.setFileFilter(filtroPdf);
                int seleccionado = abrir.showOpenDialog(menu);
                if (seleccionado == JFileChooser.APPROVE_OPTION && filtroPdf.getDescription().equals("*,PDF")) {
                    String fichero = abrir.getSelectedFile().getPath();

                    try {
                        Desktop.getDesktop().open(new File(fichero));
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "No se puede abrir este archivo");
                    }

                } else {
                    if (seleccionado == JFileChooser.CANCEL_OPTION) {
                        JOptionPane.showMessageDialog(null, "No se seleccionaste nada");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se puede abrir este archivo");
                    }
                }
            } else {
                if (e.getSource() == editarTxt) {
                    JFileChooser dig = new JFileChooser();
                    dig.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                    FileNameExtensionFilter filtro = new FileNameExtensionFilter("*,TXT", "txt");
                    dig.setFileFilter(filtro);
                    int opcion = dig.showOpenDialog(menu);
                    if (opcion == JFileChooser.APPROVE_OPTION && filtro.getDescription().equals("*,TXT")) {
                        File fichero = dig.getSelectedFile();
                        contTxt++;
                        String nombre = dig.getSelectedFile().getPath();
                        VentanaInterna ventanaInterna = new VentanaInterna("Txt: " + contTxt, true, true, true, true, nombre);
                        ventanaInterna.setBounds(0 + posicion, 0 + posicion, 200, 200);
                        posicion += 10;
                        escritorio.add(ventanaInterna);
                        ventanaInterna.setVisible(true);
                        try (FileReader fr = new FileReader(fichero)) {
                            String cadena = "";
                            int valor = fr.read();
                            while (valor != -1) {
                                cadena = cadena + (char) valor;
                                valor = fr.read();
                            }
                            ventanaInterna.texto.setText(cadena);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        if (opcion == JFileChooser.CANCEL_OPTION) {
                            JOptionPane.showMessageDialog(null, "No seleccionaste nada");
                        } else {
                            JOptionPane.showMessageDialog(null, "Este tipo de archivo no puede ser abierto");
                        }
                    }
                }
            }
        }

    }
}
