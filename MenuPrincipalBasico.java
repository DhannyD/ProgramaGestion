package es.studium.Gestion;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MenuPrincipalBasico implements WindowListener, ActionListener {
    Frame ventana = new Frame("Menu Principal");

    MenuBar menuBar = new MenuBar();

    Menu mnuPais = new Menu("Paises");
    Menu mnuProductoras = new Menu("Productoras");
    Menu mnuPeliculas = new Menu("Peliculas");
    Menu mnuAyuda = new Menu("Ayuda"); // Nuevo menú de Ayuda

    MenuItem mniNuevoPais = new MenuItem("Nuevo Pais");
    MenuItem mniNuevaProductora = new MenuItem("Nueva Productora");
    MenuItem mniNuevaPelicula = new MenuItem("Nueva Pelicula");
    MenuItem mniVerAyuda = new MenuItem("Ver Ayuda"); // Nueva opción de Ayuda

    MenuPrincipalBasico() {
        ventana.setLayout(new FlowLayout());
        ventana.addWindowListener(this);

        mniNuevoPais.addActionListener(this);
        mniNuevaProductora.addActionListener(this);
        mniNuevaPelicula.addActionListener(this);
        mniVerAyuda.addActionListener(this); // Agregar ActionListener para la opción de ayuda

        mnuPais.add(mniNuevoPais);
        mnuProductoras.add(mniNuevaProductora);
        mnuPeliculas.add(mniNuevaPelicula);
        mnuAyuda.add(mniVerAyuda); // Agregar la opción de ayuda al menú de ayuda

        menuBar.add(mnuPais);
        menuBar.add(mnuProductoras);
        menuBar.add(mnuPeliculas);
        menuBar.add(mnuAyuda); // Agregar el menú de ayuda a la barra de menú

        ventana.setMenuBar(menuBar);

        ventana.setSize(400, 400);
        ventana.setResizable(false);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        String usuario = Conexion.Sesion.getNombreUsuario();
        String sentencia = "Salida del sistema";
        Conexion.registrarMovimiento(usuario, sentencia);
        System.exit(0);
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

        if (e.getSource().equals(mniNuevoPais)) {
            new NuevoPais();
        }

        if (e.getSource().equals(mniNuevaProductora)) {
            new NuevaProductora();
        }

        if (e.getSource().equals(mniNuevaPelicula)) {
            new NuevaPelicula();
        }

        if (e.getSource().equals(mniVerAyuda)) { // Si se selecciona la opción de ayuda
            // Abre la ventana de ayuda
        	new VentanaAyuda();
        }
    }
}
