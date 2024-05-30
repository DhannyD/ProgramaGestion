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

public class NuevaPelicula implements WindowListener, ActionListener {
    Frame ventana = new Frame("Nueva Pelicula");
    Dialog dlgMensaje = new Dialog(ventana, "Mensaje", true);
    Choice choProductora = new Choice();
    Choice choPais = new Choice(); // Choice para seleccionar el país

    Label lblTitulo = new Label("------- Alta de Pelicula -------");
    Label lblNombre = new Label("Nombre:");
    Label lblMensaje = new Label("");

    TextField txtNombre = new TextField(10);

    Button btnAceptar = new Button("Aceptar");
    Button btnCancelar = new Button("Cancelar");

    Conexion conexion = new Conexion();

    NuevaPelicula() {
        ventana.setLayout(new FlowLayout());
        ventana.setSize(320, 240);
        ventana.addWindowListener(this);
        conexion.rellenarChoiceProductora(choProductora);
        conexion.rellenarChoicePaises(choPais); // Rellenar el Choice de países

        Panel panelNombre = new Panel(new GridLayout(1, 2));
        panelNombre.add(lblNombre);
        panelNombre.add(txtNombre);

        ventana.add(lblTitulo);
        ventana.add(panelNombre);

        ventana.add(choProductora);
        ventana.add(choPais); // Agregar el Choice de países

        btnAceptar.addActionListener(this);
        btnCancelar.addActionListener(this);

        ventana.add(btnAceptar);
        ventana.add(btnCancelar);

        ventana.setResizable(true);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {
        if (dlgMensaje.isActive()) {
            dlgMensaje.setVisible(false);
        } else {
            ventana.setVisible(false);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnAceptar)) {
            dlgMensaje.setLayout(new FlowLayout());
            dlgMensaje.setSize(300, 100);
            dlgMensaje.addWindowListener(this);

            if (txtNombre.getText().length() == 0 || choProductora.getSelectedIndex() == 0 || choPais.getSelectedIndex() == 0) {
                lblMensaje.setText("Los campos están vacíos");
            } else {
                String idProductora = choProductora.getSelectedItem().split("-")[0];
                String idPais = choPais.getSelectedItem().split("-")[0]; // Obtener el ID del país seleccionado
                String sentencia = "INSERT INTO peliculas (nombrePelicula, idProductoraFK, idPaisFK) VALUES ('" + txtNombre.getText() + "', " + idProductora + ", " + idPais + ");";
                int respuesta = conexion.NuevaPelicula(sentencia);
                if (respuesta != 0) {
                    lblMensaje.setText("Error en el alta de Pelicula");
                } else {
                    lblMensaje.setText("Nueva Pelicula creada correctamente");
                }
            }

            dlgMensaje.add(lblMensaje);
            dlgMensaje.setResizable(false);
            dlgMensaje.setLocationRelativeTo(null);
            dlgMensaje.setVisible(true);
        } else if (e.getSource().equals(btnCancelar)) {
            txtNombre.setText("");
            choProductora.select(0);
            choPais.select(0); // Restablecer la selección del país al primero de la lista
            txtNombre.requestFocus();
        }
    }
}