drop database if exists dr_muelas;
create database dr_muelas;
use dr_muelas;

/*Entidades*/
create table fichaMedica(
	id int,
	DNI long,
	nombre varchar(15),
	apellido varchar(15),
	edad tinyint,
	peso float,
	talla float,
	alergias varchar(100),
	tratamientos varchar(100),
	primary key (id)
);
create table sector(
	id int,
    nombre varchar(10),
    cantPacientesRegistrados int,
    tipo enum("emergencia,""normal"),
    primary key (id)
);
create table turno(
	id int,
	diaYHora datetime,
	estado varchar(10),
	primary key (id)
);
create table paciente(
	id int,
	usuario varchar(15),
	contraseña varchar(15),
	primary key (id)
);
create table informe(
	id int,
	sector varchar(10),
	cantPacientesRegistrados int,
	cantTurnosRegistrados int,
	cantTurnosCancelados int,
	fechaInforme Date,
	primary key (id)
);

/*Relaciones temporales, luego seran incluidas dentro de las entidades*/
create table ficha_asignada(
	id int,
	idPaciente int,
	idfichaMedica int,
    primary key (id),
	foreign key(idPaciente) references Paciente(id),
	foreign key(idFichaMedica) references fichaMedica(id)
);
create table sector_asignado(
	id int,
    idPaciente int,
    idSectorAsignado int,
    primary key(id),
    foreign key (idPaciente) references paciente (id),
    foreign key (idSectorAsignado) references sector (id)

);
create table turno_asignado_paciente(

	id int,
    idPaciente int,
    idTurno int,
    diayhora datetime,
    primary key(id),
    foreign key (idPaciente) references paciente (id),
    foreign key (idTurno) references turno (id)
);
