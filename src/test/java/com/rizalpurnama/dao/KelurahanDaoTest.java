package com.rizalpurnama.dao;

import com.rizalpurnama.entity.Kelurahan;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
public class KelurahanDaoTest {

    @Autowired
    private KelurahanDao kelurahanDao;

    @Test
    void testInsert() {

        Kelurahan k = new Kelurahan();
        k.setNama("Banyumas");
        k.setKodepos("17098");

        for (int i = 2; i <= 10; i++) {
            String running = String.valueOf(i);
            String kode = "K00" + running;
            k.setKode(kode);

            // Simpan data
            kelurahanDao.save(k);
        }

        // Test Apakah Benar datanya masih belum diinput.?
        Assertions.assertNull(k.getId());



        // Cetak data ke layar
        System.out.println("ID : " + k.getId());
        System.out.println("Kode : " + k.getKode());
        System.out.println("Nama : " + k.getNama());
        System.out.println("Kode Pos : " + k.getKodepos());

        // Test apakah data sudah masuk atau belum ?
        Assertions.assertNotNull(k.getId());
        Assertions.assertNotNull(k.getKode());
        Assertions.assertNotNull(k.getNama());
        Assertions.assertNotNull(k.getKodepos());
    }

    @Test
    void testMultipleInsert() {


        for (int i = 2; i <= 9; i++) {
            String running = String.valueOf(i);
            String kode = "K00" + running;
            Kelurahan k = new Kelurahan();
            k.setNama("Banyumas");
            k.setKodepos("17098");
            k.setKode(kode);

            // Simpan data
            kelurahanDao.save(k);
        }
    }
}
