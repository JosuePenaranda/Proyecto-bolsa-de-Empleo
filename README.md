## 💼 Proyecto – Sistema Web Bolsa de Empleo

EIF – Desarrollo Web
Universidad Nacional – Escuela de Informática

---

## 🧩 Descripción General

Este proyecto consiste en el desarrollo de un sistema web para la gestión de una bolsa de empleo, donde empresas pueden publicar puestos de trabajo y personas oferentes pueden registrar sus habilidades para aplicar a ellos.

El sistema permite almacenar y gestionar la información utilizando una base de datos MySQL, y está desarrollado en Java utilizando el framework Spring bajo el patrón MVC (Server Side Rendering).

El objetivo principal es facilitar la conexión entre empresas que buscan talento y personas que ofrecen sus servicios, mostrando el grado de coincidencia entre los requerimientos de los puestos y las habilidades de los candidatos.

## ⚠️ Restricción importante:
El proyecto debe implementarse usando Server Side Rendering con Spring MVC, por lo tanto NO se permite el uso de JavaScript.

---

## ⚙️ Funcionalidades del Sistema
## 🌐 Parte Pública

Disponible para cualquier visitante del sitio.

Registro de empresas en el sistema.

Registro de oferentes (personas).

Visualización de los 5 puestos públicos más recientes.

Visualización de detalle del puesto al posicionar el cursor.

Búsqueda de puestos públicos según características específicas.

## 🏢 Funcionalidades para Empresas

Las empresas registradas y autorizadas podrán:

## 📋 Gestión de Empresa

Registrar información de la empresa:

Nombre

Localización

Correo electrónico

Teléfono

Descripción

## 🔐 Acceso al Sistema

Iniciar sesión mediante correo electrónico y contraseña.

Acceder a su dashboard o panel de control.

## 💼 Gestión de Puestos

Publicar puestos de trabajo indicando:

Descripción del puesto

Características requeridas

Nivel deseado de cada característica

Salario ofrecido

Tipo de publicación (pública o privada)

## 🔎 Búsqueda de Candidatos

Buscar oferentes cuyas habilidades coincidan con los requisitos del puesto.

## 📄 Consulta de Candidatos

Ver información detallada de los candidatos.

Visualizar el currículum del oferente en formato PDF.

## 🚫 Gestión de Puestos

Desactivar puestos publicados cuando ya hayan sido cubiertos.

## 👤 Funcionalidades para Oferentes

Los oferentes registrados podrán:

## 📋 Registro de Datos

Registrar información personal:

Identificación

Nombre

Primer apellido

Nacionalidad

Teléfono

Correo electrónico

Lugar de residencia

## 🔐 Acceso al Sistema

Iniciar sesión mediante correo electrónico y contraseña.

Acceder a su dashboard personal.

## 🧠 Gestión de Habilidades

Registrar o actualizar su lista de:

Características

Nivel de habilidad en cada característica.

## 📄 Currículo

Subir su currículum en formato PDF al sistema.

## 🛠️ Funcionalidades para Administradores

Los administradores del sistema podrán:

## 🔐 Acceso

Iniciar sesión usando identificación y contraseña.

Acceder a su panel de administración.

## ✔️ Autorizaciones

Listar nuevas empresas registradas.

Aprobar o rechazar su registro.

Listar nuevos oferentes registrados.

Aprobar o rechazar su registro.

## 🧩 Gestión de Características

Registrar las características que podrán utilizarse para describir:

Requisitos de los puestos.

Habilidades de los oferentes.

Las características deben ser jerárquicas, por ejemplo:

Lenguajes de Programación
   ├── Java
   ├── C#
   └── Python

Tecnologías Web
   ├── HTML
   ├── CSS
   └── JavaScript
📊 Reportes

Generar reportes PDF de puestos solicitados por mes.

## 🗄️ Tecnologías Utilizadas

Java

Spring Framework (Spring MVC)

MySQL

JDBC / JPA

HTML + CSS

Server Side Rendering
