/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import gui.controller.Controlador_Principal;
import javax.swing.SwingUtilities;

/**
 *
 * @author oriol
 */
public class main {
        public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Controlador_Principal controlador = new Controlador_Principal();
            }
        });
    }
    
}
