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

import es.studium.Gestion.Conexion.Sesion;

public class Login implements WindowListener, ActionListener {
	Frame ventanaLogin = new Frame("Login");
	Dialog dlgMensaje = new Dialog(ventanaLogin, "Error", true);

	Label lblUsuario = new Label("Usuario:");
	Label lblClave = new Label("Clave:    ");
	Label lblCredIncorrectas = new Label("Credenciales Incorrectas");

	TextField txtUsuario = new TextField(10);
	TextField txtClave = new TextField(10);

	Button btnAcceder = new Button("Acceder");
	Button btnCancelar = new Button("Cancelar");

	Conexion conexion = new Conexion();

	Login() {
		ventanaLogin.setLayout(new FlowLayout());
		ventanaLogin.addWindowListener(this);

		ventanaLogin.add(lblUsuario);
		ventanaLogin.add(txtUsuario);
		ventanaLogin.add(lblClave);
		txtClave.setEchoChar('●');
		ventanaLogin.add(txtClave);
		btnAcceder.addActionListener(this);
		ventanaLogin.add(btnAcceder);
		btnCancelar.addActionListener(this);
		ventanaLogin.add(btnCancelar);

		ventanaLogin.setSize(220, 135);
		ventanaLogin.setResizable(false);
		ventanaLogin.setLocationRelativeTo(null);
		ventanaLogin.setVisible(true);
	}

	public static void main(String[] args) {
		new Login();
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		if (dlgMensaje.isActive()) {
			dlgMensaje.setVisible(false);
		} else {
			String usuario = "";
			String sentencia = "Salida del sistema";
			Conexion.registrarMovimiento(usuario, sentencia);
			System.exit(0);
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
		if (e.getSource().equals(btnAcceder)) {
			String usuario = txtUsuario.getText();
			String clave = txtClave.getText();
			// Si las credenciales son correctas
			if (conexion.comprobarCredenciales(usuario, clave) == 1) {
				// Mostrar el Men Principal
				Sesion.setNombreUsuario(usuario);
				new MenuPrincipal();
				ventanaLogin.setVisible(false);
			} else if (conexion.comprobarCredenciales(usuario, clave) == 0) {
				// Mostrar el Men Principal
				Sesion.setNombreUsuario(usuario);
				new MenuPrincipalBasico();
				ventanaLogin.setVisible(false);
			}
			// Si las credenciales son incorrectas
			else {
				dlgMensaje.setLayout(new FlowLayout());
				dlgMensaje.addWindowListener(this);
				dlgMensaje.setSize(220, 75);
				dlgMensaje.add(lblCredIncorrectas);
				dlgMensaje.setResizable(false);
				dlgMensaje.setLocationRelativeTo(null);
				dlgMensaje.setVisible(true);
			}
		}
		if (e.getSource().equals(btnCancelar)) {
			txtUsuario.setText("");
			txtClave.setText("");
			txtUsuario.requestFocus();
		}
	}
}