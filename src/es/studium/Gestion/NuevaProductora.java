package es.studium.Gestion;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class NuevaProductora implements WindowListener, ActionListener {
	Frame ventana = new Frame("Nueva Productora");
	Dialog dlgMensaje = new Dialog(ventana, "Mensaje", true);
	Choice choPaises = new Choice();

	Label lblTitulo = new Label("------- Alta de Productora -------");
	Label lblNombre = new Label("Nombre:");
	Label lblMensaje = new Label("");

	TextField txtNombre = new TextField(10);
	TextField txtPais = new TextField(10);

	Button btnAceptar = new Button("Aceptar");
	Button btnCancelar = new Button("Cancelar");

	Conexion conexion = new Conexion();

	NuevaProductora() {
		ventana.setLayout(new FlowLayout());
		ventana.setSize(320, 180);
		ventana.addWindowListener(this);
		conexion.rellenarChoicePaises(choPaises);

		Panel panelNombre = new Panel(new GridLayout(1, 2)); // GridLayout con 1 fila y 2 columnas
		panelNombre.add(lblNombre); // Añade la etiqueta "Nombre" al panel
		panelNombre.add(txtNombre); // Añade el campo de texto al panel

		ventana.add(lblTitulo);
		ventana.add(panelNombre); // Añade el panel que contiene la etiqueta y el campo de texto
		ventana.add(choPaises);

		btnAceptar.addActionListener(this);
		btnCancelar.addActionListener(this);

		ventana.add(btnAceptar);
		ventana.add(btnCancelar);

		ventana.setResizable(true);
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
		if (e.getSource().equals(btnAceptar)) {
			dlgMensaje.setLayout(new FlowLayout());
			dlgMensaje.setSize(300, 100);
			dlgMensaje.addWindowListener(this);

			if (txtNombre.getText().length() == 0 || choPaises.getSelectedIndex() == -1) {
				lblMensaje.setText("Los campos están vacíos");
			} else {
				// Obtener el nombre de la productora y el nombre del país seleccionado

				String sentencia = "INSERT INTO productoras VALUES (null, '" + txtNombre.getText() + "', "
						+ (choPaises.getSelectedIndex()) + ")";

				// Llamar al método NuevaProductora pasando el nombre de la productora y el
				// nombre del país
				int respuesta = conexion.NuevaProductora(sentencia);
				if (respuesta != 0) {
					lblMensaje.setText("Error en el alta de productora");
				} else {
					lblMensaje.setText("Nueva productora creada correctamente");
				}
			}

			dlgMensaje.add(lblMensaje);
			dlgMensaje.setResizable(false);
			dlgMensaje.setLocationRelativeTo(null);
			dlgMensaje.setVisible(true);
		} else if (e.getSource().equals(btnCancelar)) {
			txtNombre.setText("");
			choPaises.select(0); // Restablece la selección del país al primero de la lista
			txtNombre.requestFocus();
		}
	}

}
