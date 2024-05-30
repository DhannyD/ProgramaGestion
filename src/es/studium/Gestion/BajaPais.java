package es.studium.Gestion;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class BajaPais implements WindowListener, ActionListener {
	Frame ventana = new Frame("Baja Pais");
	Dialog dlgConfirmacion = new Dialog(ventana, "Segur@?", true);
	Dialog dlgMensaje = new Dialog(ventana, "Mensaje", true);

	Label lblElegir = new Label("Elegir el pais a borrar:");
	Label lblConfirmar = new Label("Seguro de borrar el pais ");
	Label lblMensaje = new Label("Eliminacion Correcta");

	Choice choPaises = new Choice();

	Button btnEliminar = new Button("Eliminar");
	Button btnCancelar = new Button("Cancelar");
	Button btnSi = new Button("Si");
	Button btnNo = new Button("No");

	Conexion conexion = new Conexion();

	BajaPais() {
		ventana.setLayout(new FlowLayout());
		ventana.setSize(350, 150);
		ventana.addWindowListener(this);

		ventana.add(lblElegir);
		// Rellenar el Choice con los elementos de la tabla paisess
		conexion.rellenarChoicePaises(choPaises);
		ventana.add(choPaises);
		btnEliminar.addActionListener(this);
		ventana.add(btnEliminar);
		btnCancelar.addActionListener(this);
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
		if (dlgConfirmacion.isActive()) {
			dlgConfirmacion.setVisible(false);
		} else if (dlgMensaje.isActive()) {
			dlgConfirmacion.setVisible(false);
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
		} else if (e.getSource().equals(btnEliminar)) {
			// Si NO est� seleccionada la primera opci�n
			if (choPaises.getSelectedIndex() != 0) {
				dlgConfirmacion.setLayout(new FlowLayout());
				dlgConfirmacion.setSize(270, 100);
				dlgConfirmacion.addWindowListener(this);

				lblConfirmar.setText("Seguro de borrar el pais '" + choPaises.getSelectedItem() + "'?");
				dlgConfirmacion.add(lblConfirmar);
				btnSi.addActionListener(this);
				dlgConfirmacion.add(btnSi);
				btnNo.addActionListener(this);
				dlgConfirmacion.add(btnNo);

				dlgConfirmacion.setResizable(false);
				dlgConfirmacion.setLocationRelativeTo(null);
				dlgConfirmacion.setVisible(true);
			}
		} else if (e.getSource().equals(btnNo)) {
			dlgConfirmacion.setVisible(false);
		} else if (e.getSource().equals(btnSi)) {
			String tabla[] = choPaises.getSelectedItem().split("-");
			int resultado = conexion.eliminarPais(tabla[0]); // idUsuario --> DELETE

			dlgMensaje.setLayout(new FlowLayout());
			dlgMensaje.setSize(250, 100);
			dlgMensaje.addWindowListener(this);

			// Baja correcta
			if (resultado == 0) {
				// Mostramos el Di�logo con el OK
				lblMensaje.setText("Eliminacion Correcta");
			}
			// Error en Baja
			else {
				// Mostramos el Di�logo con el ERROR
				lblMensaje.setText("ERROR en Eliminacion");
			}

			dlgMensaje.add(lblMensaje);
			dlgMensaje.setResizable(false);
			dlgMensaje.setLocationRelativeTo(null);
			dlgMensaje.setVisible(true);
		}
	}
}
