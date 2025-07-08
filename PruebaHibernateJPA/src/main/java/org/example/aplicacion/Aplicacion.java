package org.example.aplicacion;


import java.util.List;
import javax.swing.JOptionPane;

import org.example.dao.MascotasDao;
import org.example.entidades.Mascota;

public class Aplicacion {
    MascotasDao miMascotasDao = new MascotasDao();

    public void iniciar() {
        String menu = "MENU DE OPCIONES\n\n" +
                "1. Registrar Mascota\n" +
                "2. Consultar Mascota\n" +
                "3. Consultar Lista de Mascotas\n" +
                "4. Consultar Lista de Mascotas por sexo\n" +
                "5. Actualizar Mascota\n" +
                "6. Eliminar Mascota\n" +
                "7. Salir\n";

        int opc = 0;
        while (opc != 7) {
            opc = Integer.parseInt(JOptionPane.showInputDialog(menu));
            switch (opc) {
                case 1 -> registrar();
                case 2 -> consultar();
                case 3 -> consultarLista();
                case 4 -> consultarListaPorSexo();
                case 5 -> actualizar();
                case 6 -> eliminar();
                case 7 -> miMascotasDao.close();
            }
        }
    }

    private void registrar() {
        Mascota m = new Mascota(
                JOptionPane.showInputDialog("Ingrese el nombre de la mascota"),
                JOptionPane.showInputDialog("Ingrese la raza de la mascota"),
                JOptionPane.showInputDialog("Ingrese el color de la mascota"),
                JOptionPane.showInputDialog("Ingrese el sexo de su mascota")
        );
        System.out.println(miMascotasDao.registrarMascota(m));
    }

    private void consultar() {
        Long id = Long.parseLong(JOptionPane.showInputDialog("Ingrese el id de la Mascota"));
        Mascota m = miMascotasDao.consultarMascota(id);
        System.out.println(m != null ? m : "No se encontró la mascota");
    }

    private void consultarLista() {
        List<Mascota> lista = miMascotasDao.consultarListaMascotas();
        lista.forEach(System.out::println);
    }

    private void consultarListaPorSexo() {
        String sexo = JOptionPane.showInputDialog("Ingrese el sexo de la Mascota");
        List<Mascota> lista = miMascotasDao.consultarListaMascotasPorSexo(sexo);
        lista.forEach(System.out::println);
    }

    private void actualizar() {
        Long id = Long.parseLong(JOptionPane.showInputDialog("Ingrese el id de la Mascota para actualizar"));
        Mascota m = miMascotasDao.consultarMascota(id);
        if (m != null) {
            m.setNombre(JOptionPane.showInputDialog("Ingrese el nombre de la mascota"));
            m.setRaza(JOptionPane.showInputDialog("Ingrese la raza de la mascota"));
            m.setColorMascota(JOptionPane.showInputDialog("Ingrese el color de la mascota"));
            m.setSexo(JOptionPane.showInputDialog("Ingrese el sexo de su mascota"));
            System.out.println(miMascotasDao.actualizarMascota(m));
        } else {
            System.out.println("No se encontró la mascota");
        }
    }

    private void eliminar() {
        Long id = Long.parseLong(JOptionPane.showInputDialog("Ingrese el id de la Mascota para eliminar"));
        Mascota m = miMascotasDao.consultarMascota(id);
        if (m != null) {
            System.out.println(miMascotasDao.eliminarMascota(m));
        } else {
            System.out.println("No se encontró la mascota");
        }
    }
}
