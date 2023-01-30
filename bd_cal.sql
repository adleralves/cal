drop database aa_api_teste;
create database aa_api_teste;
use aa_api_teste;

# Tabela Usuario

create table usuario (
    id bigint auto_increment,
    login varchar(20) not null,
    senha varchar(60) not null,
    primary key (id)
);

# Tabela Sala

create table sala(
    id bigint auto_increment,
    nome varchar(3) not null,
    estado boolean not null,
    primary key(id)
);


# Tabela Acessos

create table acessos(
    id bigint auto_increment,
    verificacao boolean,
    usuario_id bigint not null,
    sala_id bigint not null,
    primary key(id),
    foreign key fk_usuario(usuario_id) references usuario(id),
    foreign key fk_sala(sala_id) references sala(id)
);

