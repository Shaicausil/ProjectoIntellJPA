package org.example.gui;

import org.example.dao.MascotasDao;
import org.example.entidades.Mascota;

import javax.swing.*;
import java.awt.*;

public class VistaPrincipal extends JFrame {

    private JTextField txtId, txtNombre, txtRaza, txtColor, txtSexo;
    private JTextArea areaResultado;

    private JButton btnRegistrar, btnConsultar, btnListar, btnActualizar, btnEliminar, btnListarPorSexo;

    public VistaPrincipal() {
        setTitle("Gestión de Mascotas");
        setSize(700, 520);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        getContentPane().add(panel);


        JPanel panelCampos = new JPanel(new GridLayout(5, 2, 5, 5));
        txtId = new JTextField();
        txtNombre = new JTextField();
        txtRaza = new JTextField();
        txtColor = new JTextField();
        txtSexo = new JTextField();

        panelCampos.add(new JLabel("ID:"));
        panelCampos.add(txtId);
        panelCampos.add(new JLabel("Nombre:"));
        panelCampos.add(txtNombre);
        panelCampos.add(new JLabel("Raza:"));
        panelCampos.add(txtRaza);
        panelCampos.add(new JLabel("Color:"));
        panelCampos.add(txtColor);
        panelCampos.add(new JLabel("Sexo:"));
        panelCampos.add(txtSexo);

        panel.add(panelCampos, BorderLayout.NORTH);
        JPanel panelBotones = new JPanel(new GridLayout(2, 1, 5, 5));
        JPanel fila1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        btnRegistrar = new JButton("Registrar");
        btnConsultar = new JButton("Consultar");
        btnListar = new JButton("Listar todas");
        fila1.add(btnRegistrar);
        fila1.add(btnConsultar);
        fila1.add(btnListar);
        JPanel fila2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnListarPorSexo = new JButton("Listar por Sexo");
        fila2.add(btnActualizar);
        fila2.add(btnEliminar);
        fila2.add(btnListarPorSexo);

        panelBotones.add(fila1);
        panelBotones.add(fila2);

        panel.add(panelBotones, BorderLayout.CENTER);


        areaResultado = new JTextArea(10, 50);
        areaResultado.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaResultado);
        panel.add(scrollPane, BorderLayout.SOUTH);


        btnRegistrar.addActionListener(e -> {
            try {
                Mascota mascota = new Mascota(
                        txtNombre.getText(),
                        txtRaza.getText(),
                        txtColor.getText(),
                        txtSexo.getText()
                );
                MascotasDao dao = new MascotasDao();
                String resultado = dao.registrarMascota(mascota);
                areaResultado.setText(resultado);
                limpiarCampos();
            } catch (Exception ex) {
                areaResultado.setText("Error al registrar: " + ex.getMessage());
            }
        });

        btnConsultar.addActionListener(e -> {
            try {
                Long id = Long.parseLong(txtId.getText());
                MascotasDao dao = new MascotasDao();
                Mascota mascota = dao.consultarMascota(id);
                if (mascota != null) {
                    txtNombre.setText(mascota.getNombre());
                    txtRaza.setText(mascota.getRaza());
                    txtColor.setText(mascota.getColorMascota());
                    txtSexo.setText(mascota.getSexo());
                    areaResultado.setText("Mascota encontrada:\n" + mascota);
                } else {
                    areaResultado.setText("Mascota no encontrada.");
                }
            } catch (Exception ex) {
                areaResultado.setText("Error al consultar: " + ex.getMessage());
            }
        });

        btnListar.addActionListener(e -> {
            try {
                MascotasDao dao = new MascotasDao();
                StringBuilder sb = new StringBuilder("Lista de mascotas:\n");
                for (Mascota m : dao.consultarListaMascotas()) {
                    sb.append(m).append("\n");
                }
                areaResultado.setText(sb.toString());
            } catch (Exception ex) {
                areaResultado.setText("Error al listar: " + ex.getMessage());
            }
        });

        btnActualizar.addActionListener(e -> {
            try {
                Long id = Long.parseLong(txtId.getText());
                MascotasDao dao = new MascotasDao();
                Mascota mascota = dao.consultarMascota(id);
                if (mascota != null) {
                    mascota.setNombre(txtNombre.getText());
                    mascota.setRaza(txtRaza.getText());
                    mascota.setColorMascota(txtColor.getText());
                    mascota.setSexo(txtSexo.getText());
                    String resultado = dao.actualizarMascota(mascota);
                    areaResultado.setText(resultado);
                    limpiarCampos();
                } else {
                    areaResultado.setText("Mascota no encontrada para actualizar.");
                }
            } catch (Exception ex) {
                areaResultado.setText("Error al actualizar: " + ex.getMessage());
            }
        });

        btnEliminar.addActionListener(e -> {
            try {
                Long id = Long.parseLong(txtId.getText());
                MascotasDao dao = new MascotasDao();
                Mascota mascota = dao.consultarMascota(id);
                if (mascota != null) {
                    String resultado = dao.eliminarMascota(mascota);
                    areaResultado.setText(resultado);
                    limpiarCampos();
                } else {
                    areaResultado.setText("Mascota no encontrada para eliminar.");
                }
            } catch (Exception ex) {
                areaResultado.setText("Error al eliminar: " + ex.getMessage());
            }
        });

        btnListarPorSexo.addActionListener(e -> {
            try {
                String sexo = txtSexo.getText().trim();
                if (sexo.isEmpty()) {
                    areaResultado.setText("Por favor ingrese un valor en el campo 'Sexo'.");
                    return;
                }

                MascotasDao dao = new MascotasDao();
                StringBuilder sb = new StringBuilder("Mascotas con sexo '" + sexo + "':\n");
                for (Mascota m : dao.consultarListaMascotasPorSexo(sexo)) {
                    sb.append(m).append("\n");
                }
                areaResultado.setText(sb.toString());
            } catch (Exception ex) {
                areaResultado.setText("Error al consultar por sexo: " + ex.getMessage());
            }
        });
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtRaza.setText("");
        txtColor.setText("");
        txtSexo.setText("");
    }

    public static void main(String[] args) {
        VistaPrincipal vista = new VistaPrincipal();
        vista.setVisible(true);
    }

}


