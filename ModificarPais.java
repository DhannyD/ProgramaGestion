package es.studium.Gestion;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ModificarPais implements WindowListener, ActionListener {
	Frame ventana = new Frame("Edicion Pais");
	Dialog dlgEdicion = new Dialog(ventana, "Editar Pais", true);
	Dialog dlgMensaje = new Dialog(ventana, "Mensaje", true);

	Label lblElegir = new Label("Elegir el pais a editar:");

	Choice choPaises = new Choice();

	Button btnEditar = new Button("Editar");
	Button btnCancelar = new Button("Cancelar");

	Label lblTitulo = new Label("------- Editar Pais -------");
	Label lblNombre = new Label("Nombre:");
	Label lblISO = new Label("ISO:");
	Label lblMensaje = new Label("Edicion de Pais Correcta");

	TextField txtNombre = new TextField(10);
	TextField txtISO = new TextField(10);

	Button btnModificar = new Button("Modificar");
	Button btnVolver = new Button("Volver");

	Conexion conexion = new Conexion();
	String idPais = "";

	ModificarPais() {
		ventana.setLayout(new FlowLayout());
		ventana.setSize(320, 150);
		ventana.addWindowListener(this);

		ventana.add(lblElegir);
		// Rellenar el Choice con los elementos de la tabla usuarios
		conexion.rellenarChoicePaises(choPaises);
		ventana.add(choPaises);
		btnEditar.addActionListener(this);
		ventana.add(btnEditar);
		btnCancelar.addActionListener(this);
		ventana.add(btnCancelar);

		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);

		dlgMensaje.setLayout(new FlowLayout());
		dlgMensaje.setSize(250, 100);
		dlgMensaje.addWindowListener(this);
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		if (dlgEdicion.isActive()) {
			dlgEdicion.setVisible(false);
			dlgMensaje.setVisible(false);
		} else if (dlgMensaje.isActive()) {
			dlgMensaje.setVisible(false);
			// Rellenar el Choice con los elementos de la tabla usuarios
			conexion.rellenarChoicePaises(choPaises);
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
		if (e.getSource().equals(btnCancelar)) {
			ventana.setVisible(false);
		} else if (e.getSource().equals(btnEditar)) {
			// Si NO est� seleccionada la primera opcion
			if (choPaises.getSelectedIndex() != 0) {
				String tabla[] = choPaises.getSelectedItem().split("-");
				idPais = tabla[0];
				String datosObtenidos = conexion.editarPais(idPais);
				
				String[] tablaDatos = datosObtenidos.split("-");
				dlgEdicion.setLayout(new FlowLayout());
				dlgEdicion.setSize(220, 220);
				dlgEdicion.addWindowListener(this);

				dlgEdicion.add(lblTitulo);
				dlgEdicion.add(lblNombre);
				txtNombre.setText(tablaDatos[0]);
				dlgEdicion.add(txtNombre);
				txtNombre.setText("");
				dlgEdicion.add(lblISO);
				txtISO.setText("");
				dlgEdicion.add(txtISO);

				btnModificar.addActionListener(this);
				btnVolver.addActionListener(this);

				dlgEdicion.add(btnModificar);
				dlgEdicion.add(btnVolver);

				dlgEdicion.setResizable(false);
				dlgEdicion.setLocationRelativeTo(null);
				dlgEdicion.setVisible(true);
			}
		} else if (e.getSource().equals(btnModificar)) {
			if (txtNombre.getText().length() == 0 || txtISO.getText().length() == 0) {
				lblMensaje.setText("Los campos estan vacios");
			} else {

				String sentencia = "UPDATE paises SET nombrePais = '" + txtNombre.getText() + "', isoPais = '"
						+ txtISO.getText() + "' WHERE idPais = " + idPais + ";";
				int respuesta = conexion.modificarPais(sentencia);
				if (respuesta != 0) {
					// Mostrar Mensaje Error
					lblMensaje.setText("Error en Modificacion");
				} else {
					lblMensaje.setText("Modificacion de Pais Correcta");
				}
			}
			dlgMensaje.add(lblMensaje);
			dlgMensaje.setResizable(false);
			dlgMensaje.setLocationRelativeTo(null);
			dlgMensaje.setVisible(true);
		} else if (e.getSource().equals(btnVolver)) {
			dlgEdicion.setVisible(false);
		}
	}
}
