package br.edu.unisep.documento.view;

import br.edu.unisep.documento.model.Document;
import br.edu.unisep.documento.model.DocumentUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DocumentController {

    private final DocumentManagerView view;
    private static final String ERROR_TITLE = "Erro";
    private static final String INFO_TITLE = "Informação";

    private static final String INVALID_INPUT_MSG = "Preencha todos os campos corretamente! Certifique-se de que a data está no formato DD/MM/AAAA.";
    private static final String SAVE_SUCCESS_MSG = "Documento salvo com sucesso!";
    private static final String SAVE_FAILURE_MSG = "Erro ao salvar o documento. Tente novamente.";
    private static final String DELETE_SUCCESS_MSG = "Documento excluído com sucesso!";
    private static final String DELETE_FAILURE_MSG = "Erro ao excluir o documento. Tente novamente.";
    private static final String LOAD_FAILURE_MSG = "Erro ao carregar documentos.";
    private static final String SEARCH_FAILURE_MSG = "Erro ao buscar documentos.";
    private static final String SELECT_DOC_MSG = "Selecione um documento para excluir.";
    private static final String NO_SELECTION_MSG = "Selecione um documento para editar.";

    private Optional<Document> documentToEdit = Optional.empty();

    public DocumentController(DocumentManagerView view) {
        this.view = view;
        initializeController();
    }

    private void initializeController() {
        // Definindo as ações para os botões
        view.getSaveButton().addActionListener(e -> handleSaveOrUpdate());
        view.getLoadButton().addActionListener(e -> handleLoad());
        view.getDeleteButton().addActionListener(e -> handleDelete());
        view.getSearchButton().addActionListener(e -> handleSearch());
        view.getEditButton().addActionListener(e -> handleEdit());

        // Adicionando ação para o novo botão "Salvar Alterações"
        view.getSaveChangesButton().addActionListener(e -> handleSaveChanges());
    }

    private void handleSaveOrUpdate() {
        String title = view.getTitleField().getText().trim();
        String author = view.getAuthorField().getText().trim();
        String date = view.getDateField().getText().trim();

        // Verificando se os campos estão preenchidos corretamente
        if (isInvalidInput(title, author, date)) {
            highlightInvalidFields(title, author, date); // Destacar campos inválidos
            showError(INVALID_INPUT_MSG);
            return;
        }

        Document document = new Document(title, author, date);

        if (documentToEdit.isPresent()) {
            // Editar documento existente
            if (DocumentUtils.updateDocument(documentToEdit.get().getTitle(), document)) {
                showInfo("Documento atualizado com sucesso!");
                handleLoad();
            } else {
                showError("Erro ao atualizar o documento.");
            }
        } else {
            // Salvar novo documento
            if (DocumentUtils.saveDocument(document)) {
                showInfo(SAVE_SUCCESS_MSG);
                handleLoad();
            } else {
                showError(SAVE_FAILURE_MSG);
            }
        }

        view.clearForm();
        documentToEdit = Optional.empty(); // Limpar o documento em edição
        resetFieldHighlighting(); // Resetar a cor dos campos
    }

    private void handleSaveChanges() {
        String title = view.getTitleField().getText().trim();
        String author = view.getAuthorField().getText().trim();
        String date = view.getDateField().getText().trim();

        // Verificando se os campos estão preenchidos corretamente
        if (isInvalidInput(title, author, date)) {
            highlightInvalidFields(title, author, date); // Destacar campos inválidos
            showError(INVALID_INPUT_MSG);
            return;
        }

        // Verifica se há um documento para editar
        if (documentToEdit.isPresent()) {
            Document document = new Document(title, author, date);
            if (DocumentUtils.updateDocument(documentToEdit.get().getTitle(), document)) {
                showInfo("Documento atualizado com sucesso!");
                handleLoad();
                view.clearForm();
                documentToEdit = Optional.empty();
                view.getSaveButton().setEnabled(true);
                view.getSaveChangesButton().setEnabled(false); // Desabilita o botão "Salvar Alterações"
                resetFieldHighlighting(); // Resetar a cor dos campos
            } else {
                showError("Erro ao atualizar o documento.");
            }
        }
    }

    private void handleLoad() {
        try {
            List<Document> documents = DocumentUtils.loadDocuments();
            updateTable(documents);
        } catch (Exception ex) {
            showError(LOAD_FAILURE_MSG + " " + ex.getMessage());
        }
    }

    private void handleDelete() {
        int selectedRow = view.getDocumentTable().getSelectedRow();
        if (selectedRow == -1) {
            showError(SELECT_DOC_MSG);
            return;
        }

        String title = (String) view.getDocumentTable().getValueAt(selectedRow, 0);
        int confirmation = JOptionPane.showConfirmDialog(view,
                "Tem certeza que deseja excluir o documento: " + title + "?",
                "Confirmação", JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            if (DocumentUtils.deleteDocument(title)) {
                showInfo(DELETE_SUCCESS_MSG);
                handleLoad();
            } else {
                showError(DELETE_FAILURE_MSG);
            }
        }
    }

    private void handleSearch() {
        String query = view.getSearchField().getText().trim().toLowerCase();
        if (query.isEmpty()) {
            showError("Digite algo para buscar.");
            return;
        }

        try {
            List<Document> documents = DocumentUtils.loadDocuments();
            List<Document> filtered = filterDocuments(documents, query);
            updateTable(filtered);
        } catch (Exception ex) {
            showError(SEARCH_FAILURE_MSG + " " + ex.getMessage());
        }
    }

    private void handleEdit() {
        int selectedRow = view.getDocumentTable().getSelectedRow();
        if (selectedRow == -1) {
            showError(NO_SELECTION_MSG);
            return;
        }

        String title = (String) view.getDocumentTable().getValueAt(selectedRow, 0);
        String author = (String) view.getDocumentTable().getValueAt(selectedRow, 1);
        String date = (String) view.getDocumentTable().getValueAt(selectedRow, 2);

        view.getTitleField().setText(title);
        view.getAuthorField().setText(author);
        view.getDateField().setText(date);

        documentToEdit = Optional.of(new Document(title, author, date));

        view.getSaveButton().setEnabled(false);
        view.getSaveChangesButton().setEnabled(true);
    }

    private List<Document> filterDocuments(List<Document> documents, String query) {
        return documents.stream()
                .filter(doc -> doc.getTitle().toLowerCase().contains(query) || doc.getAuthor().toLowerCase().contains(query))
                .collect(Collectors.toList());
    }

    private void updateTable(List<Document> documents) {
        DefaultTableModel tableModel = (DefaultTableModel) view.getDocumentTable().getModel();
        tableModel.setRowCount(0);
        documents.forEach(doc -> tableModel.addRow(new Object[]{doc.getTitle(), doc.getAuthor(), doc.getDate()}));
    }

    private boolean isInvalidInput(String title, String author, String date) {
        return title.isEmpty() || author.isEmpty() || !DocumentUtils.isValidDate(date);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(view, message, ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
    }

    private void showInfo(String message) {
        JOptionPane.showMessageDialog(view, message, INFO_TITLE, JOptionPane.INFORMATION_MESSAGE);
    }

    private void highlightInvalidFields(String title, String author, String date) {

        if (title.isEmpty()) {
            view.getTitleField().setBackground(Color.RED);
        } else {
            view.getTitleField().setBackground(Color.WHITE);
        }
        if (author.isEmpty()) {
            view.getAuthorField().setBackground(Color.RED);
        } else {
            view.getAuthorField().setBackground(Color.WHITE);
        }
        if (!DocumentUtils.isValidDate(date)) {
            view.getDateField().setBackground(Color.RED);
        } else {
            view.getDateField().setBackground(Color.WHITE);
        }
    }

    private void resetFieldHighlighting() {

        view.getTitleField().setBackground(Color.WHITE);
        view.getAuthorField().setBackground(Color.WHITE);
        view.getDateField().setBackground(Color.WHITE);
    }
}
