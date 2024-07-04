
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Tela extends JFrame {

    public Tela() {
        setTitle("Leitor de Arquivos Texto Multithreading");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(null);
        initComponets();
        setVisible(true);
    }

    public void initComponets() {
        JLabel lblArq1 = new JLabel("Progresso de leitura do arquivo 1:");
        lblArq1.setBounds(10, 5, 200, 25);
        add(lblArq1);

        JTextField jtxtArq1 = new JTextField("");
        jtxtArq1.setBounds(10, 35, 300, 30);
        add(jtxtArq1);

        JProgressBar progressBarArq1 = new JProgressBar(0, 100);
        progressBarArq1.setStringPainted(true);
        progressBarArq1.setBounds(10, 75, 300, 20);
        add(progressBarArq1);

        JLabel lblTimeTask1 = new JLabel("Tempo da tarefa 1:");
        lblTimeTask1.setBounds(10, 120, 150, 25);
        add(lblTimeTask1);

        JTextField jtxtTimeTask1 = new JTextField("");
        jtxtTimeTask1.setBounds(150, 120, 160, 30);
        add(jtxtTimeTask1);

        JLabel lbltxtArq2 = new JLabel("Progresso de leitura do arquivo 2:");
        lbltxtArq2.setBounds(10, 190, 200, 25);
        add(lbltxtArq2);

        JTextField jtxtArq2 = new JTextField("");
        jtxtArq2.setBounds(10, 225, 300, 30);
        add(jtxtArq2);

        JProgressBar progressBarArq2 = new JProgressBar(0, 100);
        progressBarArq2.setStringPainted(true);
        progressBarArq2.setBounds(10, 265, 300, 20);
        add(progressBarArq2);

        JLabel lblTimeTask2 = new JLabel("Tempo da tarefa 2:");
        lblTimeTask2.setBounds(10, 310, 150, 25);
        add(lblTimeTask2);

        JTextField jtxtTimeTask2 = new JTextField("");
        jtxtTimeTask2.setBounds(150, 310, 160, 30);
        add(jtxtTimeTask2);

        JButton btnStart2 = new JButton("Iniciar Leitura Arq 2");
        btnStart2.setBounds(10, 350, 300, 35);
        add(btnStart2);

        JButton btnStart1 = new JButton("Iniciar Leitura Arq 1");
        btnStart1.setBounds(10, 155, 300, 35);
        add(btnStart1);

        JLabel lblInput = new JLabel("Texto de Entrada:");
        lblInput.setBounds(10, 390, 100, 30);
        add(lblInput);

        JTextField jtxtInput = new JTextField("");
        jtxtInput.setBounds(10, 420, 560, 30);
        add(jtxtInput);

        JTextArea jTextArea = new JTextArea("");
        jTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(jTextArea);
        scrollPane.setBounds(325, 5, 245, 400);
        add(scrollPane);
        javax.swing.border.Border gray = BorderFactory.createLineBorder(Color.GRAY);
        scrollPane.setBorder(gray);

        btnStart1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                btnStart1.setEnabled(false);
                btnStart1.setText("Tarefa em Andamento");
                progressBarArq1.setValue(0);
                try {
                    FileWorker1 worker1 = new FileWorker1(
                            "C:\\Users\\joao_cristofolini.EDU_FIESC\\Documents\\Visual Studio 2022\\Java\\Projetos\\LeitorArqSwingWorker\\Arq1.txt",
                            jTextArea, Integer.parseInt(jtxtTimeTask1.getText()), btnStart1, jtxtArq1);
                    worker1.addPropertyChangeListener(evt -> {
                        if ("progress".equals(evt.getPropertyName())) {
                            progressBarArq1.setValue((Integer) evt.getNewValue());
                        }
                    });
                    worker1.execute();
                } catch (Exception f) {
                    JOptionPane.showMessageDialog(Tela.this, "Erro nos Parametros!!");
                    btnStart1.setEnabled(true);
                    btnStart1.setText("Iniciar Tarefa");
                }
            }

        });

        btnStart2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                btnStart2.setEnabled(false);
                btnStart2.setText("Tarefa em Andamento");
                progressBarArq2.setValue(0);
                try {
                    FileWorker2 worker2 = new FileWorker2(
                            "C:\\Users\\joao_cristofolini.EDU_FIESC\\Documents\\Visual Studio 2022\\Java\\Projetos\\LeitorArqSwingWorker\\Arq2.txt",
                            jTextArea, Integer.parseInt(jtxtTimeTask2.getText()), btnStart2, jtxtArq2);
                    worker2.addPropertyChangeListener(evt -> {
                        if ("progress".equals(evt.getPropertyName())) {
                            progressBarArq2.setValue((Integer) evt.getNewValue());
                        }
                    });
                    worker2.execute();
                } catch (Exception f) {
                    JOptionPane.showMessageDialog(Tela.this, "Erro nos Parametros!!");
                    btnStart2.setEnabled(true);
                    btnStart2.setText("Iniciar Tarefa");
                }
            }

        });

        jtxtInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texto = jtxtInput.getText();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        String input = jtxtInput.getText().trim();
                        if (!input.isEmpty()) {
                            jTextArea.append(input + "\n");
                            jtxtInput.setText(""); // Limpa o campo após imprimir
                        }

                    }
                });

            }
        });
    }

}
