package es.studium.Gestion;

import java.awt.Button;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

public class ListadoPaises implements WindowListener, ActionListener {
    Frame ventana = new Frame("Listado de Paises");
    TextArea txtListado = new TextArea(10, 40);
    Button btnPdf = new Button("PDF");
    Conexion conexion = new Conexion();

    ListadoPaises() {
        ventana.setLayout(new FlowLayout());
        ventana.setSize(340, 260);
        ventana.addWindowListener(this);

        txtListado.append("tId\tISO\t\tNombre\n");
        // Sacar de la tabla usuarios todos los registros
        txtListado.append(conexion.obtenerPaises());
        ventana.add(txtListado);

        btnPdf.addActionListener(this);

        ventana.add(btnPdf);

        ventana.setResizable(false);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        ventana.setVisible(false);
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnPdf) {
            generarPdf(txtListado.getText());
        }
    }

    private void generarPdf(String contenido) {
        try {
            // Genera el PDF como lo hiciste antes
            PdfWriter writer = new PdfWriter(new FileOutputStream("ListadoPaises.pdf"));
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);
            document.add(new Paragraph(contenido));
            document.close();
            System.out.println("PDF creado exitosamente.");
            
            // Abre el PDF en el navegador
            File file = new File("ListadoPaises.pdf");
            Desktop.getDesktop().open(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
