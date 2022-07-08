drop database if exists dr_muelas;
create database dr_muelas;
use dr_muelas;

/*Entidades*/
create table fichaMedica(
	id int,
	DNI long,
    fechaNacimiento date,
	nombre varchar(15),
	apellido varchar(15),
	edad tinyint,
	peso float,
	talla float,
	alergias varchar(100),
	tratamientos varchar(100),
	activo boolean,
	primary key (id)
);
create table paciente(
	id int,
    idFicha int,
	usuario varchar(15),
	contrasenia varchar(15),
    fechaCreacion datetime,
    activo boolean,
	primary key (id),
    foreign key (idFicha) references FichaMedica(id)
);
create table turno(
	id int,
    idPaciente int,
    diayHoraSolicitado datetime,
	diaYHoraDelTurno datetime,
	estado varchar(10),
    sector varchar(15),
	activo boolean,
	primary key (id),
    foreign key (idPaciente) references paciente(id)
);

