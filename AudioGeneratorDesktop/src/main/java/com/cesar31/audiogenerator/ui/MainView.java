package com.cesar31.audiogenerator.ui;

import com.cesar31.audiogenerator.control.FileControl;
import com.cesar31.audiogenerator.control.MainControl;
import com.cesar31.audiogenerator.error.Err;
import java.io.File;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;

/**
 *
 * @author cesar31
 */
public class MainView extends javax.swing.JFrame {

    private MainControl control;
    private FileControl file;
    private LineNumber line;
    private String path;

    public MainView(MainControl control) {
        this.control = control;
        initComponents();

        line = new LineNumber(editor);
        scroll.setRowHeaderView(line);
        file = control.getFile();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        tabbed = new javax.swing.JTabbedPane();
        editorPanel = new javax.swing.JPanel();
        scroll = new javax.swing.JScrollPane();
        editor = new javax.swing.JTextArea();
        label_caret = new javax.swing.JLabel();
        compileButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        log = new javax.swing.JTextArea();
        saveTrack = new javax.swing.JButton();
        typeCombo = new javax.swing.JComboBox<>();
        logPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        errorTable = new javax.swing.JTable();
        playerPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        scrollMusicList = new javax.swing.JScrollPane();
        musicList = new javax.swing.JList<>();
        playButton = new javax.swing.JButton();
        playButton1 = new javax.swing.JButton();
        playButton2 = new javax.swing.JButton();
        playButton3 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        scrollMusicList1 = new javax.swing.JScrollPane();
        playList = new javax.swing.JList<>();
        jLabel4 = new javax.swing.JLabel();
        scrollMusicList2 = new javax.swing.JScrollPane();
        songsList = new javax.swing.JList<>();
        playButton4 = new javax.swing.JButton();
        playButton5 = new javax.swing.JButton();
        progressBar = new javax.swing.JProgressBar();
        playPause = new javax.swing.JButton();
        repeatCheck = new javax.swing.JCheckBox();
        songLabel = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        new_Item = new javax.swing.JMenuItem();
        open_item = new javax.swing.JMenuItem();
        save_Item = new javax.swing.JMenuItem();
        saveAs_Item = new javax.swing.JMenuItem();
        exit_Item = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(150, 850));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(24, 26, 31));
        jPanel1.setPreferredSize(new java.awt.Dimension(1200, 850));

        tabbed.setBackground(new java.awt.Color(24, 26, 31));
        tabbed.setForeground(new java.awt.Color(255, 255, 255));
        tabbed.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        tabbed.setPreferredSize(new java.awt.Dimension(1500, 850));
        tabbed.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabbedStateChanged(evt);
            }
        });

        editorPanel.setBackground(new java.awt.Color(24, 26, 31));
        editorPanel.setPreferredSize(new java.awt.Dimension(1500, 850));

        editor.setBackground(new java.awt.Color(40, 44, 53));
        editor.setColumns(20);
        editor.setFont(new java.awt.Font("FuraCode Nerd Font", 0, 20)); // NOI18N
        editor.setForeground(new java.awt.Color(255, 255, 255));
        editor.setRows(5);
        editor.setTabSize(4);
        editor.setCaretColor(new java.awt.Color(9, 177, 80));
        editor.setMargin(new java.awt.Insets(10, 10, 10, 10));
        editor.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                editorCaretUpdate(evt);
            }
        });
        scroll.setViewportView(editor);

        label_caret.setBackground(new java.awt.Color(24, 26, 31));
        label_caret.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        label_caret.setForeground(new java.awt.Color(255, 255, 255));
        label_caret.setText("Linea: 1 Columna: 0");

        compileButton.setBackground(new java.awt.Color(40, 180, 99));
        compileButton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        compileButton.setForeground(new java.awt.Color(24, 26, 31));
        compileButton.setText("Compilar");
        compileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compileButtonActionPerformed(evt);
            }
        });

        log.setEditable(false);
        log.setBackground(new java.awt.Color(40, 44, 53));
        log.setColumns(20);
        log.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        log.setForeground(new java.awt.Color(40, 180, 99));
        log.setRows(5);
        log.setTabSize(4);
        log.setMargin(new java.awt.Insets(10, 10, 10, 10));
        jScrollPane1.setViewportView(log);

        saveTrack.setBackground(new java.awt.Color(40, 180, 99));
        saveTrack.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        saveTrack.setForeground(new java.awt.Color(24, 26, 31));
        saveTrack.setText("Guardar Pista");
        saveTrack.setEnabled(false);
        saveTrack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveTrackActionPerformed(evt);
            }
        });

        typeCombo.setBackground(new java.awt.Color(40, 44, 53));
        typeCombo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        typeCombo.setForeground(new java.awt.Color(255, 255, 255));
        typeCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Definicion de Pistas", "Definicion de Lista de Reproducción" }));

        javax.swing.GroupLayout editorPanelLayout = new javax.swing.GroupLayout(editorPanel);
        editorPanel.setLayout(editorPanelLayout);
        editorPanelLayout.setHorizontalGroup(
            editorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editorPanelLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(editorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editorPanelLayout.createSequentialGroup()
                        .addComponent(label_caret, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 537, Short.MAX_VALUE)
                        .addComponent(typeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(compileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(saveTrack, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1)
                    .addComponent(scroll))
                .addGap(29, 29, 29))
        );
        editorPanelLayout.setVerticalGroup(
            editorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editorPanelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(editorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_caret, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(compileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saveTrack, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(typeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        tabbed.addTab("Editor", editorPanel);

        logPanel.setBackground(new java.awt.Color(24, 26, 31));

        jLabel1.setBackground(new java.awt.Color(24, 26, 31));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Errores Lexicos, Semanticos, Sintacticos y de Ejecucion");

        jScrollPane3.setPreferredSize(new java.awt.Dimension(1360, 600));

        jScrollPane2.setPreferredSize(new java.awt.Dimension(3500, 1800));

        errorTable.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        errorTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        errorTable.setPreferredSize(new java.awt.Dimension(3500, 1800));
        jScrollPane2.setViewportView(errorTable);

        jScrollPane3.setViewportView(jScrollPane2);

        javax.swing.GroupLayout logPanelLayout = new javax.swing.GroupLayout(logPanel);
        logPanel.setLayout(logPanelLayout);
        logPanelLayout.setHorizontalGroup(
            logPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logPanelLayout.createSequentialGroup()
                .addGroup(logPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(logPanelLayout.createSequentialGroup()
                        .addGap(309, 309, 309)
                        .addComponent(jLabel1))
                    .addGroup(logPanelLayout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1360, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(106, Short.MAX_VALUE))
        );
        logPanelLayout.setVerticalGroup(
            logPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logPanelLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel1)
                .addGap(33, 33, 33)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(88, Short.MAX_VALUE))
        );

        tabbed.addTab("Log", logPanel);

        playerPanel.setBackground(new java.awt.Color(24, 26, 31));
        playerPanel.setPreferredSize(new java.awt.Dimension(1500, 850));

        jLabel2.setBackground(new java.awt.Color(24, 26, 31));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Biblioteca");
        jLabel2.setToolTipText("");

        musicList.setBackground(new java.awt.Color(40, 44, 53));
        musicList.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        musicList.setForeground(new java.awt.Color(40, 180, 99));
        musicList.setToolTipText("");
        musicList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                musicListMouseClicked(evt);
            }
        });
        scrollMusicList.setViewportView(musicList);

        playButton.setBackground(new java.awt.Color(40, 180, 99));
        playButton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        playButton.setForeground(new java.awt.Color(24, 26, 31));
        playButton.setText("Play");
        playButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playButtonActionPerformed(evt);
            }
        });

        playButton1.setBackground(new java.awt.Color(40, 180, 99));
        playButton1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        playButton1.setForeground(new java.awt.Color(24, 26, 31));
        playButton1.setText("Edit");
        playButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playButton1ActionPerformed(evt);
            }
        });

        playButton2.setBackground(new java.awt.Color(40, 180, 99));
        playButton2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        playButton2.setForeground(new java.awt.Color(24, 26, 31));
        playButton2.setText("Delete");
        playButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playButton2ActionPerformed(evt);
            }
        });

        playButton3.setBackground(new java.awt.Color(40, 180, 99));
        playButton3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        playButton3.setForeground(new java.awt.Color(24, 26, 31));
        playButton3.setText("Add");
        playButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playButton3ActionPerformed(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(24, 26, 31));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Pistas");
        jLabel3.setToolTipText("");

        playList.setBackground(new java.awt.Color(40, 44, 53));
        playList.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        playList.setForeground(new java.awt.Color(40, 180, 99));
        playList.setToolTipText("");
        playList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                playListMouseClicked(evt);
            }
        });
        scrollMusicList1.setViewportView(playList);

        jLabel4.setBackground(new java.awt.Color(24, 26, 31));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Listas");
        jLabel4.setToolTipText("");

        songsList.setBackground(new java.awt.Color(40, 44, 53));
        songsList.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        songsList.setForeground(new java.awt.Color(40, 180, 99));
        songsList.setToolTipText("");
        songsList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                songsListMouseClicked(evt);
            }
        });
        scrollMusicList2.setViewportView(songsList);

        playButton4.setBackground(new java.awt.Color(40, 180, 99));
        playButton4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        playButton4.setForeground(new java.awt.Color(24, 26, 31));
        playButton4.setText("Delete");
        playButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playButton4ActionPerformed(evt);
            }
        });

        playButton5.setBackground(new java.awt.Color(40, 180, 99));
        playButton5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        playButton5.setForeground(new java.awt.Color(24, 26, 31));
        playButton5.setText("New");
        playButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playButton5ActionPerformed(evt);
            }
        });

        progressBar.setBackground(new java.awt.Color(40, 44, 53));
        progressBar.setForeground(new java.awt.Color(9, 177, 80));

        playPause.setBackground(new java.awt.Color(40, 180, 99));
        playPause.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        playPause.setForeground(new java.awt.Color(24, 26, 31));
        playPause.setText("Play");
        playPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playPauseActionPerformed(evt);
            }
        });

        repeatCheck.setBackground(new java.awt.Color(24, 26, 31));
        repeatCheck.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        repeatCheck.setForeground(new java.awt.Color(255, 255, 255));
        repeatCheck.setText("Repeat");

        songLabel.setBackground(new java.awt.Color(24, 26, 31));
        songLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        songLabel.setForeground(new java.awt.Color(255, 255, 255));
        songLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        songLabel.setText("Play a Song");
        songLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout playerPanelLayout = new javax.swing.GroupLayout(playerPanel);
        playerPanel.setLayout(playerPanelLayout);
        playerPanelLayout.setHorizontalGroup(
            playerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(playerPanelLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(playerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(playerPanelLayout.createSequentialGroup()
                        .addComponent(playButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(playButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(playButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(playButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scrollMusicList))
                .addGap(18, 18, 18)
                .addGroup(playerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                    .addComponent(scrollMusicList1, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                    .addComponent(scrollMusicList2)
                    .addGroup(playerPanelLayout.createSequentialGroup()
                        .addComponent(playButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(playButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(playerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(playerPanelLayout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(playerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
                            .addComponent(songLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(41, 41, 41))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, playerPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(playPause, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(repeatCheck)
                        .addGap(199, 199, 199))))
            .addGroup(playerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(playerPanelLayout.createSequentialGroup()
                    .addGap(457, 457, 457)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                    .addGap(684, 684, 684)))
        );
        playerPanelLayout.setVerticalGroup(
            playerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(playerPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addGroup(playerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollMusicList, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, playerPanelLayout.createSequentialGroup()
                        .addComponent(scrollMusicList1, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(playerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, playerPanelLayout.createSequentialGroup()
                                .addGroup(playerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(playButton4)
                                    .addComponent(playButton5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scrollMusicList2, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, playerPanelLayout.createSequentialGroup()
                                .addComponent(songLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(playerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(playPause, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(repeatCheck))
                                .addGap(18, 18, 18)
                                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(playerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(playButton)
                    .addComponent(playButton1)
                    .addComponent(playButton2)
                    .addComponent(playButton3))
                .addGap(76, 76, 76))
            .addGroup(playerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(playerPanelLayout.createSequentialGroup()
                    .addGap(43, 43, 43)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(710, Short.MAX_VALUE)))
        );

        tabbed.addTab("Reproductor", playerPanel);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabbed, javax.swing.GroupLayout.DEFAULT_SIZE, 1539, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(tabbed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jMenuBar1.setBackground(new java.awt.Color(24, 26, 31));
        jMenuBar1.setForeground(new java.awt.Color(0, 0, 255));
        jMenuBar1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jMenu1.setBackground(new java.awt.Color(24, 26, 31));
        jMenu1.setForeground(new java.awt.Color(255, 255, 255));
        jMenu1.setText("File");
        jMenu1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        new_Item.setBackground(new java.awt.Color(24, 26, 31));
        new_Item.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        new_Item.setForeground(new java.awt.Color(255, 255, 255));
        new_Item.setText("Nuevo...");
        new_Item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                new_ItemActionPerformed(evt);
            }
        });
        jMenu1.add(new_Item);

        open_item.setBackground(new java.awt.Color(24, 26, 31));
        open_item.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        open_item.setForeground(new java.awt.Color(255, 255, 255));
        open_item.setText("Open...");
        open_item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                open_itemActionPerformed(evt);
            }
        });
        jMenu1.add(open_item);

        save_Item.setBackground(new java.awt.Color(24, 26, 31));
        save_Item.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        save_Item.setForeground(new java.awt.Color(255, 255, 255));
        save_Item.setText("Guardar...");
        save_Item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                save_ItemActionPerformed(evt);
            }
        });
        jMenu1.add(save_Item);

        saveAs_Item.setBackground(new java.awt.Color(24, 26, 31));
        saveAs_Item.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        saveAs_Item.setForeground(new java.awt.Color(255, 255, 255));
        saveAs_Item.setText("Guardar como...");
        saveAs_Item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAs_ItemActionPerformed(evt);
            }
        });
        jMenu1.add(saveAs_Item);

        exit_Item.setBackground(new java.awt.Color(24, 26, 31));
        exit_Item.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        exit_Item.setForeground(new java.awt.Color(255, 255, 255));
        exit_Item.setText("Salir");
        exit_Item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exit_ItemActionPerformed(evt);
            }
        });
        jMenu1.add(exit_Item);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1551, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void setTableErrors(List<Err> errors) {
        String[][] list = new String[errors.size()][5];
        for (int i = 0; i < errors.size(); i++) {
            list[i][0] = errors.get(i).getType().name();
            list[i][1] = errors.get(i).getLexema();
            list[i][2] = String.valueOf(errors.get(i).getLine());
            list[i][3] = String.valueOf(errors.get(i).getColumn());
            list[i][4] = errors.get(i).getDescription();
        }

        errorTable.setPreferredSize(new java.awt.Dimension(3500, 40 * errors.size()));
        errorTable.setModel(new javax.swing.table.DefaultTableModel(
                list,
                new String[]{
                    "Tipo", "Lexema", "Linea", "Columna", "Descripcion"
                }
        ));
        setSizeTable();
    }

    private void setSizeTable() {
        errorTable.getColumnModel().getColumn(0).setMinWidth(175);
        errorTable.getColumnModel().getColumn(0).setPreferredWidth(175);
        errorTable.getColumnModel().getColumn(0).setMaxWidth(175);
        for (int i = 1; i < 4; i++) {
            errorTable.getColumnModel().getColumn(i).setMinWidth(100);
            errorTable.getColumnModel().getColumn(i).setPreferredWidth(100);
            errorTable.getColumnModel().getColumn(i).setMaxWidth(100);
        }
        errorTable.getColumnModel().getColumn(4).setMinWidth(3025);
        errorTable.getColumnModel().getColumn(4).setPreferredWidth(3025);
        errorTable.getColumnModel().getColumn(4).setMaxWidth(3025);
    }

    private void open_itemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_open_itemActionPerformed
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
        int seleccion = fileChooser.showOpenDialog(this);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            this.path = fileChooser.getSelectedFile().getAbsolutePath();
            String text = file.readData(this.path);
            editor.setText(text);
        }

        tabbed.setSelectedIndex(0);
    }//GEN-LAST:event_open_itemActionPerformed

    private void saveAs_ItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAs_ItemActionPerformed
        // TODO add your handling code here:
        saveFile();
    }//GEN-LAST:event_saveAs_ItemActionPerformed

    private void save_ItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_save_ItemActionPerformed
        // TODO add your handling code here:
        if (this.path != null) {
            String text = editor.getText();
            File archivo = new File(this.path);
            this.file.writeFile(archivo, text);
        } else {
            saveFile();
        }
    }//GEN-LAST:event_save_ItemActionPerformed

    private void exit_ItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exit_ItemActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_exit_ItemActionPerformed

    private void new_ItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_new_ItemActionPerformed
        // TODO add your handling code here:
        if (this.path != null) {
            int input = JOptionPane.showConfirmDialog(this, "Desear guardar los cambios?");
            if (input == 0) {
                String text = editor.getText();
                File archivo = new File(this.path);
                file.writeFile(archivo, text);
            } else if (input == 1) {
                this.path = null;
                editor.setText("");
            }
        } else {
            String txt = editor.getText();
            if (!txt.isEmpty()) {
                int input = JOptionPane.showConfirmDialog(this, "Desea guardar los cambios?");
                if (input == 0) {
                    JFileChooser save = new JFileChooser();
                    int sel = save.showSaveDialog(this);
                    if (sel == JFileChooser.APPROVE_OPTION) {
                        File archivo = save.getSelectedFile();

                        this.path = archivo.getAbsolutePath();
                        String tx = editor.getText();

                        file.writeFile(archivo, tx);

                        this.path = null;
                        editor.setText("");
                    }
                } else if (input == 1) {
                    //this.path = null;
                    editor.setText("");
                }
            } else {
                editor.setText("");
            }
        }
    }//GEN-LAST:event_new_ItemActionPerformed

    private void editorCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_editorCaretUpdate
        // TODO add your handling code here:
        String info = file.lineAndColumnInfo(editor);
        this.label_caret.setText(info);
    }//GEN-LAST:event_editorCaretUpdate

    private void compileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_compileButtonActionPerformed
        // TODO add your handling code here:
        log.setText("");
        saveTrack.setEnabled(false);

        control.parse(editor.getText(), this);
    }//GEN-LAST:event_compileButtonActionPerformed

    private void saveTrackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveTrackActionPerformed
        // Guardar pista
        control.saveTrack(this);

        // Desactivar boton
        saveTrack.setEnabled(false);
    }//GEN-LAST:event_saveTrackActionPerformed

    private void tabbedStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabbedStateChanged
        // TODO add your handling code here:
        control.updateSongs(this);
    }//GEN-LAST:event_tabbedStateChanged

    private void musicListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_musicListMouseClicked
        // TODO add your handling code here:
        JList list = (JList) evt.getSource();
        if (evt.getClickCount() == 2) {
            int index = list.locationToIndex(evt.getPoint());
            control.playSong(index, this);
        }
    }//GEN-LAST:event_musicListMouseClicked

    private void playButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playButtonActionPerformed
        // control.play();
    }//GEN-LAST:event_playButtonActionPerformed

    private void playButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_playButton1ActionPerformed

    private void playButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_playButton2ActionPerformed

    private void playButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_playButton3ActionPerformed

    // Para actualizar listas de reproduccion
    private void playListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playListMouseClicked
        JList list = (JList) evt.getSource();
        if(evt.getClickCount() == 2) {
            int index = list.locationToIndex(evt.getPoint());
            control.updateSongsList(index, this);
        }
    }//GEN-LAST:event_playListMouseClicked

    private void songsListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_songsListMouseClicked
        JList list = (JList) evt.getSource();
        if(evt.getClickCount() == 2) {
            int index = list.locationToIndex(evt.getPoint());
            control.playPlayList(index, this);
        }
    }//GEN-LAST:event_songsListMouseClicked
    
    private void playButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_playButton4ActionPerformed

    private void playButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playButton5ActionPerformed
    }//GEN-LAST:event_playButton5ActionPerformed

    private void playPauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playPauseActionPerformed
        control.play(this);
    }//GEN-LAST:event_playPauseActionPerformed

    /**
     * Guardar como
     */
    private void saveFile() {
        JFileChooser fileChooser = new JFileChooser();
        int selection = fileChooser.showSaveDialog(this);

        if (selection == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();

            this.path = archivo.getAbsolutePath();
            String txt = editor.getText();

            this.file.writeFile(archivo, txt);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton compileButton;
    private javax.swing.JTextArea editor;
    private javax.swing.JPanel editorPanel;
    public javax.swing.JTable errorTable;
    private javax.swing.JMenuItem exit_Item;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel label_caret;
    public javax.swing.JTextArea log;
    private javax.swing.JPanel logPanel;
    public javax.swing.JList<String> musicList;
    private javax.swing.JMenuItem new_Item;
    private javax.swing.JMenuItem open_item;
    private javax.swing.JButton playButton;
    private javax.swing.JButton playButton1;
    private javax.swing.JButton playButton2;
    private javax.swing.JButton playButton3;
    private javax.swing.JButton playButton4;
    private javax.swing.JButton playButton5;
    public javax.swing.JList<String> playList;
    public javax.swing.JButton playPause;
    private javax.swing.JPanel playerPanel;
    public javax.swing.JProgressBar progressBar;
    public javax.swing.JCheckBox repeatCheck;
    private javax.swing.JMenuItem saveAs_Item;
    public javax.swing.JButton saveTrack;
    private javax.swing.JMenuItem save_Item;
    private javax.swing.JScrollPane scroll;
    private javax.swing.JScrollPane scrollMusicList;
    private javax.swing.JScrollPane scrollMusicList1;
    private javax.swing.JScrollPane scrollMusicList2;
    public javax.swing.JLabel songLabel;
    public javax.swing.JList<String> songsList;
    public javax.swing.JTabbedPane tabbed;
    public javax.swing.JComboBox<String> typeCombo;
    // End of variables declaration//GEN-END:variables
}
