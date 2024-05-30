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

public class ModificarPelicula implements WindowListener, ActionListener {

    Frame ventana = new Frame("Edicion Pelicula");
    Dialog dlgEdicion = new Dialog(ventana, "Editar Pelicula", true);
    Dialog dlgMensaje = new Dialog(ventana, "Mensaje", true);

    Label lblElegir = new Label("Elegir la pelicula a editar:");

    Choice choPeliculas = new Choice();
    Choice choProductoras = new Choice();
    Choice choPaises = new Choice();

    Button btnEditar = new Button("Editar");
    Button btnCancelar = new Button("Cancelar");

    Label lblTitulo = new Label("------------------------ Editar Pelicula ------------------------");
    Label Espacio = new Label("\n");
    Label lblPelicula = new Label("Nombre Pelicula:");
    Label lblMensaje = new Label("Edicion de Pelicula Correcta");

    TextField txtPelicula = new TextField(15);

    Button btnModificar = new Button("Modificar");
    Button btnVolver = new Button("Volver");

    Conexion conexion = new Conexion();
    String idPelicula = "";

    ModificarPelicula() {
        ventana.setLayout(new FlowLayout());
        ventana.setSize(300, 200);
        ventana.addWindowListener(this);

        ventana.add(lblElegir);

        conexion.rellenarChoicePelicula(choPeliculas);
        conexion.rellenarChoiceProductora(choProductoras);
        conexion.rellenarChoicePaises(choPaises);

        ventana.add(choPeliculas);
        btnEditar.addActionListener(this);
        ventana.add(btnEditar);
        btnCancelar.addActionListener(this);
        ventana.add(btnCancelar);

        ventana.setResizable(true);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);

        dlgMensaje.setLayout(new FlowLayout());
        dlgMensaje.setSize(250, 100);
        dlgMensaje.addWindowListener(this);
    }

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {
        if (dlgEdicion.isActive()) {
            dlgEdicion.setVisible(false);
            dlgMensaje.setVisible(false);
        } else if (dlgMensaje.isActive()) {
            dlgMensaje.setVisible(false);
            // Rellenar el Choice con los elementos de la tabla peliculas
            conexion.rellenarChoicePelicula(choPeliculas);
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
        if (e.getSource().equals(btnCancelar)) {
            ventana.setVisible(false);
        } else if (e.getSource().equals(btnEditar)) {
            // Si NO está seleccionada la primera opción
            if (choPeliculas.getSelectedIndex() != 0) {
                String[] tabla = choPeliculas.getSelectedItem().split("-");
                idPelicula = tabla[0];
                String datosObtenidos = conexion.editarPelicula(idPelicula);
                
                String[] tablaDatos = datosObtenidos.split("-");
                dlgEdicion.setLayout(new FlowLayout());
                dlgEdicion.setSize(350, 250);
                dlgEdicion.addWindowListener(this);

                dlgEdicion.add(lblTitulo);
                dlgEdicion.add(lblPelicula);
                txtPelicula.setText(tablaDatos[0]);
                dlgEdicion.add(txtPelicula);
                txtPelicula.setText("");
                dlgEdicion.add(Espacio);
                
                dlgEdicion.add(choProductoras);
                dlgEdicion.add(choPaises);

                btnModificar.addActionListener(this);
                btnVolver.addActionListener(this);

                dlgEdicion.add(btnModificar);
                dlgEdicion.add(btnVolver);

                dlgEdicion.setResizable(true);
                dlgEdicion.setLocationRelativeTo(null);
                dlgEdicion.setVisible(true);
            }
        } else if (e.getSource().equals(btnModificar)) {
            if (txtPelicula.getText().length() == 0) {
                lblMensaje.setText("Los campos estan vacios");
            } else {
                String idProductora = choProductoras.getSelectedItem().split("-")[0];
                String idPais = choPaises.getSelectedItem().split("-")[0];
                String sentencia = "UPDATE Peliculas SET nombrePelicula = '" + txtPelicula.getText() + 
                                   "', idProductoraFK = '" + idProductora + 
                                   "', idPaisFK = '" + idPais + 
                                   "' WHERE idPelicula = " + idPelicula + ";";
                int respuesta = conexion.ModificarPelicula(sentencia);
                if (respuesta != 0) {
                    // Mostrar Mensaje Error
                    lblMensaje.setText("Error en Modificacion");
                } else {
                    lblMensaje.setText("Modificacion de Pelicula Correcta");
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
