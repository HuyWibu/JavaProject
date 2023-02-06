/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import controller.IOFile;
import controller.ValidException;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.BangChamCong;
import model.CongNhan;
import model.Xuong;

/**
 *
 * @author Admin
 */
public class Main extends javax.swing.JFrame {

    private List<CongNhan> listCN;
    private List<Xuong> listX;
    private List<BangChamCong> listBCC;
    private DefaultTableModel tmCN, tmX, tmBCC;
    private String fCN, fX, fBCC;
    
    
    public Main() {
        
        initComponents();
        // cong nhan
        fCN = "src/controller/CN.dat";
        if (new File(fCN).exists()) {
            listCN = IOFile.doc(fCN);
        } else {
            listCN = new ArrayList<>();
        }
        tmCN = (DefaultTableModel) tbCN.getModel();
        docCN();
        setButtonCN(true);
        // viet cho cac button
        eventCN();

        // xuong san xuat
        fX = "src/controller/Xuong.dat";
        if (new File(fX).exists()) {
            listX = IOFile.doc(fX);
        } else {
            listX = new ArrayList<>();
        }
        tmX = (DefaultTableModel) tbX.getModel();
        setButtonX(true);
        docX();
        evenX();

        // bang cham cong
        fBCC = "src/controller/BangChamCong.dat";
        if (new File(fBCC).exists()) {
            listBCC = IOFile.doc(fBCC);
        } else {
            listBCC = new ArrayList<>();
        }
        tmBCC = (DefaultTableModel) tbBCC.getModel();
        setButtonBCC(true);
        docBCC();
        evenBCC();
    }

    public void docCN() {
        tmCN.setRowCount(0);
        for (CongNhan x : listCN) {
            tmCN.addRow(x.toObject());
        }
    }

    public void docX() {
        tmX.setRowCount(0);
        for (Xuong x : listX) {
            tmX.addRow(x.toObject());
        }
    }

    public void docBCC() {
        tmBCC.setRowCount(0);
        for (BangChamCong x : listBCC) {
            tmBCC.addRow(x.toObject());
        }
    }

    public void setButtonCN(boolean b) {
        themCNBT.setEnabled(b);
        capnhatCNBT.setEnabled(!b);
        boquaCNBT.setEnabled(!b);
    }

    public void setButtonX(boolean b) {
        themXBT.setEnabled(b);
        capnhatXBT.setEnabled(!b);
        boquaXBT.setEnabled(!b);
    }

    public void setButtonBCC(boolean b) {
        themCCBT.setEnabled(b);
    }

    private void eventCN() {
        themCNBT.addActionListener(e -> {
            maCN.setText("");
            tenCN.setText("");
            dchiCN.setText("");
            dthoaiCN.setText("");
            maCN.setText(1000 + listCN.size() + "");
            tenCN.requestFocus();
            setButtonCN(false);
        });

        capnhatCNBT.addActionListener(e -> {
            try {
                if (!dthoaiCN.getText().matches("0\\d{9}")) {
                    throw new ValidException("Nhap dung dinh dang so dien thoai!!");
                }
                CongNhan c = new CongNhan(
                        maCN.getText(),
                        tenCN.getText(),
                        dchiCN.getText(),
                        dthoaiCN.getText(),
                        Integer.parseInt(bacCN.getSelectedItem().toString()));
                listCN.add(c);
                tmCN.addRow(c.toObject());
                setButtonCN(true);
            } catch (ValidException ex) {
                JOptionPane.showMessageDialog(this, ex);
                dthoaiCN.setText("");
                dthoaiCN.requestFocus();
            }
        });

        xoaCNBT.addActionListener(e -> {
            int row = tbCN.getSelectedRow();
            if (row >= 0 && row < tbCN.getRowCount()) {
                listCN.remove(row);
                tmCN.removeRow(row);
            } else {
                JOptionPane.showMessageDialog(this, "Chon dong de xoa!!");
            }
        });

        luuCNBT.addActionListener(e -> {
            IOFile.ghi(fCN, listCN);
            JOptionPane.showMessageDialog(this, "Luu file thanh cong!!");
        });

        boquaCNBT.addActionListener(e -> {
            maCN.setText("");
            tenCN.setText("");
            dchiCN.setText("");
            dthoaiCN.setText("");
            setButtonCN(true);
        });
    }

    public void evenX() {
        themXBT.addActionListener(e -> {
            maX.setText("");
            tenX.setText("");
            maX.setText(100 + listX.size() + "");
            tenX.requestFocus();
            setButtonX(false);
        });

        capnhatXBT.addActionListener(e -> {
            Xuong x = new Xuong(
                    maX.getText(),
                    tenX.getText(),
                    Integer.parseInt(hesoX.getSelectedItem().toString())
            );
            setButtonX(true);
            listX.add(x);
            tmX.addRow(x.toObject());
        });

        boquaXBT.addActionListener(e -> {
            maX.setText("");
            tenX.setText("");
            setButtonX(true);
        });

        suaXBT.addActionListener(e -> {
            int row = tbX.getSelectedRow();
            if (row >= 0 && row < listX.size()) {
                Xuong x = new Xuong(
                        maX.getText(),
                        tenX.getText(),
                        Integer.parseInt(hesoX.getSelectedItem().toString())
                );
                listX.set(row, x);
                tmX.removeRow(row);
                tmX.insertRow(row, x.toObject());
            } else {
                JOptionPane.showMessageDialog(this, "Chon dong de sua!!");
            }
        });

        luuXBT.addActionListener(e -> {
            IOFile.ghi(fX, listX);
        });
    }

    public int getSoNgay(String maCN) {
        int res = 0;
        for (BangChamCong x : listBCC) {
            if (x.getCongNhan().getMaCN().equals(maCN)) {
                res += x.getSoNgay();
            }
        }
        return res;
    }

    public CongNhan getCongNhan(String maCN) {
        for (CongNhan x : listCN) {
            if (x.getMaCN().equals(maCN)) {
                return x;
            }
        }
        return null;
    }

    public Xuong getXuong(String maXuong) {
        for (Xuong x : listX) {
            if (x.getMaX().equals(maXuong)) {
                return x;
            }
        }
        return null;
    }

    public void evenBCC() {
        loadCCBT.addActionListener(e -> {
            maCNCB.removeAllItems();
            for (CongNhan x : listCN) {
                maCNCB.addItem(x.getMaCN());
            }
            maXCB.removeAllItems();
            for (Xuong x : listX) {
                maXCB.addItem(x.getMaX());
            }
        });

        themCCBT.addActionListener(e -> {
            String maCN = maCNCB.getSelectedItem().toString();
            String maX = maXCB.getSelectedItem().toString();
            int ngay = 0;
            try{
                ngay = Integer.parseInt(songayCC.getText());
            } catch(NumberFormatException ex){
                System.out.println(e);
            }
            
            try {
                if (getSoNgay(maCN) + ngay > 31) {
                    throw new ValidException("So ngay lam viec phai nho hon 31!!");
                } else {
                    BangChamCong x = new BangChamCong(getCongNhan(maCN),
                            getXuong(maX), ngay);
                    listBCC.add(x);
                    tmBCC.addRow(x.toObject());
                }
            } catch (ValidException ex) {
                JOptionPane.showMessageDialog(this, ex);
                songayCC.setText("");
                songayCC.requestFocus();
            }
        });
        
        luuCCBT.addActionListener(e -> {
            IOFile.ghi(fBCC, listBCC);
        });
        
        sapxepCCBT.addActionListener(e -> {
            int index = sapxepCB.getSelectedIndex();
            if(index == 0){
                listBCC.sort(new Comparator<BangChamCong>(){
                    @Override
                    public int compare(BangChamCong o1, BangChamCong o2) {
                        String ten1 = o1.getCongNhan().getHoTen().substring(o1.getCongNhan().getHoTen().lastIndexOf(" ") + 1)
                                + o1.getCongNhan().getHoTen();
                        String ten2 = o2.getCongNhan().getHoTen().substring(o2.getCongNhan().getHoTen().lastIndexOf(" ") + 1)
                                + o2.getCongNhan().getHoTen();
                        return ten1.compareTo(ten2);
                    }
                });
            }
            else{
                listBCC.sort(new Comparator<BangChamCong>(){
                    @Override
                    public int compare(BangChamCong o1, BangChamCong o2) {
                        return o1.getSoNgay() - o2.getSoNgay();
                    }
                });
            }
            tmBCC.setRowCount(0);
            for(BangChamCong x : listBCC){
                tmBCC.addRow(x.toObject());
            }
        });
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbCN = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tenCN = new javax.swing.JTextField();
        dchiCN = new javax.swing.JTextField();
        maCN = new javax.swing.JTextField();
        dthoaiCN = new javax.swing.JTextField();
        bacCN = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        themCNBT = new javax.swing.JButton();
        capnhatCNBT = new javax.swing.JButton();
        boquaCNBT = new javax.swing.JButton();
        xoaCNBT = new javax.swing.JButton();
        luuCNBT = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbX = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        maX = new javax.swing.JTextField();
        tenX = new javax.swing.JTextField();
        hesoX = new javax.swing.JComboBox<>();
        jPanel7 = new javax.swing.JPanel();
        themXBT = new javax.swing.JButton();
        capnhatXBT = new javax.swing.JButton();
        boquaXBT = new javax.swing.JButton();
        suaXBT = new javax.swing.JButton();
        luuXBT = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbBCC = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        themCCBT = new javax.swing.JButton();
        luuCCBT = new javax.swing.JButton();
        sapxepCCBT = new javax.swing.JButton();
        loadCCBT = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        maCNCB = new javax.swing.JComboBox<>();
        maXCB = new javax.swing.JComboBox<>();
        songayCC = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        sapxepCB = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tbCN.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Ma", "Ho va ten", "Dia chi", "Dien thoai", "Bac"
            }
        ));
        jScrollPane1.setViewportView(tbCN);

        jLabel1.setText("Ma:");

        jLabel2.setText("Ho va ten:");

        jLabel3.setText("Dia chi:");

        jLabel4.setText("Dien thoai");

        jLabel5.setText("Bac:");

        bacCN.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7" }));
        bacCN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bacCNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 164, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(maCN, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tenCN, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dchiCN, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                            .addComponent(dthoaiCN, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                            .addComponent(bacCN, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(maCN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tenCN, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(dchiCN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(dthoaiCN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(bacCN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        themCNBT.setText("Them moi");

        capnhatCNBT.setText("Cap nhat");

        boquaCNBT.setText("Bo qua");

        xoaCNBT.setText("Xoa");

        luuCNBT.setText("Luu file");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(themCNBT, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                    .addComponent(capnhatCNBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(boquaCNBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(xoaCNBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(luuCNBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(119, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(themCNBT)
                .addGap(42, 42, 42)
                .addComponent(capnhatCNBT)
                .addGap(42, 42, 42)
                .addComponent(boquaCNBT)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addComponent(xoaCNBT)
                .addGap(42, 42, 42)
                .addComponent(luuCNBT)
                .addGap(54, 54, 54))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 950, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(86, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Cong Nhan", jPanel1);

        tbX.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Ma xuong", "Ten xuong", "He so"
            }
        ));
        tbX.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbXMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbX);

        jLabel6.setText("Ma xuong:");

        jLabel7.setText("Ten xuong:");

        jLabel8.setText("He so:");

        maX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maXActionPerformed(evt);
            }
        });

        hesoX.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(130, 130, 130)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(maX)
                    .addComponent(tenX)
                    .addComponent(hesoX, 0, 284, Short.MAX_VALUE))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(maX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(80, 80, 80)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(tenX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(hesoX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(79, 79, 79))
        );

        themXBT.setText("Them moi");
        themXBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                themXBTActionPerformed(evt);
            }
        });

        capnhatXBT.setText("Cap nhat");

        boquaXBT.setText("Bo qua");

        suaXBT.setText("Sua");

        luuXBT.setText("Luu file");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(themXBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(capnhatXBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(boquaXBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(suaXBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(luuXBT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(229, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(themXBT)
                .addGap(29, 29, 29)
                .addComponent(capnhatXBT)
                .addGap(36, 36, 36)
                .addComponent(boquaXBT)
                .addGap(40, 40, 40)
                .addComponent(suaXBT)
                .addGap(48, 48, 48)
                .addComponent(luuXBT)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1003, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(897, 897, 897))
        );

        jTabbedPane1.addTab("Xuong", jPanel3);

        tbBCC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Ma cong nhan", "Ho va ten ", "Ma xuong", "So ngay lam viec"
            }
        ));
        jScrollPane3.setViewportView(tbBCC);

        themCCBT.setText("Them moi");

        luuCCBT.setText("Luu file");

        sapxepCCBT.setText("Sap xep");

        loadCCBT.setText("Loading ");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(sapxepCCBT, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(luuCCBT, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(themCCBT)
                    .addComponent(loadCCBT, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(224, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(themCCBT)
                .addGap(66, 66, 66)
                .addComponent(luuCCBT)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(loadCCBT)
                .addGap(49, 49, 49)
                .addComponent(sapxepCCBT)
                .addGap(46, 46, 46))
        );

        jLabel10.setText("Ma cong nhan:");

        jLabel13.setText("Ma xuong:");

        jLabel14.setText("So ngay lam viec:");

        maCNCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        maXCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        songayCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                songayCCActionPerformed(evt);
            }
        });

        jLabel9.setText("Sap xep:");

        sapxepCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ho va ten cong nhan", "So ngay lam viec" }));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 202, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(maCNCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(maXCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(songayCC)
                    .addComponent(sapxepCB, 0, 270, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(maCNCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(maXCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(songayCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(69, 69, 69)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(sapxepCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 843, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Bang cham cong", jPanel6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1042, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 756, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void bacCNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bacCNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bacCNActionPerformed

    private void maXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maXActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_maXActionPerformed

    private void themXBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_themXBTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_themXBTActionPerformed

    private void tbXMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbXMouseClicked
        // TODO add your handling code here:
        int row = tbX.getSelectedRow();
        if (row >= 0 && row < listX.size()) {
            maX.setText(tmX.getValueAt(row, 0).toString());
            tenX.setText(tmX.getValueAt(row, 1).toString());
            for (int i = 0; i < hesoX.getItemCount(); i++) {
                if (hesoX.getItemAt(i).equals(tmX.getValueAt(row, 2).toString())) {
                    hesoX.setSelectedIndex(i);
                };
            }
        }
    }//GEN-LAST:event_tbXMouseClicked

    private void songayCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_songayCCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_songayCCActionPerformed

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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> bacCN;
    private javax.swing.JButton boquaCNBT;
    private javax.swing.JButton boquaXBT;
    private javax.swing.JButton capnhatCNBT;
    private javax.swing.JButton capnhatXBT;
    private javax.swing.JTextField dchiCN;
    private javax.swing.JTextField dthoaiCN;
    private javax.swing.JComboBox<String> hesoX;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton loadCCBT;
    private javax.swing.JButton luuCCBT;
    private javax.swing.JButton luuCNBT;
    private javax.swing.JButton luuXBT;
    private javax.swing.JTextField maCN;
    private javax.swing.JComboBox<String> maCNCB;
    private javax.swing.JTextField maX;
    private javax.swing.JComboBox<String> maXCB;
    private javax.swing.JComboBox<String> sapxepCB;
    private javax.swing.JButton sapxepCCBT;
    private javax.swing.JTextField songayCC;
    private javax.swing.JButton suaXBT;
    private javax.swing.JTable tbBCC;
    private javax.swing.JTable tbCN;
    private javax.swing.JTable tbX;
    private javax.swing.JTextField tenCN;
    private javax.swing.JTextField tenX;
    private javax.swing.JButton themCCBT;
    private javax.swing.JButton themCNBT;
    private javax.swing.JButton themXBT;
    private javax.swing.JButton xoaCNBT;
    // End of variables declaration//GEN-END:variables
}
