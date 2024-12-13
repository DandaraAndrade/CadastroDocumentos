package br.edu.unisep.documento;

import br.edu.unisep.documento.view.DocumentController;
import br.edu.unisep.documento.view.DocumentManagerView;

import javax.swing.SwingUtilities;

public class Principal {
    public static void main(String[] args) {
        // Executa a interface gráfica na Thread do Swing (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> {
            try {
                // Criar a interface gráfica
                DocumentManagerView view = new DocumentManagerView();

                // Inicializar o controlador
                new DocumentController(view);

                // Tornar a janela visível
                view.setVisible(true);
            } catch (Exception e) {
                // Tratar possíveis exceções ao inicializar a aplicação
                e.printStackTrace();
                System.err.println("Erro ao iniciar a aplicação: " + e.getMessage());
            }
        });
    }
}
