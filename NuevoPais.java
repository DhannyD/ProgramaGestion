package es.studium.Gestion;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class NuevoPais implements WindowListener, ActionListener {
	Frame ventana = new Frame("Nuevo Pais");
	Dialog dlgMensaje = new Dialog(ventana, "Mensaje", true);

	Label lblTitulo = new Label("------- Alta de Pais -------");
	Label lblNombre = new Label("Nombre:");
	Label lblISO = new Label("ISO:       ");
	Label lblMensaje = new Label("Alta de Pais Correcta");

	TextField txtNombre = new TextField(10);
	TextField txtISO = new TextField(10);

	Button btnAceptar = new Button("Aceptar");
	Button btnCancelar = new Button("Cancelar");

	Conexion conexion = new Conexion();

	NuevoPais() {
		ventana.setLayout(new FlowLayout());
		ventana.setSize(200, 220);
		ventana.addWindowListener(this);

		ventana.add(lblTitulo);
		ventana.add(lblNombre);
		ventana.add(txtNombre);
		ventana.add(lblISO);
		ventana.add(txtISO);

		btnAceptar.addActionListener(this);
		btnCancelar.addActionListener(this);

		ventana.add(btnAceptar);
		ventana.add(btnCancelar);

		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		if (dlgMensaje.isActive()) {
			dlgMensaje.setVisible(false);
		} else {
			ventana.setVisible(false);
		}
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
		// Nuevo Usuario
		if (e.getSource().equals(btnAceptar)) {
			dlgMensaje.setLayout(new FlowLayout());
			dlgMensaje.setSize(220, 100);
			dlgMensaje.addWindowListener(this);

			if (txtNombre.getText().length() == 0 || txtNombre.getText().length() == 0
					|| txtISO.getText().length() == 0) {
				lblMensaje.setText("Los campos estan vacios");
			}
			// Comprobar las claves
			else {
				// Dar de alta
				String sentencia = "INSERT INTO paises VALUES (null, '" + txtISO.getText() + "','" + txtNombre.getText()
						+ "');";
				int respuesta = conexion.NuevoPais(sentencia);
				if (respuesta != 0) {
					// Mostrar Mensaje Error
					lblMensaje.setText("Error en Alta");
				} else {
					lblMensaje.setText("Nuevo pais creado correctamente");
				}
			}

			dlgMensaje.add(lblMensaje);
			dlgMensaje.setResizable(false);
			dlgMensaje.setLocationRelativeTo(null);
			dlgMensaje.setVisible(true);
		} else if (e.getSource().equals(btnCancelar)) {
			txtNombre.setText("");
			txtISO.setText("");
			txtNombre.requestFocus();
		}
	}
}
