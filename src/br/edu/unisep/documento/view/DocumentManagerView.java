package br.edu.unisep.documento.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DocumentManagerView extends JFrame {
    private final JTextField titleField;
    private final JTextField authorField;
    private final JTextField dateField;
    private final JTextField searchField;
    private final JTable documentTable;
    private final JButton saveButton;
    private final JButton loadButton;
    private final JButton deleteButton;
    private final JButton searchButton;
    private final JButton editButton;
    private final JButton saveChangesButton; // Novo botão para salvar alterações

    public DocumentManagerView() {
        setTitle("Gerenciador de Documentos");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Painel Superior (Cadastro)
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Cadastro de Documentos"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Adicionando margens entre os componentes
        gbc.anchor = GridBagConstraints.EAST; // Alinhando os componentes à direita

        titleField = new JTextField(20);
        authorField = new JTextField(20);
        dateField = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Título:"), gbc);
        gbc.gridx = 1;
        formPanel.add(titleField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Autor:"), gbc);
        gbc.gridx = 1;
        formPanel.add(authorField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Data (DD/MM/AAAA):"), gbc);
        gbc.gridx = 1;
        formPanel.add(dateField, gbc);

        saveButton = new JButton("Salvar");

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(saveButton, gbc);

        JButton clearButton = new JButton("Limpar");
        clearButton.addActionListener(e -> clearForm());
        gbc.gridx = 1;
        formPanel.add(clearButton, gbc);

        add(formPanel, BorderLayout.NORTH);

        // Painel Central (Tabela de Documentos)
        documentTable = new JTable(new DefaultTableModel(new Object[]{"Título", "Autor", "Data de Criação"}, 0));
        documentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(documentTable), BorderLayout.CENTER);

        // Painel Inferior (Busca e Ações)
        JPanel actionPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        searchField = new JTextField();
        searchButton = new JButton("Buscar");
        searchPanel.add(new JLabel("Buscar (Título ou Autor):"), BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        loadButton = new JButton("Carregar Dados");
        deleteButton = new JButton("Excluir");
        editButton = new JButton("Editar"); // Botão para editar
        saveChangesButton = new JButton("Salvar Alterações"); // Novo botão de "Salvar Alterações"
        saveChangesButton.setEnabled(false); // Inicialmente desabilitado
        buttonPanel.add(loadButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton); // Adicionando o botão de editar
        buttonPanel.add(saveChangesButton); // Adicionando o botão de "Salvar Alterações"

        actionPanel.add(searchPanel);
        actionPanel.add(buttonPanel);

        add(actionPanel, BorderLayout.SOUTH);

        // ActionListener do Botão Editar
        editButton.addActionListener(e -> enableEditMode());

        // ActionListener do Botão Salvar Alterações
        saveChangesButton.addActionListener(e -> saveChanges());
    }

    public JTextField getTitleField() {
        return titleField;
    }

    public JTextField getAuthorField() {
        return authorField;
    }

    public JTextField getDateField() {
        return dateField;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JTable getDocumentTable() {
        return documentTable;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getLoadButton() {
        return loadButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JButton getSaveChangesButton() {
        return saveChangesButton;
    }

    public void clearForm() {
        titleField.setText("");
        authorField.setText("");
        dateField.setText("");
    }

    // Habilitar modo de edição
    public void enableEditMode() {
        // Habilitar os campos de texto para edição
        titleField.setEditable(true);
        authorField.setEditable(true);
        dateField.setEditable(true);
        saveButton.setEnabled(false); // Desabilita o botão "Salvar"
        saveChangesButton.setEnabled(true); // Habilita o botão "Salvar Alterações"
    }

    // Desabilitar modo de edição
    public void disableEditMode() {
        titleField.setEditable(false);
        authorField.setEditable(false);
        dateField.setEditable(false);
        saveButton.setEnabled(true); // Habilita o botão "Salvar"
        saveChangesButton.setEnabled(false); // Desabilita o botão "Salvar Alterações"
    }

    private void saveChanges() {
        // Implementar a lógica para salvar as alterações no documento
        // Aqui você pode pegar os valores dos campos e chamar o método correspondente no controlador
        System.out.println("Alterações salvas!");
        disableEditMode(); // Desabilita o modo de edição após salvar as alterações
    }
}
