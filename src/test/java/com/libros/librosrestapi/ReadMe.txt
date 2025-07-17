🧪 Cómo correr los tests
🔹 Solo tests unitarios
bash
Copiar
Editar
mvn clean test
Corre solo *Test.java

Rápidos, sin arrancar contexto completo de Spring

🔹 Unitarios + integración
bash
Copiar
Editar
mvn clean verify
Corre primero los unitarios (*Test.java)

Luego los de integración (*IT.java)

Ideal antes de hacer merge o deploy

📦 Plugins de test
✅ maven-surefire-plugin: corre unit tests durante fase test

✅ maven-failsafe-plugin: corre integration tests durante fases integration-test y verify

Configurados en el pom.xml para buscar patrones:

**/*Test.java → unit

**/*IT.java → integración

