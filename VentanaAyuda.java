package es.studium.Gestion;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class VentanaAyuda {

    public VentanaAyuda() {
        // Ruta del archivo HTML del manual de uso del programa
        String rutaArchivoHTML = "paginaayuda/manual.pdf";

        try {
            // Obtener la instancia del escritorio del sistema
            Desktop escritorio = Desktop.getDesktop();
            // Abrir el archivo HTML en el navegador web predeterminado del sistema
            escritorio.open(new File(rutaArchivoHTML));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}