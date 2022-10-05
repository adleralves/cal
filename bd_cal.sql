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

insert into usuario(login, senha) values
("adler", "123"),
("dani", "123"),
("brunos", "123");

# Tabela Sala

create table sala(
    id bigint auto_increment,
    nome varchar(3) not null,
    estado boolean not null,
    primary key(id)
);

insert into sala(nome, estado) values
("61", false),
("62", false),
("64", false);

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

insert into acessos(verificacao, usuario_id, sala_id) values
(true, 1, 3)
