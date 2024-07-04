import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class FileWorker2 extends SwingWorker<String, String> {
    private final String filePath;
    private final JTextArea textArea;
    private final int tempo;
    private final JButton button;
    private final JTextField textField;
    private final int totalLinhas;
    private int linesRead;
    int progress;

    public FileWorker2(String filePath, JTextArea textArea, int tempo, JButton button, JTextField textField) {
        this.filePath = filePath;
        this.textArea = textArea;
        this.tempo = tempo;
        this.button = button;
        this.textField = textField;
        totalLinhas = contaTotalLinhas(filePath);
    }

    @Override
    protected String doInBackground() throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            linesRead = 0;
            while ((line = reader.readLine()) != null) {
                final String currentLine = line;
                linesRead++;
                publish(currentLine);
                progress = (int) (((double) linesRead / totalLinhas) * 100);
                setProgress(progress);
                Thread.sleep(tempo);
            }
        } catch (IOException e) {
            mostraErro("Erro ao ler o arquivo 2: " + e.getMessage());
        } catch (InterruptedException e) {
            mostraErro("A leitura do arquivo foi interrompida: " + e.getMessage());
        }
        return "Tarefa Concluida com sucesso!";
    }

    @Override
    protected void process(List<String> chunks) {
        for (String chunk : chunks) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    textArea.append((chunk + "\n"));
                    textField.setText(chunk);
                }
            });

        }
    }

    @Override
    protected void done() {
        try {
            String result = get();
            Thread.sleep(500);
            textArea.append(result + "\n");
            button.setEnabled(true);
            button.setText("Iniciar Tarefa");
        } catch (InterruptedException | ExecutionException e) {
            textArea.append("Erro: " + e.getMessage() + "\n");
        }
    }

    private int contaTotalLinhas(String filePath) {
        int lineCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while (reader.readLine() != null) {
                lineCount++;
            }
        } catch (IOException e) {
            mostraErro("Erro ao contar as linhas do arquivo: " + e.getMessage());
        }
        return lineCount;
    }

    private void mostraErro(final String msg) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(textArea.getParent(), msg, "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

}
