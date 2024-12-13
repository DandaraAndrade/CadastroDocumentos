package br.edu.unisep.documento;

import br.edu.unisep.documento.view.DocumentController;
import br.edu.unisep.documento.view.DocumentManagerView;

import javax.swing.*;

public class Principalincializador {
    public static void main(String[] args) {
        // Garantir que a interface gráfica seja criada na thread correta
        SwingUtilities.invokeLater(() -> {
            // Criar a interface gráfica
            DocumentManagerView view = new DocumentManagerView();

            // Definir o tamanho da janela (opcional)
            view.setSize(800, 600);

            // Definir operação de fechar (opcional)
            view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Inicializar o controlador
            new DocumentController(view);

            // Tornar a janela visível
            view.setVisible(true);
        });
    }
}
