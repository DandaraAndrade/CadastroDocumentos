package br.edu.unisep.documento;

import br.edu.unisep.documento.view.DocumentController;
import br.edu.unisep.documento.view.DocumentManagerView;

import javax.swing.SwingUtilities;

public class Principal {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                DocumentManagerView view = new DocumentManagerView();

                new DocumentController(view);
                view.setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Erro ao iniciar a aplicação: " + e.getMessage());
            }
        });
    }
}
