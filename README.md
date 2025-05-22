# Sistema de Administración Órdenes de servicios
Este proyecto consiste en una aplicación de escritorio desarrollada en Java, diseñada para gestionar y automatizar las operaciones internas de una empresa del sector de servicios informáticos. La solución permite llevar un control preciso de las actividades realizadas, centralizando la información de clientes, empleados, equipos electrónicos, mano de obra y refacciones.

La aplicación inicia con una pantalla de carga (splash screen) que prepara los componentes del sistema antes de mostrar la ventana principal, desde la cual se accede a todas las funcionalidades. Su interfaz intuitiva y estructura modular facilitan la operación por parte del personal administrativo y técnico.

## Características Principales

- **Gestión de datos**: Registro y administración de clientes, empleados, equipos electrónicos, refacciones y servicios de mano de obra.
- **Órdenes de servicio**: Generación, administración y seguimiento de órdenes de servicio, asociando los datos previamente registrados.
- **Documentación de servicio**: Creación automática de formatos de orden de servicio, listos para impresión o entrega digital como comprobante al cliente.
- **Reportes personalizados**: Generación de seis tipos distintos de reportes, con opciones para exportarlos en múltiples formatos como PDF, HTML y DOCX.
- **Configuración de base de datos**: Herramientas integradas para configurar y gestionar la conexión con el servidor de base de datos de forma sencilla.

## Tecnologías Utilizadas

- Java SE
- Swing/AWT (Interfaz gráfica)
- JDBC (Conexión a base de datos)
- Reportes exportables (PDF, HTML, DOCX)

## ⚙️ Información Técnica

- El código fue originalmente desarrollado en **2009** .
- Ha sido **actualizado y revisado para ser compatible con Java 24 (JDK 24)**.
- Se utiliza adicionalmente: Jasper Reports y el conector Mysql.

## 🧰 Requisitos

- **Java Development Kit (JDK) 24 o superior**
- Sistema operativo con soporte para Java (Windows, macOS o Linux)

## 🚀 Cómo usar

1. Clona este repositorio:
   ```bash
   git clone https://github.com/aldomontero/java_servicios.git
   cd nombre-del-repositorio
   ```

2. Compila los archivos fuente:
   ```bash
   javac <nombre_del_archivo_clase>
   ```

3. Ejecuta la aplicación:
   ```bash
   java <nombre_de_clase>
   ```

> Asegúrate de tener todos los archivos `.java` necesarios en el mismo directorio o configurados correctamente en tu IDE.

## Capturas de pantalla

- Pantalla principal.
![1.Pantalla inicio.png](_Screenshots/1.Pantalla%20inicio.png)
- Administración de clientes.
![2. Admin clientes.png](_Screenshots/2.%20Admin%20clientes.png)
- Registro de servicios.
![3. Registro servicios.png](_Screenshots/3.%20Registro%20servicios.png)
- Visualización de orden de servicio.
![4. Orden de servicio.png](_Screenshots/4.%20Orden%20de%20servicio.png)
- Parametrización de reportes.
![5. Reporte cliente.png](_Screenshots/5.%20Reporte%20cliente.png)
- Visualización de reportes.
![6. Reportes.png](_Screenshots/6.%20Reportes.png)

## Exención de Responsabilidad

Este software se proporciona "tal cual", sin garantías de ningún tipo, expresas o implícitas, incluidas, entre otras, las garantías de comerciabilidad, idoneidad para un propósito particular y no infracción.

El autor no será responsable de ningún daño derivado del uso o la imposibilidad de uso de este software, incluyendo, entre otros, la pérdida de datos, interrupción del negocio o cualquier otro daño, ya sea en una acción contractual, negligencia u otra acción legal.

El uso de este software es bajo su propio riesgo. Al utilizarlo, usted acepta eximir al autor de cualquier responsabilidad legal.
