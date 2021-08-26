package com.rizalpurnama.controller;

import com.rizalpurnama.dao.KelurahanDao;
import com.rizalpurnama.entity.Kelurahan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class KelurahanApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(KelurahanApiController.class);

    @Autowired
    private KelurahanDao kelurahanDao;

    @GetMapping("/api/kelurahan")
    @ResponseBody
    public Page<Kelurahan> dataKelurahan(Pageable page){
        return kelurahanDao.findAll(page);
    }

    @GetMapping("/api/kelurahan/{id}")
    @ResponseBody
    public Kelurahan cariKelurahanById(@PathVariable(name = "id") @Valid Kelurahan k){
        return k;
    }

    @PostMapping("/api/kelurahan")
    @ResponseStatus(HttpStatus.CREATED)
    private void simpan(@RequestBody @Valid Kelurahan kelurahan){
        kelurahanDao.save(kelurahan);
    }

    @PutMapping("/api/kelurahan/{id}")
    @ResponseStatus(HttpStatus.OK)
    private void update(@PathVariable("id") String id,
                        @RequestBody @Valid Kelurahan input){
        Kelurahan oldData = kelurahanDao.findById(id).orElse(null);
        if (oldData == null){
            LOGGER.warn("Kelurahan dengan id {} tidak ada di database", id);
            return;
        }
        BeanUtils.copyProperties(input, oldData);
        oldData.setId(id);
        kelurahanDao.save(oldData);
    }

    @DeleteMapping("/api/kelurahan/{id}")
    @ResponseStatus(HttpStatus.OK)
    private void delete(@PathVariable("id") Kelurahan kelurahan){
        if (kelurahan != null){
            kelurahanDao.delete(kelurahan);
            LOGGER.info("Berhasil Menghapus Kelurahan dengan id {}", kelurahan.getId());
        }else {
            LOGGER.error("Gagal menghapus, tidak dapat menemukan kelurahan dengan id {}", kelurahan.getId());
        }
    }
}
