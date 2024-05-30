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

public class MenuPrincipal implements WindowListener, ActionListener {
	Frame ventana = new Frame("Menu Principal");

	MenuBar menuBar = new MenuBar();

	Menu mnuPais = new Menu("Paises");
	Menu mnuProductoras = new Menu("Productoras");
	Menu mnuPeliculas = new Menu("Peliculas");
	Menu mnuAyuda = new Menu("Ayuda"); // Nuevo menú de Ayuda

	MenuItem mniListadodePaises = new MenuItem("Lista de países");
	MenuItem mniNuevoPais = new MenuItem("Nuevo Pais");
	MenuItem mniModificarPais = new MenuItem("Modificar Pais");
	MenuItem mniBajaPais = new MenuItem("Eliminar Pais");

	MenuItem mniNuevaProductora = new MenuItem("Nueva Productora");
	MenuItem mniBajaProductora = new MenuItem("Baja Productora");
	MenuItem mniListadoProductora = new MenuItem("Listado Productoras");
	MenuItem mniModificarProductora = new MenuItem("Modificar Productora");

	MenuItem mniNuevaPelicula = new MenuItem("Nueva Pelicula");
	MenuItem mniBajaPelicula = new MenuItem("Baja Pelicula");
	MenuItem mniListadoPelicula = new MenuItem("Listado Peliculas");
	MenuItem mniModificarPelicula = new MenuItem("Modificar Pelicula");

	MenuItem mniVerAyuda = new MenuItem("Ver Ayuda"); // Nuevo elemento de menú para ver la ayuda

	MenuPrincipal() {
		ventana.setLayout(new FlowLayout());
		ventana.addWindowListener(this);

		mniListadodePaises.addActionListener(this);
		mniNuevoPais.addActionListener(this);
		mniModificarPais.addActionListener(this);
		mniBajaPais.addActionListener(this);

		mniNuevaProductora.addActionListener(this);
		mniBajaProductora.addActionListener(this);
		mniListadoProductora.addActionListener(this);
		mniModificarProductora.addActionListener(this);

		mniNuevaPelicula.addActionListener(this);
		mniBajaPelicula.addActionListener(this);
		mniListadoPelicula.addActionListener(this);
		mniModificarPelicula.addActionListener(this);

		mniVerAyuda.addActionListener(this); // Agregar ActionListener para la opción de Ayuda

		mnuPais.add(mniListadodePaises);
		mnuPais.add(mniNuevoPais);
		mnuPais.add(mniModificarPais);
		mnuPais.add(mniBajaPais);

		mnuProductoras.add(mniNuevaProductora);
		mnuProductoras.add(mniBajaProductora);
		mnuProductoras.add(mniListadoProductora);
		mnuProductoras.add(mniModificarProductora);

		mnuPeliculas.add(mniNuevaPelicula);
		mnuPeliculas.add(mniBajaPelicula);
		mnuPeliculas.add(mniListadoPelicula);
		mnuPeliculas.add(mniModificarPelicula);

		mnuAyuda.add(mniVerAyuda); // Agregar la opción de ayuda al menú de Ayuda

		menuBar.add(mnuPais);
		menuBar.add(mnuProductoras);
		menuBar.add(mnuPeliculas);
		menuBar.add(mnuAyuda); // Agregar el menú de Ayuda a la barra de menú

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

		if (e.getSource().equals(mniListadodePaises)) {
			new ListadoPaises();
		}
		if (e.getSource().equals(mniNuevoPais)) {
			new NuevoPais();
		}
		if (e.getSource().equals(mniBajaPais)) {
			new BajaPais();
		}
		if (e.getSource().equals(mniModificarPais)) {
			new ModificarPais();
		}
		if (e.getSource().equals(mniListadoProductora)) {
			new ListadoProductora();
		}
		if (e.getSource().equals(mniNuevaProductora)) {
			new NuevaProductora();
		}
		if (e.getSource().equals(mniBajaProductora)) {
			new BajaProductora();
		}
		if (e.getSource().equals(mniModificarProductora)) {
			new ModificarProductora();
		}
		if (e.getSource().equals(mniNuevaPelicula)) {
			new NuevaPelicula();
		}
		if (e.getSource().equals(mniListadoPelicula)) {
			new ListadoPelicula();
		}
		if (e.getSource().equals(mniBajaPelicula)) {
			new BajaPelicula();
		}
		if (e.getSource().equals(mniModificarPelicula)) {
			new ModificarPelicula();
		}
		if (e.getSource().equals(mniVerAyuda)) {
			new VentanaAyuda();
		}
	}
}
