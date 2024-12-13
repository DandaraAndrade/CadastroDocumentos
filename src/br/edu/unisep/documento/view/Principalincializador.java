package br.edu.unisep.documento;

import br.edu.unisep.documento.view.DocumentController;
import br.edu.unisep.documento.view.DocumentManagerView;

import javax.swing.*;

public class Principalincializador {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            DocumentManagerView view = new DocumentManagerView();

            view.setSize(800, 600);

            view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            new DocumentController(view);

            view.setVisible(true);
        });
    }
}
