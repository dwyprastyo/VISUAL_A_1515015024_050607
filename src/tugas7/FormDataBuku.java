/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tugas7;
import tugas6.*;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author prastyo
 */
public class FormDataBuku extends javax.swing.JFrame {
    private DefaultTableModel model; //untuk membuat model pada tabel
    private Connection con = koneksi.getConnection(); //untuk mengambil koneksi
    private Statement stt; //untuk mengeksekusi query database
    private ResultSet rss; //untuk menampung data dari database
    
    private int baris; //untuk mendeklarasikan variabel baris yang bertipe integer pada table
    private boolean cekbuku=true; //untuk mendeklarasikan variabel cekbuku yang nantinya akan digunakan untuk proses validasi
    /**
     * Creates new form FormDataBuku
     */
    public FormDataBuku() { //method dari Form yang dibuat
        initComponents(); 
    }
    private void InitTable(){ //method untuk menghubungkan inputan agar masuk ke table 
        model = new DefaultTableModel(); //objek model
        model.addColumn("ID");  //untuk menghubungkan dengan kolom ID
        model.addColumn("JUDUL"); //untuk menghubungkan dengan kolom JUDUL
        model.addColumn("PENULIS"); //untuk menghubungkan dengan kolom PENULIS
        model.addColumn("HARGA"); //untuk menghubungkan dengan kolom HARGA
        
        jTable1.setModel(model);
    }
    private void TampilData(){ //method yang digunakan untuk menampilkan isi tabel dari database
        try { //untuk menangkap kesalahan pada program
            String sql = "SELECT * FROM buku"; //query untuk mengambil dan menampilkan isi tabel dari database
            stt = con.createStatement(); //untuk koneksi database
            rss = stt.executeQuery(sql); //untuk mengeksekusi query
            while(rss.next()){ //perulangan untuk menampilkan data
                Object[] o = new Object[4]; //objek baru bernama o dengan 4 array
                o[0] = rss.getInt("id");  //array ke-0 mengambil dan menampilkan data id
                o[1] = rss.getString("judul"); //array ke-1 mengambil dan menampilkan data judul
                o[2] = rss.getString("penulis"); //array ke-2 mengambil dan menampilkan data penulis
                o[3] = rss.getString("harga"); //array ke-3 mengambil dan menampilkan data harga
                model.addRow(o); //untuk menambahkan baris pada kolom setiap ada data yang ditampilkan
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void TambahData(String judul, String penulis, String harga){ //method yang digunakan untuk menambahkan data
        try {
            String sql = 
                    "INSERT INTO buku VALUES (NULL,'"+judul+"','"+penulis+"',"+harga+")"; //query untuk menambahkan data di tabel yang ada di database
            stt = con.createStatement(); //untuk koneksi database
            stt.executeUpdate(sql); //untuk eksekusi update sql
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void HapusData(String id,int baris){ //method yang digunakan untuk menghapus data
        try {
            
            String sqldelete = 
                    "DELETE FROM buku WHERE id="+id+""; //query untuk menghapus data pada tabel yang ada di database
            stt = con.createStatement(); //untuk koneksi database
            stt.executeUpdate(sqldelete); //untuk mengeksekusi query
            model.removeRow(baris); //untuk menghapus baris jika ada data yang di hapus
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void UbahData(String judul,String penulis, String harga, String id){ //method yang digunakan untuk mengupdate data
        try {
            
            String sql = "UPDATE buku SET " //query untuk mengupdate data 
                         + "judul='"+judul+"',"
                         + "penulis='"+penulis+"',"
                         + "harga='"+harga+"'"
                         + "WHERE id='"+id+"'";
            stt = con.createStatement(); //untuk koneksi database
            stt.executeUpdate(sql); //untuk mengeksekusi query
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
     private void cariData(){ //method yang digunakan untuk mencari data
        try {
            String sql = "SELECT * FROM buku where judul='"+cari.getText()+"'|| penulis='"+cari.getText()+"'|| harga='"+cari.getText()+"'"; //query yang digunakan untuk mencari data berdasarkan judul,penulis dan harga
            stt = con.createStatement(); //untuk koneksi database
            rss = stt.executeQuery(sql); //untuk mengeksekusi query
            while(rss.next()){ //perulangan untuk menampilkan data yang dicari
                Object[] o = new Object[4];
                o[0] = rss.getInt("id");
                o[1] = rss.getString("judul");
                o[2] = rss.getString("penulis");
                o[3] = rss.getString("harga");
                model.addRow(o);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
     
    private void validasi (String judul, String penulis, String harga){ //method yang digunakan untuk validasi data yang diinputkan agar tidak ada data yang sama
        try {
            String sql = "SELECT * FROM buku"; //query untuk menselect semua kolom pada tabel buku
            stt = con.createStatement(); //untuk koneksi database
            rss = stt.executeQuery(sql); //untuk mengeksekusi database
            while(rss.next()){ //perulangan untuk menampilkan data
                Object[] o = new Object[2]; 
                o[0] = rss.getString("judul").toLowerCase(); //array ke-0 mengambil dan menampilkan data dari judul dan mengubahnya menjadi huruf keccil semua 
                o[1] = rss.getString("penulis").toLowerCase(); //array ke-1 mengambil dan menampilkan data dari penulis dan mengubahnya menjadi huruf keccil semua
                
                if( o[0].equals(judul.toLowerCase()) && o[1].equals(penulis.toLowerCase())){ //kondisi untuk menseleksi data yang di inputkan dengan data yang telah ada pada database
                    JOptionPane.showMessageDialog(null,"Data telah ada");
                    cekbuku=false; //cekbuku bernilai false karena sebelumnya telah di set bernila true
                    break;
                }
            }
            if(cekbuku==true){ //kondisi jika cekbuku bernilai true maka menjalankan metodh tambahbuku dan data bisa tersimpan
                TambahData(judul, penulis, harga);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
  
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtHarga = new javax.swing.JTextField();
        txtJudul = new javax.swing.JTextField();
        comboPenulis = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        simpan = new javax.swing.JButton();
        ubah = new javax.swing.JButton();
        hapus = new javax.swing.JButton();
        keluar = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        cari = new javax.swing.JTextField();
        btncari = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(102, 73, 10));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("From Data Buku");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(138, 138, 138)
                .addComponent(jLabel1)
                .addContainerGap(175, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(102, 73, 10));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Judul");

        jPanel4.setBackground(new java.awt.Color(102, 73, 10));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Penulis");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Harga");

        comboPenulis.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Prof.H.Dwy Prastyo.Phd", "W.S Rendra", "Felix Siauw", "Asma Nadia", "Dewi Lestari" }));
        comboPenulis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboPenulisActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4))
                .addGap(29, 29, 29)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtJudul)
                    .addComponent(comboPenulis, 0, 334, Short.MAX_VALUE)
                    .addComponent(txtHarga))
                .addGap(18, 18, 18))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(txtJudul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboPenulis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtHarga, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel2)
                .addContainerGap(122, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "JUDUL", "PENULIS", "HARGA"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        simpan.setText("SIMPAN");
        simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanActionPerformed(evt);
            }
        });

        ubah.setText("UBAH");
        ubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ubahActionPerformed(evt);
            }
        });

        hapus.setText("HAPUS");
        hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusActionPerformed(evt);
            }
        });

        keluar.setText("KELUAR");
        keluar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keluarMouseClicked(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(102, 73, 10));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Search");

        cari.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cariMouseClicked(evt);
            }
        });
        cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cariActionPerformed(evt);
            }
        });

        btncari.setText("Cari");
        btncari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncariActionPerformed(evt);
            }
        });

        jButton1.setText("back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cari, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btncari, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(btncari)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(ubah, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(keluar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(0, 9, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ubah)
                        .addComponent(hapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(keluar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(simpan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 9, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void comboPenulisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboPenulisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboPenulisActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
    //untuk menampilkan data pada tabel    
        InitTable();
        TampilData();
       
    }//GEN-LAST:event_formComponentShown

    private void simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanActionPerformed
        //action untuk tombol simpan 
        if(txtJudul.getText().equals("") && txtHarga.getText().equals("")) //kondisi jika field teks judul dan harga kosong maka akan menampilkan joptionpane seperti dibawah
     {
           JOptionPane.showMessageDialog(null, "Data Belum Lengkap","Warning !!!!",JOptionPane.INFORMATION_MESSAGE);
           txtJudul.requestFocus(); //kursor akan focus mengarahkan ke field teks judul
     } else{ //jika selain kondisi diatas maka akan menjalankan fungsi dibawah ini
        String judul = txtJudul.getText(); //untuk mengisi kolom judul dengan mengambil data yang di inputkan di field teks judul
        String penulis = comboPenulis.getSelectedItem().toString(); //untuk mengisi kolom penulsi pada database dengan mengambil data yang di pilih di combobox penulis
        String harga = txtHarga.getText(); //untuk mengisi kolom harga pada database dengan mengambil data yang di inputkan di field teks harga
        
        validasi(judul, penulis, harga); //memanggi metodh validasi
         
        InitTable(); //memanggil metod untuk tabel
        TampilData(); //memanggil metod untuk menampilkan data pada tabel
     }
    }//GEN-LAST:event_simpanActionPerformed

    private void keluarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_keluarMouseClicked
    //action untuk tombol keluar
        System.exit(0);        // TODO add your handling code here:
    }//GEN-LAST:event_keluarMouseClicked

    private void hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusActionPerformed
    //action untuk tombol hapus
        try {
        int baris = jTable1.getSelectedRow(); //untuk mendeklarasikan variabel baris pada tabel 
        String id = jTable1.getValueAt(baris, 0).toString(); //mengdeklarasikan variabel id pada tabel dan mengambil nilai pada tabel id
        HapusData(id, baris);    //memanggil metod hapusdata dan menghapus data berdasatkan id
        } catch (Exception e) {
         JOptionPane.showMessageDialog(null,"Data tidak dipilih","Warning !!!!",JOptionPane.INFORMATION_MESSAGE);   
        }     
    }//GEN-LAST:event_hapusActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
    //action untuk mengisi fild teks ketika mengklik atau memilih data pada tabel
        int baris = jTable1.getSelectedRow();
        
        String judul=jTable1.getValueAt(baris,1).toString();
        String penulis=jTable1.getValueAt(baris,2).toString();
        String harga=jTable1.getValueAt(baris,3).toString();
        String id=jTable1.getValueAt(baris,0).toString();
        
        
        txtJudul.setText(judul);
        comboPenulis.setSelectedItem(penulis);
        txtHarga.setText(harga);

// TODO add your handling code here:
      
        
    }//GEN-LAST:event_jTable1MouseClicked

    private void ubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ubahActionPerformed
    //action untuk tombol mengubah data    
        try {
        int baris = jTable1.getSelectedRow();
        
        
        jTable1.setValueAt(txtJudul.getText(),baris,1);
        jTable1.setValueAt(comboPenulis.getSelectedItem(),baris,2);
        jTable1.setValueAt(txtHarga.getText(),baris,3); 
        
        String judul=jTable1.getValueAt(baris,1).toString();
        String penulis=jTable1.getValueAt(baris,2).toString();
        String harga=jTable1.getValueAt(baris,3).toString();
        String id=jTable1.getValueAt(baris,0).toString();
        
        
        txtJudul.setText(judul);
        comboPenulis.setSelectedItem(penulis);
        txtHarga.setText(harga);
        
        UbahData(judul,penulis,harga,id);    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Data tidak dipilih","Warning !!!!",JOptionPane.INFORMATION_MESSAGE);
        }
 
        
    }//GEN-LAST:event_ubahActionPerformed

    private void cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cariActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_cariActionPerformed

    private void btncariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncariActionPerformed
    //action untuk tombol cari data
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        cariData();        
// TODO add your handling code here:
    }//GEN-LAST:event_btncariActionPerformed

    private void cariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cariMouseClicked
        
       
// TODO add your handling code here:
    }//GEN-LAST:event_cariMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    //action untuk menampilkan kembali data yang ada di tabel pada database
        InitTable();
        TampilData();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(FormDataBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormDataBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormDataBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormDataBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormDataBuku().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncari;
    private javax.swing.JTextField cari;
    private javax.swing.JComboBox<String> comboPenulis;
    private javax.swing.JButton hapus;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton keluar;
    private javax.swing.JButton simpan;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtJudul;
    private javax.swing.JButton ubah;
    // End of variables declaration//GEN-END:variables

}
