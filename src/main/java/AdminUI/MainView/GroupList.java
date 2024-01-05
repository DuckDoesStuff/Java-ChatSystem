package AdminUI.MainView;

import User.GroupChat;
import User.UserModel;
import User.UserService;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;

public class GroupList extends javax.swing.JFrame {

    private UserService userService;
    /**
     * Creates new form GroupList
     */
    public GroupList(Connection conn) {
        userService = new UserService(conn);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {
        ArrayList<GroupChat> allGroups = userService.getAllGroupChat("nameasc");

        title = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableOfGroups = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        membersOfSelectedGroup = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        adminOfSelectedGroup = new javax.swing.JTable();
        sortModeCombobox = new javax.swing.JComboBox<>();
        searchBar = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        backToMainMenuBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(815, 570));

        title.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        title.setText("List of user Groups");

        tableOfGroups.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "Group's name", "Creation time"
                }
        ) {
            Class[] types = new Class [] {
                    java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tableOfGroups);
        tableOfGroups.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                // Get the selected row and perform actions (e.g., update another table)
                int selectedRow = tableOfGroups.getSelectedRow();
                if (selectedRow != -1) {
                    Object name = tableOfGroups.getValueAt(selectedRow, 0);
                    Object createdTime = tableOfGroups.getValueAt(selectedRow, 1);
                    ArrayList<UserModel> members = userService.MemberGroupChat(name.toString(), Timestamp.valueOf(createdTime.toString()));
                    // Example: Update another table or perform other actions
                    DefaultTableModel model = (DefaultTableModel) membersOfSelectedGroup.getModel();
                    model.setRowCount(0);
                    for (UserModel member : members) {
                        model.addRow(new Object[]{member.getUsername()});
                    }
                    ArrayList<UserModel> admins = userService.AdminGroupChat(name.toString(), Timestamp.valueOf(createdTime.toString()));
                    DefaultTableModel model1 = (DefaultTableModel) adminOfSelectedGroup.getModel();
                    model1.setRowCount(0);
                    for (UserModel admin : admins) {
                        model1.addRow(new Object[]{admin.getUsername()});
                    }
                }
            }
        });
        membersOfSelectedGroup.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "Members"
                }
        ) {
            Class[] types = new Class [] {
                    java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(membersOfSelectedGroup);
        if (membersOfSelectedGroup.getColumnModel().getColumnCount() > 0) {
            membersOfSelectedGroup.getColumnModel().getColumn(0).setResizable(false);
        }

        adminOfSelectedGroup.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "Administrator"
                }
        ) {
            Class[] types = new Class [] {
                    java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                    false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(adminOfSelectedGroup);
        if (adminOfSelectedGroup.getColumnModel().getColumnCount() > 0) {
            adminOfSelectedGroup.getColumnModel().getColumn(0).setResizable(false);
        }

        sortModeCombobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Name: A-Z", "Name: Z-A", "Creation Time: oldest to newest", "Creation Time: newest to oldest" }));
        sortModeCombobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sortModeComboboxActionPerformed(evt);
            }
        });

        searchBar.setText("Search...");
        searchBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBarActionPerformed(evt);
            }
        });

        searchButton.setText("Search");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        backToMainMenuBtn.setText("Back To Main Menu");
        backToMainMenuBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backToMainMenuBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(backToMainMenuBtn)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(title)
                                                        .addGap(29, 29, 29)
                                                        .addComponent(searchBar, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(searchButton)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(sortModeCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(49, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(searchBar, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(searchButton)
                                                .addComponent(sortModeCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(title))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(backToMainMenuBtn))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>

    private void sortModeComboboxActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        String selectedItem = (String) sortModeCombobox.getSelectedItem();
        if (selectedItem.equals("Name: A-Z")) {
            clearTableOfGroup();
            ArrayList<GroupChat> allGroups = userService.getAllGroupChat("nameasc");
            for (GroupChat groupChat : allGroups) {
                addRowForGroupTable(groupChat.getName(), groupChat.getTimeCreated());
            }
        } else if (selectedItem.equals("Name: Z-A")) {
            clearTableOfGroup();
            ArrayList<GroupChat> allGroups = userService.getAllGroupChat("namedes");
            for (GroupChat groupChat : allGroups) {
                addRowForGroupTable(groupChat.getName(), groupChat.getTimeCreated());
            }
        } else if (selectedItem.equals("Creation Time: oldest to newest")) {
            clearTableOfGroup();
            ArrayList<GroupChat> allGroups = userService.getAllGroupChat("timeasc");
            for (GroupChat groupChat : allGroups) {
                addRowForGroupTable(groupChat.getName(), groupChat.getTimeCreated());
            }
        } else if (selectedItem.equals("Creation Time: newest to oldest")) {
            clearTableOfGroup();
            ArrayList<GroupChat> allGroups = userService.getAllGroupChat("timedes");
            for (GroupChat groupChat : allGroups) {
                addRowForGroupTable(groupChat.getName(), groupChat.getTimeCreated());
            }
        }
    }

    private void searchBarActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        String keyword = searchBar.getText();
        ArrayList<GroupChat> allGroups = userService.filterGroupChat(keyword);
        clearTableOfGroup();
        for (GroupChat groupChat : allGroups) {
            addRowForGroupTable(groupChat.getName(), groupChat.getTimeCreated());
        }
    }

    private void backToMainMenuBtnActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        dispose();
        new MainMenu().setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(GroupList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GroupList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GroupList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GroupList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */

    }

    private void addRowForGroupTable(String name, Timestamp createdTime) {
        DefaultTableModel model = (DefaultTableModel) tableOfGroups.getModel();
        model.addRow(new Object[]{name, createdTime});
    }

    private void clearTableOfGroup() {
        DefaultTableModel model = (DefaultTableModel) tableOfGroups.getModel();
        model.setRowCount(0);
    }

    // Variables declaration - do not modify
    private javax.swing.JTable adminOfSelectedGroup;
    private javax.swing.JButton backToMainMenuBtn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable membersOfSelectedGroup;
    private javax.swing.JTextField searchBar;
    private javax.swing.JButton searchButton;
    private javax.swing.JComboBox<String> sortModeCombobox;
    private javax.swing.JTable tableOfGroups;
    private javax.swing.JLabel title;
    // End of variables declaration
}
