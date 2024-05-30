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

public class ModificarProductora implements WindowListener, ActionListener {
    Frame ventana = new Frame("Edicion Productora");
    Dialog dlgEdicion = new Dialog(ventana, "Editar Productora", true);
    Dialog dlgMensaje = new Dialog(ventana, "Mensaje", true);

    Label lblElegir = new Label("Elegir la productora a editar:");

    Choice choProductoras = new Choice();
    Choice choPaises = new Choice();

    Button btnEditar = new Button("Editar");
    Button btnCancelar = new Button("Cancelar");

    Label lblTitulo = new Label("------------------------ Editar Productora ------------------------");
    Label Espacio = new Label("\n");
    Label lblProductora = new Label("Nombre productora:");
    Label lblMensaje = new Label("Edicion de Productora Correcta");

    TextField txtProductora = new TextField(10);

    Button btnModificar = new Button("Modificar");
    Button btnVolver = new Button("Volver");

    Conexion conexion = new Conexion();
    String idProductora = "";

    ModificarProductora() {
        ventana.setLayout(new FlowLayout());
        ventana.setSize(250, 150);
        ventana.addWindowListener(this);

        ventana.add(lblElegir);
        // Rellenar el Choice con los elementos de la tabla productoras
        conexion.rellenarChoiceProductora(choProductoras);
        conexion.rellenarChoicePaises(choPaises);
   
        ventana.add(choProductoras);
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
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        if (dlgEdicion.isActive()) {
            dlgEdicion.setVisible(false);
            dlgMensaje.setVisible(false);
        } else if (dlgMensaje.isActive()) {
            dlgMensaje.setVisible(false);
            // Rellenar el Choice con los elementos de la tabla productoras
            conexion.rellenarChoiceProductora(choProductoras);
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
            // Si NO está seleccionada la primera opcion
            if (choProductoras.getSelectedIndex() != 0) {
                String tabla[] = choProductoras.getSelectedItem().split("-");
                idProductora = tabla[0];
                String datosObtenidos = conexion.editarProductora(idProductora);
                
                String[] tablaDatos = datosObtenidos.split("-");
                dlgEdicion.setLayout(new FlowLayout());
                dlgEdicion.setSize(320, 220);
                dlgEdicion.addWindowListener(this);

                dlgEdicion.add(lblTitulo);
                dlgEdicion.add(lblProductora);
                dlgEdicion.add(txtProductora);
                txtProductora.setText(tablaDatos[0]);
                txtProductora.setText("");
                dlgEdicion.add(Espacio);
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
            if (txtProductora.getText().length() == 0) {
                lblMensaje.setText("Los campos estan vacios");
            } else {
            	 String idPais = choPaises.getSelectedItem().split("-")[0];
            	 String sentencia = "UPDATE productoras SET nombreProductora = '" + txtProductora.getText() + "', idPaisFK = '"
                         + idPais + "' WHERE idProductora = " + idProductora + ";";
                int respuesta = conexion.modificarProductora(sentencia);
                if (respuesta != 0) {
                    // Mostrar Mensaje Error
                    lblMensaje.setText("Error en Modificacion");
                } else {
                    lblMensaje.setText("Modificacion de Productora Correcta");
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
