







package Final;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

public class MainMenu extends javax.swing.JFrame {
    private AnalizadorLexico analizadorLexico;
    AnalizadorSintactico analizadorSintactico;
    
    public MainMenu() {
        initComponents();
        inicializar();
        analizadorLexico = new AnalizadorLexico();
        
        jTextAreaEntrada.setText(
        		"float a, b;\n"+
        		"int aux;\n\n"+
        				
        		"int sumar(int a, int b){\n"+
        		"     int res;\n"+
        		"     res = a + b;\n"+
        		"     return res;\n"+
        		"}\n\n"+
        		
				"void sumatoria(int limite){\n"+
				"     int i, res;\n"+
				"     i = 0;\n"+
				"     while(i<10){\n"+
				"          res = res + i;\n"+
				"     }\n"+
				""+
				"}\n\n"+
				
        		"int main(){\n"+
        		"     int a, b;\n"+
        		"     a = 4;\n"+
        		"     b = a + 4 - 5 + 6 * (4 + 3);\n"+
        		"     if(a >= 5){\n"+
        		"          aux = sumar(4, 9);\n"+
        		"     }\n"+
        		"     else{\n"+
        		"          sumatoria(10);\n"+
        		"          aux = sumar(43, a) + 9;\n"+
        		"     }\n"+
        		/**/
        		"}");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaEntrada = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaSalida = new javax.swing.JTextArea();
        jButtonEjecutar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableResultados = new javax.swing.JTable();
        jLabelValido = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextAreaEntrada.setColumns(20);
        jTextAreaEntrada.setRows(5);
        jScrollPane1.setViewportView(jTextAreaEntrada);

        jTextAreaSalida.setColumns(20);
        jTextAreaSalida.setRows(5);
        jScrollPane2.setViewportView(jTextAreaSalida);

        jButtonEjecutar.setText("Ejecutar");
        jButtonEjecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEjecutarActionPerformed(evt);
            }
        });

        jTableResultados.setAutoCreateRowSorter(true);
        jTableResultados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Token", "Lexema", "Numero"
            }
        ));
        jScrollPane3.setViewportView(jTableResultados);

        jLabelValido.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(65, 65, 65)
                                .addComponent(jLabelValido)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(jButtonEjecutar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelValido)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                            .addComponent(jScrollPane2)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jButtonEjecutar)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inicializar(){
        jLabelValido.setText("");
    }
    
    private void jButtonEjecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEjecutarActionPerformed
        boolean codigoValido;
        
        //Analizador lexico
        analizadorLexico.fijaCadena(jTextAreaEntrada.getText());
        analizadorLexico.analizarCadena();
        
        //Analizafor Sintactico
        analizadorSintactico = new AnalizadorSintactico(analizadorLexico.dameNumeros(), 
                analizadorLexico.dameTokens(),
                analizadorLexico.dameLexemas());
        //codigoValido = analizadorSintactico.esCadenaValida();
        codigoValido = analizadorSintactico.esCadenaValidaV2();
        
        //mostrar resultados
        //Analizador lexico
        jTableResultados.setModel(new javax.swing.table.DefaultTableModel(
                analizadorLexico.dameMatriz(),
                new String [] {
                    "Token", "Lexema", "Numero"
                }
        ));
        
        //Analizador Sintactico
        if(codigoValido){
            jLabelValido.setText("Codigo valido");
            jLabelValido.setForeground(Color.GREEN);
            
            List<String[]> tablaSemantica = analizadorSintactico.dameMatriz();
            List<String[]> errores = analizadorSintactico.dameErrores();
            String s = "";
            
            //Mostrar resultados de analizador semantico
            /*
            jTextAreaSalida.setText("");//limpiar 
            
            for(String[] row: tablaSemantica) {
            	s += Arrays.toString(row) + "\n";
            }
            
            s += "\n ********** Errores **********\n";
            for(String[] row: errores) {
            	s += Arrays.toString(row) + "\n";
            }
            
            jTextAreaSalida.setText(s);*/
        }
        else{
            jLabelValido.setText("Codigo NO valido");
            jLabelValido.setForeground(Color.RED);
        }
    }//GEN-LAST:event_jButtonEjecutarActionPerformed
    
    
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonEjecutar;
    private javax.swing.JLabel jLabelValido;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTableResultados;
    private javax.swing.JTextArea jTextAreaEntrada;
    private javax.swing.JTextArea jTextAreaSalida;
    // End of variables declaration//GEN-END:variables
}
