package com.rizalpurnama.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Kelurahan {

    @Id
    @GeneratedValue(generator = "system.uuid")
    @GenericGenerator(name = "system.uuid", strategy = "uuid2")
    private String id;

    @NotNull @NotEmpty
    private String kode;

    @NotNull @NotEmpty
    private String nama;

    @NotNull @NotEmpty
    private String kodepos;
}