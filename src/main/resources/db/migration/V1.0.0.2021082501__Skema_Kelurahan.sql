create table kelurahan(
    id varchar(36) primary key,
    kode varchar(10) not null unique,
    nama varchar(255) not null,
    kodepos varchar(10) not null
);