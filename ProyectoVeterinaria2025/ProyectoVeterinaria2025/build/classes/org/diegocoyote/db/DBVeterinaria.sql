drop database if exists DBVeterinaria;
create database DBVeterinaria;
use DBVeterinaria;

Create table Cliente (
	codigoCliente int auto_increment,
	nombreCliente varchar(100),
	apellidoCliente varchar(50),
	telefonoCliente varchar(20),
	direccionCliente varchar(255),
	emailCliente varchar(100),
	fechaRegistro Date,
	primary key PK_codigoCliente (codigoCliente)
);

Create table Veterinario (
	codigoVeterinario int auto_increment,
	nombreVeterinario varchar(50) not null,
    apellidoVeterinario varchar(50) not null,
    especialidad varchar(100) not null,
    telefonoVeterinario varchar(8) not null,
    correoVeterinario varchar(100) not null,
    fechaIngreso date not null,
    estado varchar(100),
    primary key PK_codigoVeterinario (codigoVeterinario)
);

Create table Proveedor (
	codigoProveedor int auto_increment,
	nombreProveedor varchar(100),
	direccionProveedor varchar(100),
	telefonoProveedor varchar(20),
	correoProveedor varchar(100),
	primary key PK_codigoProveedor (codigoProveedor)
);

Create table Vacuna (
	codigoVacuna int auto_increment,
	nombreVacuna varchar(100),
	descripcion varchar(255),
	dosis varchar(100),
	frecuenciaMeses int,
	primary key PK_codigoVacuna (codigoVacuna)
);

Create table Empleado (
	codigoEmpleado int auto_increment,
	nombreEmpleado varchar(50),
	apellidoEmpleado varchar(50),
	cargo varchar(50),
	telefonoEmpleado varchar(20),
	correoEmpleado varchar(100),
	primary key PK_codigoEmpleado (codigoEmpleado)
);

Create table Mascota (
	codigoMascota int auto_increment,
    nombreMascota varchar(50) not null,
    especie varchar(30) not null,
    raza varchar(50) not null,
    sexo varchar(100),
    fechaNacimiento date not null,
    color varchar(30),
    pesoActualKg decimal(5,2),
    primary key PK_codigoMascota (codigoMascota),
    codigoCliente int,
    Constraint FK_Mascotas_Clientes foreign key (codigoCliente) references Cliente(codigoCliente) On Delete Cascade on update cascade
);

Create table Consulta (
	codigoConsulta int auto_increment,
    fechaConsulta datetime not null,
    motivo  varchar(255) not null,
    diagnostico varchar(255) not null,
    observaciones varchar(255),
    primary key FK_codigoConsulta(codigoConsulta),
    codigoMascota int,
    codigoVeterinario int,
    Constraint FK_Consultas_Mascotas foreign key (codigoMascota) references Mascota (codigoMascota) On Delete Cascade on update cascade,
    Constraint FK_Consultas_Veterinarios foreign key (codigoVeterinario) references Veterinario (codigoVeterinario)On Delete Cascade on update cascade
);

Create table Tratamiento (
	codigoTratamiento int auto_increment,
    descripcion varchar(255) not null,
    fechaInicio date not null,
    fechaFin date not null,
    medicamentosIndicados varchar(255),
    primary key PK_codigoTratamiento(codigoTratamiento),
    codigoConsulta int,
    Constraint FK_Tratamientos_Consultas foreign key (codigoConsulta) references Consulta (codigoConsulta) On Delete Cascade on update cascade
);
	
Create table Cita (
	codigoCita int auto_increment,
    fechaCita date not null,
    horaCita time not null,
    motivo varchar(255) not null,
    estado varchar(100),
    primary key PK_codigoCita (codigoCita),
    codigoMascota int,
    codigoVeterinario int,
    Constraint FK_Citas_Mascotas foreign key (codigoMascota) references Mascota (codigoMascota) On Delete Cascade on update cascade,
    Constraint FK_Citas_Veterinarios foreign key (codigoVeterinario) references Veterinario (codigoVeterinario) On Delete Cascade on update cascade
);

Create table Vacunacion (
	codigoVacunacion int auto_increment,
    fechaAplicacion date not null,
    observaciones  varchar(255) not null,
    primary key PK_codigoVacunacion (codigoVacunacion),
    codigoMascota int,
    codigoVacuna int,
    codigoVeterinario int,
    Constraint FK_Vacunaciones_Mascotas foreign key (codigoMascota) references Mascota (codigoMascota) On Delete Cascade on update cascade,
    Constraint FK_Vacunaciones_Vacunas foreign key (codigoVacuna) references Vacuna (codigoVacuna) On Delete Cascade on update cascade,
    Constraint FK_Vacunaciones_Veterinario foreign key (codigoVeterinario) references Veterinario (codigoVeterinario) On Delete Cascade on update cascade
);

Create table Medicamento (
	codigoMedicamento int auto_increment,
	nombreMedicamento  varchar(100) not null,
    descripcion  varchar(255) not null,
    stock int not null,
    precioUnitario decimal(10,2) not null,
    fechaVencimiento date,
    primary key PK_codigoMedicamento (codigoMedicamento),
    codigoProveedor int,
    Constraint FK_Medicamentos_Proveedores foreign key (codigoProveedor) references Proveedor (codigoProveedor) On Delete Cascade on update cascade
);

Create table Receta (
	codigoReceta int auto_increment,
    dosis  varchar(100) not null,
    frecuencia varchar(100) not null,
    duracionDias int not null,
    indicaciones varchar(255),
    primary key PK_codigoReceta (codigoReceta),
    codigoConsulta int,
    codigoMedicamento int,
    Constraint FK_Recetas_Consultas foreign key (codigoConsulta) references Consulta (codigoConsulta) On Delete Cascade on update cascade,
    Constraint FK_Recetas_Medicamentos foreign key (codigoMedicamento) references Medicamento (codigoMedicamento) On Delete Cascade on update cascade
);

Create table Factura (
	codigoFactura int auto_increment,
    fechaEmision date not null,
    total decimal(10,2),
    metodoPago varchar(100),
    primary key PK_codigoFactura (codigoFactura),
    codigoCliente int,
    codigoEmpleado int,
    Constraint FK_Facturas_Clientes foreign key (codigoCliente) references Cliente (codigoCliente) On Delete Cascade on update cascade,
    Constraint FK_Facturas_Empleados foreign key (codigoEmpleado) references Empleado (codigoEmpleado) On Delete Cascade on update cascade
);

Create table Compra (
	codigoCompra int auto_increment,
    fechaCompra date not null,
    total decimal(10,2),
    detalle varchar(255),
    primary key PK_codigoCompra (codigoCompra),
    codigoProveedor int,
    Constraint FK_Compras_Proveedores foreign key (codigoProveedor) references Proveedor (codigoProveedor) On Delete Cascade on update cascade
);

-- --------------------- PROCEDIMENTOS ALMACENADOS ---------------------------

-- Agregar 

Delimiter $$
	Create procedure sp_agregar_cliente(
    in nombreCliente varchar(100),
    in apellidoCliente varchar(100),
    in telefonoCliente varchar(20),
    in direccionCliente varchar(255),
    in emailCliente varchar(100),
    in fechaRegistro date
    )
    Begin 
		Insert into Cliente(nombreCliente, apellidoCliente, telefonoCliente, direccionCliente, emailCliente, fechaRegistro)
			Values (nombreCliente, apellidoCliente, telefonoCliente, direccionCliente, emailCliente, fechaRegistro);
	End$$
Delimiter ;

call sp_agregar_cliente('Carlos', 'Gómez', '5551234567', 'Av. Siempre Viva 123', 'carlos.gomez@mail.com', '2024-10-15');
call sp_agregar_cliente('Lucía', 'Martínez', '5559876543', 'calle del Sol 456', 'lucia.martinez@mail.com', '2025-01-22');
call sp_agregar_cliente('Pedro', 'Fernández', '5557654321', 'calle Luna 789', 'pedro.fernandez@mail.com', '2024-12-10');
call sp_agregar_cliente('Ana', 'López', '5551122334', 'Av. Reforma 101', 'ana.lopez@mail.com', '2024-11-05');
call sp_agregar_cliente('María', 'Ruiz', '5554433221', 'calle Hidalgo 222', 'maria.ruiz@mail.com', '2025-02-17');
call sp_agregar_cliente('José', 'Sánchez', '5559988776', 'Av. Central 333', 'jose.sanchez@mail.com', '2024-09-30');
call sp_agregar_cliente('Laura', 'Torres', '5556655443', 'Col. Roma 444', 'laura.torres@mail.com', '2025-03-10');
call sp_agregar_cliente('Diego', 'Ramírez', '5553322110', 'Av. Juárez 555', 'diego.ramirez@mail.com', '2024-10-01');
call sp_agregar_cliente('Elena', 'Castro', '5557788990', 'calle Independencia 666', 'elena.castro@mail.com', '2025-01-05');
call sp_agregar_cliente('Javier', 'Núñez', '5552233445', 'Col. Centro 777', 'javier.nunez@mail.com', '2025-04-01');
call sp_agregar_cliente('Isabel', 'Vargas', '5556677889', 'calle Morelos 888', 'isabel.vargas@mail.com', '2025-02-01');
call sp_agregar_cliente('Tomás', 'Herrera', '5554455667', 'Av. México 999', 'tomas.herrera@mail.com', '2025-03-20');
call sp_agregar_cliente('Renata', 'Pérez', '5558899001', 'Col. Del Valle 1010', 'renata.perez@mail.com', '2025-01-12');
call sp_agregar_cliente('Mateo', 'Rojas', '5553344556', 'calle Reforma 1111', 'mateo.rojas@mail.com', '2024-11-25');
call sp_agregar_cliente('Valeria', 'Jiménez', '5557766554', 'Av. Insurgentes 1212', 'valeria.jimenez@mail.com', '2025-02-28');

Delimiter $$
	Create Procedure sp_agregar_veterinario(
    in nombreVeterinario varchar (50),
    in apellidoVeterinario varchar(50),
    in especialidad varchar(100),
    in telefonoVeterinario varchar(8),
    in correoVeterinario varchar(100),
    in fechaIngreso date,
    in estado varchar(100)
    )
    Begin
		Insert into Veterinario(nombreVeterinario, apellidoVeterinario, especialidad, telefonoVeterinario, correoVeterinario, fechaIngreso, estado)
			Values(nombreVeterinario, apellidoVeterinario, especialidad, telefonoVeterinario, correoVeterinario, fechaIngreso, estado);
	End$$
Delimiter ;
call sp_agregar_veterinario('Luis', 'Ramírez', 'Cirugía', '23456789', 'luis.ramirez@vetmail.com', '2023-05-15', 'Activo');
call sp_agregar_veterinario('Sandra', 'Torres', 'Dermatología', '23451234', 'sandra.torres@vetmail.com', '2022-11-03', 'Activo');
call sp_agregar_veterinario('Miguel', 'Gómez', 'Odontología', '87654321', 'miguel.gomez@vetmail.com', '2024-01-20', 'Activo');
call sp_agregar_veterinario('Patricia', 'Morales', 'Cardiología', '34567890', 'patricia.morales@vetmail.com', '2022-09-12', 'Inactivo');
call sp_agregar_veterinario('Andrés', 'Lopez', 'Oftalmología', '45678901', 'andres.lopez@vetmail.com', '2023-06-10', 'Activo');
call sp_agregar_veterinario('Lucía', 'Martínez', 'General', '54321678', 'lucia.martinez@vetmail.com', '2021-04-22', 'Activo');
call sp_agregar_veterinario('Carlos', 'Paredes', 'Anestesiología', '65432109', 'carlos.paredes@vetmail.com', '2022-02-18', 'Inactivo');
call sp_agregar_veterinario('Marta', 'Ríos', 'Neurología', '76543210', 'marta.rios@vetmail.com', '2023-07-25', 'Activo');
call sp_agregar_veterinario('Javier', 'Salazar', 'Rehabilitación', '87651234', 'javier.salazar@vetmail.com', '2023-12-30', 'Activo');
call sp_agregar_veterinario('Sofía', 'Velázquez', 'Oncología', '98765432', 'sofia.velazquez@vetmail.com', '2024-03-05', 'Inactivo');
call sp_agregar_veterinario('Fernando', 'Luna', 'Nefrología', '13572468', 'fernando.luna@vetmail.com', '2022-10-01', 'Activo');
call sp_agregar_veterinario('Gabriela', 'Mendoza', 'Etología', '24681357', 'gabriela.mendoza@vetmail.com', '2023-01-15', 'Activo');
call sp_agregar_veterinario('Ricardo', 'Navarro', 'Urgencias', '35792468', 'ricardo.navarro@vetmail.com', '2024-02-10', 'Activo');
call sp_agregar_veterinario('Valentina', 'Herrera', 'Ortopedia', '46813579', 'valentina.herrera@vetmail.com', '2021-08-19', 'Inactivo');
call sp_agregar_veterinario('David', 'Cabrera', 'Traumatología', '57924680', 'david.cabrera@vetmail.com', '2023-04-27', 'Activo');

Delimiter $$
	Create procedure sp_agregar_proveedor(
	in nombreProveedor varchar(100),
    in direccionProveedor varchar(100),
    in telefonoProveedor varchar (20),
    in correoProveedor varchar (100)
    )
    Begin 
		Insert into Proveedor (nombreProveedor, direccionProveedor, telefonoProveedor, correoProveedor)
			Values (nombreProveedor, direccionProveedor, telefonoProveedor, correoProveedor);
	End$$
Delimiter ;
call sp_agregar_proveedor('AgroVet S.A.', 'Av. Central 123', '5551234567', 'contacto@agrovet.com');
call sp_agregar_proveedor('Distribuciones VetPlus', 'Calle Luna 45', '5559876543', 'info@vetplus.com');
call sp_agregar_proveedor('Farmavet', 'Col. Roma Norte 101', '5558765432', 'ventas@farmavet.com');
call sp_agregar_proveedor('Servicios Veterinarios MX', 'Av. Revolución 67', '5557654321', 'soporte@servvetmx.com');
call sp_agregar_proveedor('Proveedora Animal', 'Calle Hidalgo 89', '5556543210', 'admin@proveedoraanimal.com');
call sp_agregar_proveedor('Mascotas y Más', 'Av. Insurgentes 321', '5551122334', 'contacto@mascotasymas.com');
call sp_agregar_proveedor('Zootecnia Moderna', 'Calle Morelos 12', '5554433221', 'ventas@zootecniamoderna.com');
call sp_agregar_proveedor('VetDistribuciones', 'Col. Del Valle 456', '5559988776', 'contacto@vetdistribuciones.com');
call sp_agregar_proveedor('Animal Care Proveedores', 'Av. Juárez 210', '5556655443', 'animalcare@proveedores.com');
call sp_agregar_proveedor('Suministros VetLine', 'Calle Independencia 33', '5553322110', 'info@vetline.com');
call sp_agregar_proveedor('BiotecVet', 'Av. México 77', '5557788990', 'contacto@biotecvet.com');
call sp_agregar_proveedor('Laboratorios CanVet', 'Col. Centro 88', '5552233445', 'ventas@canvetlab.com');
call sp_agregar_proveedor('Equinos y Compañía', 'Calle Reforma 90', '5556677889', 'info@equinoscia.com');
call sp_agregar_proveedor('Pet Pharma Distribuidores', 'Av. Universidad 10', '5554455667', 'petpharma@distribuidores.com');
call sp_agregar_proveedor('Vet Global S.A. de C.V.', 'Calle del Trabajo 55', '5558899001', 'ventas@vetglobal.com');


Delimiter $$
	Create procedure sp_agregar_vacuna(
	in nombreVacuna varchar(100),
	in descripcion varchar(255),
	in dosis varchar(100),
	in frecuenciaMeses int
    )
    Begin 
		Insert into Vacuna(nombreVacuna, descripcion, dosis, frecuenciaMeses)
			Values (nombreVacuna, descripcion, dosis, frecuenciaMeses);
	End$$
Delimiter ;
call sp_agregar_vacuna('Rabvac', 'Vacuna contra la rabia en perros y gatos', '1 ml', 12);
call sp_agregar_vacuna('PuppyGuard 5', 'Vacuna quíntuple para cachorros', '2 ml', 12);
call sp_agregar_vacuna('Felocell 4', 'Vacuna cuádruple para gatos', '1 ml', 12);
call sp_agregar_vacuna('Duramune Max 5', 'Vacuna contra moquillo, hepatitis, parvo, parainfluenza y leptospira', '1 ml', 12);
call sp_agregar_vacuna('Vanguard Plus 5', 'Vacuna combinada para perros adultos', '1.5 ml', 12);
call sp_agregar_vacuna('Nobivac DHPPi', 'Vacuna contra moquillo y parvovirus', '1 ml', 12);
call sp_agregar_vacuna('Purevax RCP', 'Vacuna trivalente para gatos', '1 ml', 12);
call sp_agregar_vacuna('Feligen CRP', 'Vacuna contra rinotraqueítis, calicivirus y panleucopenia felina', '1 ml', 12);
call sp_agregar_vacuna('Bronchi-Shield III', 'Vacuna intranasal contra tos de las perreras', '0.5 ml', 6);
call sp_agregar_vacuna('Canigen L4', 'Vacuna contra leptospirosis canina', '1 ml', 12);
call sp_agregar_vacuna('Eurican Herpes 205', 'Vacuna para el herpesvirus canino', '1 ml', 12);
call sp_agregar_vacuna('FeloCel CVR', 'Vacuna felina combinada', '1 ml', 12);
call sp_agregar_vacuna('Versican Plus DHPPi/L4', 'Vacuna combinada para perros', '1.5 ml', 12);
call sp_agregar_vacuna('Protech C3', 'Vacuna trivalente para perros', '1 ml', 12);
call sp_agregar_vacuna('QuadriFel', 'Vacuna tetravalente felina', '1 ml', 12);

 
 Delimiter $$
	Create procedure sp_agregar_empleado(
    in nombreEmpleado varchar(50),
	in apellidoEmpleado varchar(50),
	in cargo varchar(50),
	in telefonoEmpleado varchar(20),
	in correoEmpleado varchar(100)
    )
    Begin
		Insert into Empleado (nombreEmpleado, apellidoEmpleado, cargo, telefonoEmpleado, correoEmpleado)
			Values (nombreEmpleado, apellidoEmpleado, cargo, telefonoEmpleado, correoEmpleado);
	End$$
Delimiter ;
call sp_agregar_empleado('Laura', 'Méndez', 'Recepcionista', '5551234567', 'laura.mendez@clinicavet.com');
call sp_agregar_empleado('Carlos', 'Santos', 'Asistente Veterinario', '5552345678', 'carlos.santos@clinicavet.com');
call sp_agregar_empleado('Ana', 'Rojas', 'Encargada de Limpieza', '5553456789', 'ana.rojas@clinicavet.com');
call sp_agregar_empleado('Miguel', 'Zúñiga', 'Administrador', '5554567890', 'miguel.zuniga@clinicavet.com');
call sp_agregar_empleado('Sofía', 'Delgado', 'Recepcionista', '5555678901', 'sofia.delgado@clinicavet.com');
call sp_agregar_empleado('Fernando', 'López', 'Asistente Veterinario', '5556789012', 'fernando.lopez@clinicavet.com');
call sp_agregar_empleado('Lucía', 'Hernández', 'Encargada de Almacén', '5557890123', 'lucia.hernandez@clinicavet.com');
call sp_agregar_empleado('David', 'Reyes', 'Encargado de Limpieza', '5558901234', 'david.reyes@clinicavet.com');
call sp_agregar_empleado('Andrea', 'Pérez', 'Recepcionista', '5559012345', 'andrea.perez@clinicavet.com');
call sp_agregar_empleado('Jorge', 'Navarro', 'Contador', '5550123456', 'jorge.navarro@clinicavet.com');
call sp_agregar_empleado('Valeria', 'García', 'Asistente Veterinario', '5552233445', 'valeria.garcia@clinicavet.com');
call sp_agregar_empleado('Pedro', 'Castillo', 'Chofer', '5553344556', 'pedro.castillo@clinicavet.com');
call sp_agregar_empleado('Isabel', 'Moreno', 'Encargada de Compras', '5554455667', 'isabel.moreno@clinicavet.com');
call sp_agregar_empleado('Roberto', 'Ramírez', 'Mantenimiento', '5555566778', 'roberto.ramirez@clinicavet.com');
call sp_agregar_empleado('Natalia', 'Silva', 'Supervisora', '5556677889', 'natalia.silva@clinicavet.com');

Delimiter $$
	Create procedure sp_agregar_mascota(
    in nombreMascota varchar(50),
    in especie varchar(30),
    in raza varchar(50),
    in sexo varchar(100),
    in fechaNacimiento date,
    in color varchar(30),
    in pesoActualKg decimal(5,2),
    in codigoCliente int
    )
    Begin
		Insert into Mascota(nombreMascota, especie, raza, sexo, fechaNacimiento, color, pesoActualKg, codigoCliente)
			Values (nombreMascota, especie, raza, sexo, fechaNacimiento, color, pesoActualKg, codigoCliente);
	End$$
Delimiter ;
call sp_agregar_mascota('Max', 'Perro', 'Labrador', 'Macho', '2021-06-15', 'Marrón', 28.50, 1);
call sp_agregar_mascota('Luna', 'Gato', 'Siames', 'Hembra', '2022-03-10', 'Blanco', 4.20, 2);
call sp_agregar_mascota('Rocky', 'Perro', 'Bulldog', 'Macho', '2020-11-25', 'Negro', 22.30, 3);
call sp_agregar_mascota('Mía', 'Gato', 'Persa', 'Hembra', '2023-01-08', 'Gris', 3.90, 4);
call sp_agregar_mascota('Thor', 'Perro', 'Pastor Alemán', 'Macho', '2019-07-19', 'Negro y Fuego', 32.00, 5);
call sp_agregar_mascota('Nala', 'Gato', 'Bengala', 'Hembra', '2021-09-14', 'Dorado', 5.10, 6);
call sp_agregar_mascota('Toby', 'Perro', 'Beagle', 'Macho', '2022-12-03', 'Tricolor', 12.70, 7);
call sp_agregar_mascota('Kiara', 'Gato', 'Angora', 'Hembra', '2020-05-27', 'Blanco', 4.50, 8);
call sp_agregar_mascota('Simba', 'Perro', 'Golden Retriever', 'Macho', '2023-04-12', 'Dorado', 26.80, 9);
call sp_agregar_mascota('Cleo', 'Gato', 'Maine Coon', 'Hembra', '2022-02-20', 'Gris y blanco', 6.00, 10);
call sp_agregar_mascota('Bruno', 'Perro', 'Dálmata', 'Macho', '2021-11-30', 'Blanco con manchas negras', 24.40, 11);
call sp_agregar_mascota('Pelusa', 'Gato', 'Europeo', 'Hembra', '2020-08-18', 'Negro', 3.80, 12);
call sp_agregar_mascota('Buddy', 'Perro', 'Cocker Spaniel', 'Macho', '2022-06-22', 'Marrón claro', 14.90, 13);
call sp_agregar_mascota('Lili', 'Gato', 'Abisinio', 'Hembra', '2023-02-05', 'Café', 4.10, 14);
call sp_agregar_mascota('Zeus', 'Perro', 'Rottvarchariler', 'Macho', '2018-10-01', 'Negro y marrón', 40.25, 15);

Delimiter $$
	Create procedure sp_agregar_consulta(
    in fechaConsulta datetime,
    in motivo  varchar(255),
    in diagnostico varchar(255),
    in observaciones varchar(255),
    in codigoMascota int,
    in codigoVeterinario int
    )
    Begin 
		Insert into Consulta(fechaConsulta, motivo, diagnostico, observaciones, codigoMascota, codigoVeterinario)
			Values (fechaConsulta, motivo, diagnostico, observaciones, codigoMascota, codigoVeterinario);
	End$$
Delimiter ;
call sp_agregar_consulta('2025-01-15 10:00:00', 'Chequeo general', 'Salud estable', 'Se recomienda control semestral', 1, 1);
call sp_agregar_consulta('2025-01-20 11:30:00', 'Vacunación anual', 'En buen estado', 'Aplicada vacuna triple', 2, 2);
call sp_agregar_consulta('2025-01-25 09:15:00', 'Diarrea leve', 'Gastroenteritis', 'Recetado probiótico', 3, 3);
call sp_agregar_consulta('2025-02-01 13:45:00', 'Infección ocular', 'Conjuntivitis', 'Aplicación de gotas cada 8h', 4, 4);
call sp_agregar_consulta('2025-02-05 16:00:00', 'Revisión por cirugía', 'Recuperación normal', 'Cita de seguimiento en 7 días', 5, 5);
call sp_agregar_consulta('2025-02-10 14:20:00', 'Herida en pata', 'Corte superficial', 'Vendaje y antibióticos', 6, 6);
call sp_agregar_consulta('2025-02-15 10:10:00', 'Tos persistente', 'Tos de las perreras', 'Tratamiento con jarabe', 7, 7);
call sp_agregar_consulta('2025-02-20 15:50:00', 'Problemas digestivos', 'Indigestión', 'Dieta blanda recomendada', 8, 8);
call sp_agregar_consulta('2025-02-25 12:30:00', 'Chequeo por edad', 'Signos leves de artritis', 'Suplementos recomendados', 9, 9);
call sp_agregar_consulta('2025-03-01 09:00:00', 'Vacunación antirrábica', 'Sin reacciones adversas', 'Aplicación exitosa', 10, 10);
call sp_agregar_consulta('2025-03-05 11:00:00', 'Caída de pelo', 'Alergia alimentaria', 'Cambio de dieta', 11, 11);
call sp_agregar_consulta('2025-03-10 17:30:00', 'Letargo', 'Posible anemia', 'Solicitado análisis de sangre', 12, 12);
call sp_agregar_consulta('2025-03-15 08:45:00', 'Chequeo post adopción', 'Sin anomalías', 'Peso y vacunas al día', 13, 13);
call sp_agregar_consulta('2025-03-20 10:15:00', 'Dolor al caminar', 'Luxación leve', 'Reposo y analgésicos', 14, 14);
call sp_agregar_consulta('2025-03-25 16:45:00', 'Revisión de oídos', 'Otitis', 'Tratamiento con gotas óticas', 15, 15);

Delimiter $$
	Create procedure sp_agregar_tratamiento(
    in descripcion varchar(255),
    in fechaInicio date,
    in fechaFin date ,
    in medicamentosIndicados varchar(255),
    in codigoConsulta int
    )
    Begin
		Insert into Tratamiento (descripcion, fechaInicio, fechaFin, medicamentosIndicados, codigoConsulta)
			Values(descripcion, fechaInicio, fechaFin, medicamentosIndicados, codigoConsulta);
	End$$
Delimiter ;
call sp_agregar_tratamiento('Tratamiento de antibióticos', '2025-01-16', '2025-01-23', 'Amoxicilina', 1);
call sp_agregar_tratamiento('Tratamiento para diarrea', '2025-01-26', '2025-02-02', 'Loperamida, probióticos', 2);
call sp_agregar_tratamiento('Tratamiento para conjuntivitis', '2025-02-02', '2025-02-09', 'Colirio antibiótico', 3);
call sp_agregar_tratamiento('Recuperación post quirúrgica', '2025-02-06', '2025-02-13', 'Analgésicos, antibióticos', 4);
call sp_agregar_tratamiento('Tratamiento para heridas', '2025-02-11', '2025-02-18', 'Antiséptico, vendaje', 5);
call sp_agregar_tratamiento('Tratamiento para tos', '2025-02-16', '2025-02-23', 'Jarabe para la tos', 6);
call sp_agregar_tratamiento('Tratamiento digestivo', '2025-02-21', '2025-02-28', 'Suero oral, dieta blanda', 7);
call sp_agregar_tratamiento('Tratamiento articular', '2025-02-26', '2025-03-05', 'Glucosamina, antiinflamatorios', 8);
call sp_agregar_tratamiento('Tratamiento antirrábico', '2025-03-02', '2025-03-02', 'Ninguno, solo vacuna', 9);
call sp_agregar_tratamiento('Tratamiento para alergia', '2025-03-06', '2025-03-13', 'Antihistamínicos, cambios en la dieta', 10);
call sp_agregar_tratamiento('Tratamiento para anemia', '2025-03-11', '2025-03-18', 'Suplementos de hierro', 11);
call sp_agregar_tratamiento('Tratamiento para oídos', '2025-03-16', '2025-03-23', 'Gotas óticas', 12);
call sp_agregar_tratamiento('Tratamiento post adopción', '2025-03-21', '2025-03-28', 'Vacunas, antiparasitarios', 13);
call sp_agregar_tratamiento('Tratamiento para luxación', '2025-03-26', '2025-04-02', 'Reposo, analgésicos', 14);
call sp_agregar_tratamiento('Tratamiento para otitis', '2025-03-30', '2025-04-06', 'Antibióticos tópicos', 15);

Delimiter $$
	Create procedure sp_agregar_cita(
    in fechaCita date,
    in horaCita time ,
    in motivo varchar(255),
    in estado varchar(100),
    in codigoMascota int,
    in codigoVeterinario int
    )
    Begin 
		Insert into Cita (fechaCita, horaCita, motivo, estado, codigoMascota, codigoVeterinario)
			Values (fechaCita, horaCita, motivo, estado, codigoMascota, codigoVeterinario);
	End$$
Delimiter ;
call sp_agregar_cita('2025-04-01', '10:00:00', 'Chequeo general', 'Pendiente', 1, 1);
call sp_agregar_cita('2025-04-02', '11:30:00', 'Vacunación anual', 'Pendiente', 2, 2);
call sp_agregar_cita('2025-04-03', '09:15:00', 'Diarrea leve', 'Pendiente', 3, 3);
call sp_agregar_cita('2025-04-04', '13:45:00', 'Infección ocular', 'Pendiente', 4, 4);
call sp_agregar_cita('2025-04-05', '16:00:00', 'Revisión post cirugía', 'Pendiente', 5, 5);
call sp_agregar_cita('2025-04-06', '14:20:00', 'Herida en pata', 'Pendiente', 6, 6);
call sp_agregar_cita('2025-04-07', '10:10:00', 'Tos persistente', 'Pendiente', 7, 7);
call sp_agregar_cita('2025-04-08', '15:50:00', 'Problemas digestivos', 'Pendiente', 8, 8);
call sp_agregar_cita('2025-04-09', '12:30:00', 'Chequeo por edad', 'Pendiente', 9, 9);
call sp_agregar_cita('2025-04-10', '09:00:00', 'Vacunación antirrábica', 'Pendiente', 10, 10);
call sp_agregar_cita('2025-04-11', '11:00:00', 'Caída de pelo', 'Pendiente', 11, 11);
call sp_agregar_cita('2025-04-12', '17:30:00', 'Letargo', 'Pendiente', 12, 12);
call sp_agregar_cita('2025-04-13', '08:45:00', 'Chequeo post adopción', 'Pendiente', 13, 13);
call sp_agregar_cita('2025-04-14', '10:15:00', 'Dolor al caminar', 'Pendiente', 14, 14);
call sp_agregar_cita('2025-04-15', '16:45:00', 'Revisión de oídos', 'Pendiente', 15, 15);

Delimiter $$
	Create procedure sp_agregar_vacunacion(
    in fechaAplicacion date,
    in observaciones  varchar(255),
    in codigoMascota int,
    in codigoVacuna int,
    in codigoVeterinario int
    )
    Begin
		Insert into Vacunacion (fechaAplicacion, observaciones, codigoMascota, codigoVacuna, codigoVeterinario)
			Values(fechaAplicacion, observaciones, codigoMascota, codigoVacuna, codigoVeterinario);
	End$$
Delimiter ;
call sp_agregar_vacunacion('2025-04-01', 'Vacuna contra la rabia aplicada', 1, 1, 1);
call sp_agregar_vacunacion('2025-04-02', 'Vacuna anual aplicada', 2, 2, 2);
call sp_agregar_vacunacion('2025-04-03', 'Vacuna contra parvovirus aplicada', 3, 3, 3);
call sp_agregar_vacunacion('2025-04-04', 'Vacuna de la gripe aplicada', 4, 4, 4);
call sp_agregar_vacunacion('2025-04-05', 'Vacuna de refuerzo aplicada', 5, 5, 5);
call sp_agregar_vacunacion('2025-04-06', 'Vacuna contra leptospirosis aplicada', 6, 6, 6);
call sp_agregar_vacunacion('2025-04-07', 'Vacuna contra moquillo aplicada', 7, 7, 7);
call sp_agregar_vacunacion('2025-04-08', 'Vacuna contra distemper aplicada', 8, 8, 8);
call sp_agregar_vacunacion('2025-04-09', 'Vacuna para la tos de las perreras aplicada', 9, 9, 9);
call sp_agregar_vacunacion('2025-04-10', 'Vacuna triple aplicada', 10, 10, 10);
call sp_agregar_vacunacion('2025-04-11', 'Vacuna contra hepatitis aplicada', 11, 11, 11);
call sp_agregar_vacunacion('2025-04-12', 'Vacuna contra parainfluenza aplicada', 12, 12, 12);
call sp_agregar_vacunacion('2025-04-13', 'Vacuna antirrábica aplicada', 13, 13, 13);
call sp_agregar_vacunacion('2025-04-14', 'Vacuna contra coronavirus canino aplicada', 14, 14, 14);
call sp_agregar_vacunacion('2025-04-15', 'Vacuna contra giardia aplicada', 15, 15, 15);

Delimiter $$
	Create procedure sp_agregar_medicamento(
    in nombreMedicamento  varchar(100),
    in descripcion  varchar(255),
    in stock int,
    in precioUnitario decimal(10,2),
    in fechaVencimiento date,
    in codigoProveedor int
    )
    Begin	
		Insert into Medicamento(nombreMedicamento, descripcion, stock, precioUnitario, fechaVencimiento, codigoProveedor)
			Values(nombreMedicamento, descripcion, stock, precioUnitario, fechaVencimiento, codigoProveedor);
	End$$
Delimiter ;
call sp_agregar_medicamento('Amoxicilina', 'Antibiótico de amplio espectro', 50, 10.00, '2026-03-01', 1);
call sp_agregar_medicamento('Loperamida', 'Medicamento para la diarrea', 100, 5.50, '2026-06-01', 2);
call sp_agregar_medicamento('Paracetamol', 'Analgésico y antipirético', 200, 3.75, '2027-01-10', 3);
call sp_agregar_medicamento('Ibuprofeno', 'Antiinflamatorio no esteroideo', 150, 6.00, '2026-09-15', 4);
call sp_agregar_medicamento('Doxiciclina', 'Antibiótico para infecciones bacterianas', 120, 8.25, '2026-12-20', 5);
call sp_agregar_medicamento('Dipirona', 'Analgésico, antipirético', 80, 4.50, '2027-02-28', 6);
call sp_agregar_medicamento('Clindamicina', 'Antibiótico para infecciones graves', 40, 12.00, '2026-05-25', 7);
call sp_agregar_medicamento('Prednisona', 'Corticosteroide para inflamaciones', 60, 9.00, '2026-07-30', 8);
call sp_agregar_medicamento('Cetirizina', 'Antihistamínico para alergias', 150, 7.25, '2026-11-12', 9);
call sp_agregar_medicamento('Furosemida', 'Diurético para problemas renales', 90, 5.00, '2027-04-01', 10);
call sp_agregar_medicamento('Metronidazol', 'Antibiótico para infecciones parasitarias', 130, 11.00, '2027-01-05', 11);
call sp_agregar_medicamento('Ranitidina', 'Medicamento para úlceras gástricas', 70, 6.80, '2027-08-20', 12);
call sp_agregar_medicamento('Enalapril', 'Medicamento para la hipertensión', 110, 4.20, '2026-04-14', 13);
call sp_agregar_medicamento('Benzodiazepinas', 'Medicamento ansiolítico', 85, 10.50, '2027-03-25', 14);
call sp_agregar_medicamento('Carbamazepina', 'Antiepiléptico', 95, 13.00, '2026-10-10', 15);

Delimiter $$
	Create procedure sp_agregar_receta(
    in dosis  varchar(100),
    in frecuencia varchar(100),
    in duracionDias int,
    in indicaciones varchar(255),
    in codigoConsulta int,
    in codigoMedicamento int
    )
    Begin	
		Insert into Receta(dosis, frecuencia, duracionDias, indicaciones, codigoConsulta, codigoMedicamento)
			Values(dosis, frecuencia, duracionDias, indicaciones, codigoConsulta, codigoMedicamento);
	End$$
Delimiter ;
call sp_agregar_receta('1 tableta cada 8 horas', '3 veces al día', 7, 'Tomar con comida', 1, 1);
call sp_agregar_receta('1 tableta cada 12 horas', '2 veces al día', 5, 'Tomar con agua', 2, 2);
call sp_agregar_receta('1 cápsula cada 6 horas', '4 veces al día', 10, 'Evitar la exposición al sol', 3, 3);
call sp_agregar_receta('5 mg cada 24 horas', '1 vez al día', 7, 'Tomar en ayunas', 4, 4);
call sp_agregar_receta('50 mg cada 8 horas', '3 veces al día', 14, 'No mezclar con alcohol', 5, 5);
call sp_agregar_receta('1 tableta cada 24 horas', '1 vez al día', 5, 'No exceder la dosis recomendada', 6, 6);
call sp_agregar_receta('10 mg cada 12 horas', '2 veces al día', 7, 'Tomar con comida', 7, 7);
call sp_agregar_receta('25 mg cada 8 horas', '3 veces al día', 10, 'Tomar con líquidos', 8, 8);
call sp_agregar_receta('5 mg cada 6 horas', '4 veces al día', 7, 'Tomar entre comidas', 9, 9);
call sp_agregar_receta('5 ml cada 8 horas', '3 veces al día', 7, 'Aplicar en el área afectada', 10, 10);
call sp_agregar_receta('1 cápsula cada 24 horas', '1 vez al día', 7, 'No tomar junto con lácteos', 11, 11);
call sp_agregar_receta('10 ml cada 12 horas', '2 veces al día', 5, 'Evitar alimentos ricos en grasa', 12, 12);
call sp_agregar_receta('20 mg cada 8 horas', '3 veces al día', 7, 'Tomar antes de las comidas', 13, 13);
call sp_agregar_receta('5 mg cada 24 horas', '1 vez al día', 10, 'No interrumpir el tratamiento', 14, 14);
call sp_agregar_receta('100 mg cada 8 horas', '3 veces al día', 14, 'Refrigerar en un lugar seco', 15, 15);
call sp_agregar_receta('100 mg cada 8 horas', '3 veces al día', 14, 'Refrigerar en un lugar seco', 13, 14);


Delimiter $$
	Create procedure sp_agregar_factura(
    in fechaEmision date,
    in total decimal(10,2),
    in metodoPago varchar(100),
    in codigoCliente int,
    in codigoEmpleado int
    )
    Begin
		Insert into Factura(fechaEmision, total, metodoPago, codigoCliente, codigoEmpleado)
			Values(fechaEmision, total, metodoPago, codigoCliente, codigoEmpleado);
	End$$
Delimiter ;
call sp_agregar_factura('2025-04-01', 150.75, 'Efectivo', 1, 1);
call sp_agregar_factura('2025-04-02', 200.50, 'Tarjeta', 2, 2);
call sp_agregar_factura('2025-04-03', 100.25, 'Transferencia', 3, 3);
call sp_agregar_factura('2025-04-04', 80.00, 'Efectivo', 4, 4);
call sp_agregar_factura('2025-04-05', 120.30, 'Tarjeta', 5, 5);
call sp_agregar_factura('2025-04-06', 250.00, 'Transferencia', 6, 6);
call sp_agregar_factura('2025-04-07', 175.80, 'Efectivo', 7, 7);
call sp_agregar_factura('2025-04-08', 95.60, 'Tarjeta', 8, 8);
call sp_agregar_factura('2025-04-09', 300.40, 'Transferencia', 9, 9);
call sp_agregar_factura('2025-04-10', 140.20, 'Efectivo', 10, 10);
call sp_agregar_factura('2025-04-11', 160.00, 'Tarjeta', 11, 11);
call sp_agregar_factura('2025-04-12', 220.30, 'Transferencia', 12, 12);
call sp_agregar_factura('2025-04-13', 110.50, 'Efectivo', 13, 13);
call sp_agregar_factura('2025-04-14', 180.70, 'Tarjeta', 14, 14);
call sp_agregar_factura('2025-04-15', 250.00, 'Transferencia', 15, 15);


Delimiter $$
	Create Procedure sp_agregar_compra(
    in fechaCompra date,
    in total decimal(10,2),
    in detalle varchar(255),
    in codigoProveedor int
    )
    Begin
		Insert into Compra(fechaCompra, total, detalle, codigoProveedor)
			Values(fechaCompra, total, detalle, codigoProveedor);
	End$$
Delimiter ;
call sp_agregar_compra('2025-04-01', 500.75, 'Compra de medicamentos', 1);
call sp_agregar_compra('2025-04-02', 300.50, 'Compra de equipos veterinarios', 2);
call sp_agregar_compra('2025-04-03', 150.25, 'Compra de insumos varios', 3);
call sp_agregar_compra('2025-04-04', 400.00, 'Compra de productos alimenticios para mascotas', 4);
call sp_agregar_compra('2025-04-05', 250.30, 'Compra de vacunas', 5);
call sp_agregar_compra('2025-04-06', 350.00, 'Compra de productos de higiene', 6);
call sp_agregar_compra('2025-04-07', 450.80, 'Compra de medicamentos y suplementos', 7);
call sp_agregar_compra('2025-04-08', 550.60, 'Compra de equipos de diagnóstico', 8);
call sp_agregar_compra('2025-04-09', 600.40, 'Compra de suministros de oficina', 9);
call sp_agregar_compra('2025-04-10', 700.20, 'Compra de alimentos y juguetes', 10);
call sp_agregar_compra('2025-04-11', 250.10, 'Compra de productos de limpieza', 11);
call sp_agregar_compra('2025-04-12', 500.30, 'Compra de materiales de construcción', 12);
call sp_agregar_compra('2025-04-13', 350.40, 'Compra de equipos para cirugía', 13);
call sp_agregar_compra('2025-04-14', 600.00, 'Compra de medicamentos veterinarios', 14);
call sp_agregar_compra('2025-04-15', 450.70, 'Compra de camas y accesorios para mascotas', 15);


-- ListarCliente
Delimiter $$
	Create Procedure sp_listar_cliente()
    Begin
		Select * From Cliente;
	End$$
Delimiter ;
call sp_listar_cliente();

Delimiter $$
	Create Procedure sp_listar_veterinario()
    Begin
		Select * From Veterinario;
	End$$
Delimiter ;
call sp_listar_veterinario();

Delimiter $$
	Create Procedure sp_listar_proveedor()
    Begin
		Select * From Proveedor;
	End$$
Delimiter ;
call sp_listar_proveedor();

Delimiter $$
	Create Procedure sp_listar_vacuna()
    Begin
		Select * From Vacuna;
	End$$
Delimiter ;
call sp_listar_vacuna();

Delimiter $$
	Create Procedure sp_listar_empleado()
    Begin
		Select * From Empleado;
	End$$
Delimiter ;
call sp_listar_empleado();

Delimiter $$
	Create Procedure sp_listar_mascota()
    Begin
		Select * From Mascota;
	End$$
Delimiter ;
call sp_listar_mascota();

Delimiter $$
	Create Procedure sp_listar_consulta()
    Begin
		Select * From Consulta;
	End$$
Delimiter ;
call sp_listar_consulta();

Delimiter $$
	Create Procedure sp_listar_tratamiento()
    Begin
		Select * From Tratamiento;
	End$$
Delimiter ;
call sp_listar_tratamiento();

Delimiter $$
	Create Procedure sp_listar_cita()
    Begin
		Select * From Cita;
	End$$
Delimiter ;
call sp_listar_cita();

Delimiter $$
	Create Procedure sp_listar_vacunacion()
    Begin
		Select * From Vacunacion;
	End$$
Delimiter ;
call sp_listar_vacunacion();

Delimiter $$
	Create Procedure sp_listar_medicamento()
    Begin
		Select * From Medicamento;
	End$$
Delimiter ;
call sp_listar_medicamento();

Delimiter $$
	Create Procedure sp_listar_receta()
    Begin
		Select * From Receta;
	End$$
Delimiter ;
call sp_listar_receta();

Delimiter $$
	Create Procedure sp_listar_factura()
    Begin
		Select * From Factura;
	End$$
Delimiter ;
call sp_listar_factura();

Delimiter $$
	Create Procedure sp_listar_compra()
    Begin
		Select * From Compra;
	End$$
Delimiter ;
call sp_listar_compra();

-- Eliminar Cliente

Delimiter $$
	Create procedure sp_eliminar_vacunacion(in codigoVacu int)
    Begin 
		Delete From Vacunacion
			where codigoVacu = codigoVacunacion;
	End$$
Delimiter ;
call sp_eliminar_vacunacion(3);

Delimiter $$
	Create procedure sp_eliminar_receta(in codigoRecet int)
    Begin 
		Delete From Receta
			where codigoRecet = codigoReceta;
	End$$
Delimiter ;
call sp_eliminar_receta(3);

Delimiter $$
	Create procedure sp_eliminar_tratamiento(in codigoTrata int)
    Begin 
		Delete From Tratamiento
			where codigoTrata = codigoTratamiento;
	End$$
Delimiter ;
call sp_eliminar_tratamiento(3);

Delimiter $$
	Create procedure sp_eliminar_cita(in codigoCit int)
    Begin 
		Delete From Cita
			where codigoCit = codigoCita;
	End$$
Delimiter ;
call sp_eliminar_cita(3);

Delimiter $$
	Create procedure sp_eliminar_consulta(in codigoConsult int)
    Begin 
		Delete From Consulta
			where codigoConsult = codigoConsulta;
	End$$
Delimiter ;
call sp_eliminar_consulta(3);

Delimiter $$
	Create procedure sp_eliminar_mascota(in codigoMasc int)
    Begin 
		Delete From Mascota
			where codigoMasc = codigoMascota;
	End$$
Delimiter ;
call sp_eliminar_mascota(3);

Delimiter $$
	Create procedure sp_eliminar_factura(in codigoFac int)
    Begin 
		Delete From Factura
			where codigoFac = codigoFactura;
	End$$
Delimiter ;
call sp_eliminar_factura(3);

Delimiter $$
	Create procedure sp_eliminar_compra(in codigoCom int)
    Begin 
		Delete From Compra
			where codigoCom = codigoCompra;
	End$$
Delimiter ;
call sp_eliminar_compra(3);

Delimiter $$
	Create procedure sp_eliminar_medicamento(in codigoMed int)
    Begin 
		Delete From Medicamento
			where codigoMed = codigoMedicamento;
	End$$
Delimiter ;
call sp_eliminar_medicamento(3);

Delimiter $$
	Create procedure sp_eliminar_empleado(in codigoEmp int)
    Begin 
		Delete From Empleado
			where codigoEmp = codigoEmpleado;
	End$$
Delimiter ;
call sp_eliminar_empleado(3);

Delimiter $$
	Create procedure sp_eliminar_proveedor(in codigoProve int)
    Begin 
		Delete From Proveedor
			where codigoProve = codigoProveedor;
	End$$
Delimiter ;
call sp_eliminar_proveedor(3);

Delimiter $$
	Create procedure sp_eliminar_veterinario(in codigoVet int)
    Begin 
		Delete From Veterinario
			where codigoVet = codigoVeterinario;
	End$$
Delimiter ;
call sp_eliminar_veterinario(3);

Delimiter $$
	Create procedure sp_eliminar_cliente(in codigoCli int)
    Begin 
		Delete From Cliente
			where codigoCli = codigoCliente;
	End$$
Delimiter ;
call sp_eliminar_cliente(3);

Delimiter $$
	Create procedure sp_eliminar_vacuna(in codigoVac int)
    Begin 
		Delete From Vacuna
			where codigoVac = codigoVacuna;
	End$$
Delimiter ;
call sp_eliminar_vacuna(3);


-- Buscar Cliente
Delimiter $$
	Create procedure sp_buscar_cliente(in codigoC int)
    Begin 
		Select * From Cliente
			where codigoC = codigoCliente;
	End$$
Delimiter ;
call sp_buscar_cliente(3);

Delimiter $$
	Create procedure sp_buscar_veterinario(in codigoVet int)
    Begin 
		Select * From Veterinario
			where codigoVet = codigoVeterinario;
	End$$
Delimiter ;
call sp_buscar_veterinario(3);

Delimiter $$
	Create procedure sp_buscar_proveedor(in codigoProv int)
    Begin 
		Select * From Proveedor
			where codigoProv = codigoProveedor;
	End$$
Delimiter ;
call sp_buscar_proveedor(3);

Delimiter $$
	Create procedure sp_buscar_vacuna(in codigoVac int)
    Begin 
		Select * From Vacuna
			where codigoVac = codigoVacuna;
	End$$
Delimiter ;
call sp_buscar_vacuna(3);

Delimiter $$
	Create procedure sp_buscar_empleado(in codigoEmp int)
    Begin 
		Select * From Empleado
			where codigoEmp = codigoEmpleado;
	End$$
Delimiter ;
call sp_buscar_empleado(3);

Delimiter $$
	Create procedure sp_buscar_mascota(in codigoMasc int)
    Begin 
		Select * From Mascota
			where codigoMasc = codigoMascota;
	End$$
Delimiter ;
call sp_buscar_mascota(3);

Delimiter $$
	Create procedure sp_buscar_consulta(in codigoCons int)
    Begin 
		Select * From Consulta
			where codigoCons = codigoConsulta;
	End$$
Delimiter ;
call sp_buscar_consulta(3);

Delimiter $$
	Create procedure sp_buscar_tratamiento(in codigoTrat int)
    Begin 
		Select * From Tratamiento
			where codigoTrat = codigoTratamiento;
	End$$
Delimiter ;
call sp_buscar_tratamiento(3);

Delimiter $$
	Create procedure sp_buscar_cita(in codigoCit int)
    Begin 
		Select * From Cita
			where codigoCit = codigoCIta;
	End$$
Delimiter ;
call sp_buscar_cita(3);

Delimiter $$
	Create procedure sp_buscar_vacunacion(in codigoVac int)
    Begin 
		Select * From Vacunacion
			where codigoVac = codigoVacunacion;
	End$$
Delimiter ;
call sp_buscar_vacunacion(3);

Delimiter $$
	Create procedure sp_buscar_medicamento(in codigoMed int)
    Begin 
		Select * From Medicamento
			where codigoMed = codigoMedicamento;
	End$$
Delimiter ;
call sp_buscar_medicamento(3);

Delimiter $$
	Create procedure sp_buscar_receta(in codigoRec int)
    Begin 
		Select * From Receta
			where codigoRec = codigoReceta;
	End$$
Delimiter ;
call sp_buscar_receta(3);

Delimiter $$
	Create procedure sp_buscar_factura(in codigoFac int)
    Begin 
		Select * From Factura
			where codigoFac = codigoFactura;
	End$$
Delimiter ;
call sp_buscar_factura(3);

Delimiter $$
	Create procedure sp_buscar_compra(in codigoComp int)
    Begin 
		Select * From Compra
			where codigoComp = codigoCompra;
	End$$
Delimiter ;
call sp_buscar_compra(3);

-- Editar Cliente 
Delimiter $$
	Create procedure sp_actualizar_cliente(
    in codigoCliente int,
    in nombreCliente varchar(100),
    in apellidoCliente varchar(50),
    in telefonoCliente varchar(20),
    in direccionCliente varchar(255),
    in emailCliente varchar(100),
    in fechaRegistro date
    )
    Begin
		Update Cliente C 
			set
            C.codigoCliente = codigoCliente,
            C.nombreCliente = nombreCliente,
            C.apellidoCliente = apellidoCliente,
			C.telefonoCliente = telefonoCliente,
            C.direccionCliente = direccionCliente,
            C.emailCliente = emailCliente,
            C.fechaRegistro = fechaRegistro
            where
            C.codigoCliente = codigoCliente;
	End$$
Delimiter ;
call sp_actualizar_cliente('1','Diego','Tzuquen','42125486','Zona 9 Ciudad de Guatemala','diegotz@gmail.com','2024-12-31');

    
Delimiter $$
	create procedure sp_actualizar_veterinario(
    in codigoVeterinario int,
    in nombreVeterinario varchar(50),
    in apellidoVeterinario varchar(50),
    in especialidad varchar(100),
    in telefonoVeterinario varchar(8),
    in correoVeterinario varchar(100),
    in fechaIngreso date,
    in estado varchar(100)
    )
    begin
		update Veterinario V
			set
            V.nombreVeterinario = nombreVeterinario,
            V.apellidoVeterinario = apellidoVeterinario,
            V.especialidad = especialidad,
            V.telefonoVeterinario = telefonoVeterinario,
            V.correoVeterinario = correoVeterinario,
            V.fechaIngreso = fechaIngreso,
            V.estado = estado
            where V.codigoVeterinario = codigoVeterinario;
	end$$
Delimiter ;
call sp_actualizar_veterinario('1','Juan','Jimenez','Cirujano','87878888','juan@gmail.com','2024-12-01','Activo');

Delimiter $$
	create procedure sp_actualizar_proveedor(
    in codigoProveedor int,
    in nombreProveedor varchar(100),
    in direccionProveedor varchar(100),
    in telefonoProveedor varchar(20),
    in correoProveedor varchar(100)
    )
    begin
		update Proveedor P
			set
            P.nombreProveedor = nombreProveedor,
            P.direccionProveedor = direccionProveedor,
            P.telefonoProveedor = telefonoProveedor,
            P.correoProveedor = correoProveedor
            where P.codigoProveedor = codigoProveedor;
	end$$
Delimiter ;
call sp_actualizar_proveedor('1','Distribuidora Vet','Zona 1','55552222','proveedor@vet.com');

Delimiter $$
	create procedure sp_actualizar_vacuna(
    in codigoVacuna int,
    in nombreVacuna varchar(100),
    in descripcion varchar(255),
    in dosis varchar(100),
    in frecuenciaMeses int
    )
    begin
		update Vacuna V
			set
            V.nombreVacuna = nombreVacuna,
            V.descripcion = descripcion,
            V.dosis = dosis,
            V.frecuenciaMeses = frecuenciaMeses
            where V.codigoVacuna = codigoVacuna;
	end$$
Delimiter ;
call sp_actualizar_vacuna('1','Antirrábica','Protección contra rabia','1 dosis','12');


Delimiter $$
	create procedure sp_actualizar_empleado(
    in codigoEmpleado int,
    in nombreEmpleado varchar(50),
    in apellidoEmpleado varchar(50),
    in cargo varchar(50),
    in telefonoEmpleado varchar(20),
    in correoEmpleado varchar(100)
    )
    begin
		update Empleado E
			set
            E.nombreEmpleado = nombreEmpleado,
            E.apellidoEmpleado = apellidoEmpleado,
            E.cargo = cargo,
            E.telefonoEmpleado = telefonoEmpleado,
            E.correoEmpleado = correoEmpleado
            where E.codigoEmpleado = codigoEmpleado;
	end$$
Delimiter ;
call sp_actualizar_empleado('1','Luis','Perez','Recepcionista','44556677','luis@vet.com');


Delimiter $$
	create procedure sp_actualizar_mascota(
    in codigoMascota int,
    in nombreMascota varchar(50),
    in especie varchar(30),
    in raza varchar(50),
    in sexo varchar(100),
    in fechaNacimiento date,
    in color varchar(30),
    in pesoActualKg decimal(5,2),
    in codigoCliente int
    )
    begin
		update Mascota M
			set
            M.nombreMascota = nombreMascota,
            M.especie = especie,
            M.raza = raza,
            M.sexo = sexo,
            M.fechaNacimiento = fechaNacimiento,
            M.color = color,
            M.pesoActualKg = pesoActualKg,
            M.codigoCliente = codigoCliente
            where M.codigoMascota = codigoMascota;
	end$$
Delimiter ;
call sp_actualizar_mascota('1','Max','Perro','Labrador','Macho','2022-05-10','Negro','25.5','1');


Delimiter $$
	create procedure sp_actualizar_consulta(
    in codigoConsulta int,
    in fechaConsulta datetime,
    in motivo varchar(255),
    in diagnostico varchar(255),
    in observaciones varchar(255),
    in codigoMascota int,
    in codigoVeterinario int
    )
    begin
		update Consulta C
			set
            C.fechaConsulta = fechaConsulta,
            C.motivo = motivo,
            C.diagnostico = diagnostico,
            C.observaciones = observaciones,
            C.codigoMascota = codigoMascota,
            C.codigoVeterinario = codigoVeterinario
            where C.codigoConsulta = codigoConsulta;
	end$$
Delimiter ;
call sp_actualizar_consulta('1','2025-04-28 10:30:00','Revisión general','Salud óptima','Sin observaciones','1','1');


Delimiter $$
	create procedure sp_actualizar_tratamiento(
    in codigoTratamiento int,
    in descripcion varchar(255),
    in fechaInicio date,
    in fechaFin date,
    in medicamentosIndicados varchar(255),
    in codigoConsulta int
    )
    begin
		update Tratamiento T
			set
            T.descripcion = descripcion,
            T.fechaInicio = fechaInicio,
            T.fechaFin = fechaFin,
            T.medicamentosIndicados = medicamentosIndicados,
            T.codigoConsulta = codigoConsulta
            where T.codigoTratamiento = codigoTratamiento;
	end$$
Delimiter ;
call sp_actualizar_tratamiento('1','Tratamiento de desparasitación','2025-05-01','2025-05-10','Albendazol 500mg','1');


Delimiter $$
	create procedure sp_actualizar_cita(
    in codigoCita int,
    in fechaCita date,
    in horaCita time,
    in motivo varchar(255),
    in estado varchar(100),
    in codigoMascota int,
    in codigoVeterinario int
    )
    begin
		update Cita C
			set
            C.fechaCita = fechaCita,
            C.horaCita = horaCita,
            C.motivo = motivo,
            C.estado = estado,
            C.codigoMascota = codigoMascota,
            C.codigoVeterinario = codigoVeterinario
            where C.codigoCita = codigoCita;
	end$$
Delimiter ;
call sp_actualizar_cita('1','2025-05-15','09:00:00','Vacunación anual','Pendiente','1','1');


Delimiter $$
	create procedure sp_actualizar_vacunacion(
    in codigoVacunacion int,
    in fechaAplicacion date,
    in observaciones varchar(255),
    in codigoMascota int,
    in codigoVacuna int,
    in codigoVeterinario int
    )
    begin
		update Vacunacion V
			set
            V.fechaAplicacion = fechaAplicacion,
            V.observaciones = observaciones,
            V.codigoMascota = codigoMascota,
            V.codigoVacuna = codigoVacuna,
            V.codigoVeterinario = codigoVeterinario
            where V.codigoVacunacion = codigoVacunacion;
	end$$
Delimiter ;
call sp_actualizar_vacunacion('1','2025-06-01','Aplicación exitosa','1','1','1');


Delimiter $$
	create procedure sp_actualizar_medicamento(
    in codigoMedicamento int,
    in nombreMedicamento varchar(100),
    in descripcion varchar(255),
    in stock int,
    in precioUnitario decimal(10,2),
    in fechaVencimiento date,
    in codigoProveedor int
    )
    begin
		update Medicamento M
			set
            M.nombreMedicamento = nombreMedicamento,
            M.descripcion = descripcion,
            M.stock = stock,
            M.precioUnitario = precioUnitario,
            M.fechaVencimiento = fechaVencimiento,
            M.codigoProveedor = codigoProveedor
            where M.codigoMedicamento = codigoMedicamento;
	end$$
Delimiter ;
call sp_actualizar_medicamento('1','Ivermectina','Antiparasitario','150','12.50','2026-01-01','1');


Delimiter $$
	create procedure sp_actualizar_receta(
    in codigoReceta int,
    in dosis varchar(100),
    in frecuencia varchar(100),
    in duracionDias int,
    in indicaciones varchar(255),
    in codigoConsulta int,
    in codigoMedicamento int
    )
    begin
		update Receta R
			set
            R.dosis = dosis,
            R.frecuencia = frecuencia,
            R.duracionDias = duracionDias,
            R.indicaciones = indicaciones,
            R.codigoConsulta = codigoConsulta,
            R.codigoMedicamento = codigoMedicamento
            where R.codigoReceta = codigoReceta;
	end$$
Delimiter ;
call sp_actualizar_receta('1','2 tabletas','Cada 12 horas','7','Tomar después de comer','1','1');


Delimiter $$
	create procedure sp_actualizar_factura(
    in codigoFactura int,
    in fechaEmision date,
    in total decimal(10,2),
    in metodoPago varchar(100),
    in codigoCliente int,
    in codigoEmpleado int
    )
    begin
		update Factura F
			set
            F.fechaEmision = fechaEmision,
            F.total = total,
            F.metodoPago = metodoPago,
            F.codigoCliente = codigoCliente,
            F.codigoEmpleado = codigoEmpleado
            where F.codigoFactura = codigoFactura;
	end$$
Delimiter ;
call sp_actualizar_factura('1','2025-04-28','250.00','Efectivo','1','1');


Delimiter $$
	create procedure sp_actualizar_compra(
    in codigoCompra int,
    in fechaCompra date,
    in total decimal(10,2),
    in detalle varchar(255),
    in codigoProveedor int
    )
    begin
		update Compra C
			set
            C.fechaCompra = fechaCompra,
            C.total = total,
            C.detalle = detalle,
            C.codigoProveedor = codigoProveedor
            where C.codigoCompra = codigoCompra;
	end$$
Delimiter ;
call sp_actualizar_compra('1','2025-04-25','500.00','Compra de vacunas','1');

call sp_listar_proveedor();

call sp_listar_veterinario();

call sp_listar_vacunacion();

select * from vacunacion;