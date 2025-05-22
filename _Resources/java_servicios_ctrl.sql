/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MariaDB
 Source Server Version : 100432 (10.4.32-MariaDB)
 Source Host           : localhost:3306
 Source Schema         : java_servicios_ctrl

 Target Server Type    : MariaDB
 Target Server Version : 100432 (10.4.32-MariaDB)
 File Encoding         : 65001

 Date: 21/05/2025 20:27:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for clientes
-- ----------------------------
DROP TABLE IF EXISTS `clientes`;
CREATE TABLE `clientes`  (
  `IdCliente` int(10) NOT NULL,
  `NombreCliente` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `NombreContacto` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `Direccion` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `Colonia` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `CP` int(5) NOT NULL,
  `Ciudad` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `Telefono` int(10) NOT NULL,
  `Extension` int(3) NOT NULL,
  `Fax` int(10) NOT NULL,
  `Email` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `fecha_alta` date NOT NULL,
  `TipoCliente` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `Activo` tinyint(1) NOT NULL,
  `Notas` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  PRIMARY KEY (`IdCliente`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of clientes
-- ----------------------------
INSERT INTO `clientes` VALUES (1, 'Senergy II', 'Fernando', 'la misma', 'Centro', 81660, 'Villahermosa', 992233445, 1, 111, 'x.com', '2025-05-21', 'Residencial', 1, 'cliente premium');
INSERT INTO `clientes` VALUES (12, 'Omni gas S. A. de C. V.', 'Jose de Jesus Montero', 'Col. 5 Mayo, Huimanguillo, Tabasco', 'Col. Centro', 86400, 'Huimanguillo, Tabasco', 917113416, 0, 0, 'ros90@hotmail.com', '2011-01-27', 'Residencial', 1, 'Este es el primer cliente de prueba unitaria\nde la empresa');

-- ----------------------------
-- Table structure for detallesmanoobra
-- ----------------------------
DROP TABLE IF EXISTS `detallesmanoobra`;
CREATE TABLE `detallesmanoobra`  (
  `IdDetalle` int(10) NOT NULL,
  `IdServicio` int(10) NOT NULL,
  `IdManoObra` int(10) NOT NULL,
  `IdCantidad` int(10) NOT NULL,
  PRIMARY KEY (`IdDetalle`) USING BTREE,
  INDEX `IdServicio`(`IdServicio`) USING BTREE,
  INDEX `IdManoObra`(`IdManoObra`) USING BTREE,
  CONSTRAINT `detallesmanoobra_ibfk_1` FOREIGN KEY (`IdServicio`) REFERENCES `servicios` (`IdOrden`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `detallesmanoobra_ibfk_2` FOREIGN KEY (`IdManoObra`) REFERENCES `manoobra` (`IdManoObra`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of detallesmanoobra
-- ----------------------------
INSERT INTO `detallesmanoobra` VALUES (11, 1, 1, 2);
INSERT INTO `detallesmanoobra` VALUES (12, 1, 2, 1);
INSERT INTO `detallesmanoobra` VALUES (21, 2, 1, 1);

-- ----------------------------
-- Table structure for detallesrefacciones
-- ----------------------------
DROP TABLE IF EXISTS `detallesrefacciones`;
CREATE TABLE `detallesrefacciones`  (
  `IdDetalle` int(10) NOT NULL,
  `IdServicio` int(10) NOT NULL,
  `IdRefaccion` int(10) NOT NULL,
  `IdCantidad` int(10) NOT NULL,
  PRIMARY KEY (`IdDetalle`) USING BTREE,
  INDEX `IdServicio`(`IdServicio`) USING BTREE,
  INDEX `IdRefaccion`(`IdRefaccion`) USING BTREE,
  CONSTRAINT `detallesrefacciones_ibfk_1` FOREIGN KEY (`IdServicio`) REFERENCES `servicios` (`IdOrden`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `detallesrefacciones_ibfk_2` FOREIGN KEY (`IdRefaccion`) REFERENCES `refacciones` (`IdRefaccion`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of detallesrefacciones
-- ----------------------------
INSERT INTO `detallesrefacciones` VALUES (11, 1, 1, 2);
INSERT INTO `detallesrefacciones` VALUES (13, 1, 3, 1);
INSERT INTO `detallesrefacciones` VALUES (23, 2, 3, 2);

-- ----------------------------
-- Table structure for empleados
-- ----------------------------
DROP TABLE IF EXISTS `empleados`;
CREATE TABLE `empleados`  (
  `IdEmpleado` int(10) NOT NULL,
  `Nombre` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `Comision` decimal(12, 0) NOT NULL,
  `Status` tinyint(1) NOT NULL,
  `Sbase` decimal(10, 0) NOT NULL,
  `PSbase` decimal(10, 0) NOT NULL,
  `ObjetivoM1` decimal(10, 0) NOT NULL,
  `ObjetivoM2` decimal(10, 0) NOT NULL,
  PRIMARY KEY (`IdEmpleado`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of empleados
-- ----------------------------
INSERT INTO `empleados` VALUES (1, 'Jose Ramirez Usan', 57, 1, 56, 566, 2, 600);
INSERT INTO `empleados` VALUES (2, 'Raul NArcizo Rovirosa', 56, 1, 56, 555, 666, 558);
INSERT INTO `empleados` VALUES (3, 'Geronimo madrigal', 2, 1, 1000, 22000, 2, 5000);

-- ----------------------------
-- Table structure for equipos
-- ----------------------------
DROP TABLE IF EXISTS `equipos`;
CREATE TABLE `equipos`  (
  `IdEquipo` int(10) NOT NULL,
  `IdCliente` int(10) NOT NULL,
  `Marca` int(10) NOT NULL,
  `Modelo` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `NSerie` int(12) NOT NULL,
  `NInvent` int(12) NOT NULL,
  `TipoEquipo` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `telefono` int(12) NOT NULL,
  `Caracteristica` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `Ult_servicio` date NOT NULL,
  `Prox_servicio` date NOT NULL,
  PRIMARY KEY (`IdEquipo`) USING BTREE,
  INDEX `IdCliente`(`IdCliente`) USING BTREE,
  INDEX `Marca`(`Marca`) USING BTREE,
  CONSTRAINT `equipos_ibfk_1` FOREIGN KEY (`IdCliente`) REFERENCES `clientes` (`IdCliente`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `equipos_ibfk_2` FOREIGN KEY (`Marca`) REFERENCES `marcas` (`IdMarca`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of equipos
-- ----------------------------
INSERT INTO `equipos` VALUES (1, 12, 1, 'Laptop VAIO', 2323, 1212, 'lap', 0, 'No relevante', '9999-01-01', '9999-01-01');
INSERT INTO `equipos` VALUES (2, 1, 2, 'XCDS22', 55565, 5665, 'laptop', 55556, 'bueno', '2025-05-21', '2025-05-21');

-- ----------------------------
-- Table structure for manoobra
-- ----------------------------
DROP TABLE IF EXISTS `manoobra`;
CREATE TABLE `manoobra`  (
  `IdManoObra` int(10) NOT NULL,
  `Descripcion` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `Importe` decimal(12, 0) NOT NULL,
  `ImporteTecnico` decimal(12, 0) NOT NULL,
  PRIMARY KEY (`IdManoObra`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of manoobra
-- ----------------------------
INSERT INTO `manoobra` VALUES (1, 'Un nuevo trabajo.', 130, 30);
INSERT INTO `manoobra` VALUES (2, 'Limpieza laptop', 1000, 100);

-- ----------------------------
-- Table structure for marcas
-- ----------------------------
DROP TABLE IF EXISTS `marcas`;
CREATE TABLE `marcas`  (
  `IdMarca` int(10) NOT NULL,
  `Marcas` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  PRIMARY KEY (`IdMarca`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of marcas
-- ----------------------------
INSERT INTO `marcas` VALUES (1, 'Sony');
INSERT INTO `marcas` VALUES (2, 'Samsumg');

-- ----------------------------
-- Table structure for refacciones
-- ----------------------------
DROP TABLE IF EXISTS `refacciones`;
CREATE TABLE `refacciones`  (
  `IdRefaccion` int(10) NOT NULL,
  `Descripcion` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `Importe` decimal(12, 0) NOT NULL,
  PRIMARY KEY (`IdRefaccion`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of refacciones
-- ----------------------------
INSERT INTO `refacciones` VALUES (1, 'Refaccion 2', 1000);
INSERT INTO `refacciones` VALUES (3, 'Refaccion 1', 33);

-- ----------------------------
-- Table structure for servicios
-- ----------------------------
DROP TABLE IF EXISTS `servicios`;
CREATE TABLE `servicios`  (
  `IdOrden` int(10) NOT NULL,
  `IdCliente` int(10) NOT NULL,
  `FEntra` datetime NOT NULL,
  `FProgramada` datetime NOT NULL,
  `HProgramada` datetime NOT NULL,
  `FSale` datetime NOT NULL,
  `HEntra` datetime NOT NULL,
  `HInicia` datetime NOT NULL,
  `HSale` datetime NOT NULL,
  `TipoServicio` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `Mantto` tinyint(1) NOT NULL,
  `Reparacion` tinyint(1) NOT NULL,
  `Diagnostico` tinyint(1) NOT NULL,
  `Instalacion` tinyint(1) NOT NULL,
  `Garantia` tinyint(1) NOT NULL,
  `Domicilio` tinyint(1) NOT NULL,
  `Actualizacion` tinyint(1) NOT NULL,
  `Configuracion` tinyint(1) NOT NULL,
  `Asesoria` tinyint(1) NOT NULL,
  `Remoto` tinyint(1) NOT NULL,
  `Estatus` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `Equipo` int(10) NOT NULL,
  `Falla` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `Accesorios` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `Diagnostic` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `EntregaE` int(10) NOT NULL,
  `AtendioE` int(10) NOT NULL,
  `RecibioE` int(10) NOT NULL,
  `Total` decimal(12, 0) NOT NULL,
  `IServicio` decimal(12, 0) NOT NULL,
  `Factura` int(10) NOT NULL,
  `Comision` decimal(12, 0) NOT NULL,
  `PagoCom` tinyint(1) NOT NULL,
  `FPago` date NOT NULL,
  `Documento` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `Departamento` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  PRIMARY KEY (`IdOrden`) USING BTREE,
  INDEX `IdCliente`(`IdCliente`) USING BTREE,
  INDEX `EntregaE`(`EntregaE`) USING BTREE,
  INDEX `AtendioE`(`AtendioE`) USING BTREE,
  INDEX `RecibioE`(`RecibioE`) USING BTREE,
  INDEX `Equipo`(`Equipo`) USING BTREE,
  CONSTRAINT `servicios_ibfk_1` FOREIGN KEY (`IdCliente`) REFERENCES `clientes` (`IdCliente`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `servicios_ibfk_2` FOREIGN KEY (`EntregaE`) REFERENCES `empleados` (`IdEmpleado`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `servicios_ibfk_3` FOREIGN KEY (`AtendioE`) REFERENCES `empleados` (`IdEmpleado`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `servicios_ibfk_4` FOREIGN KEY (`RecibioE`) REFERENCES `empleados` (`IdEmpleado`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `servicios_ibfk_5` FOREIGN KEY (`Equipo`) REFERENCES `equipos` (`IdEquipo`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of servicios
-- ----------------------------
INSERT INTO `servicios` VALUES (1, 12, '2011-01-27 00:00:00', '9999-01-01 00:00:00', '9999-01-01 00:00:00', '2011-01-27 00:00:00', '2000-01-01 12:00:00', '2000-01-01 07:00:00', '2000-01-01 09:00:00', 'Domicilio', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 'Pendiente', 1, '', '', '', 1, 1, 1, 0, 0, 0, 0, 0, '2011-01-27', '', '');
INSERT INTO `servicios` VALUES (2, 12, '2025-05-21 00:00:00', '2025-05-21 00:00:00', '2000-01-01 00:00:00', '2025-05-21 00:00:00', '2000-01-01 00:00:00', '2000-01-01 00:00:00', '2000-01-01 00:00:00', 'Domicilio', 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 'Pagado', 1, '', '', '', 1, 2, 1, 100, 100, 555, 20, 1, '2025-05-21', 'fff', '');

SET FOREIGN_KEY_CHECKS = 1;
