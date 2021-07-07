# Prova 3 - POOV
> Implementação de uma CRUD completa com emissão de relatório.

## Tecnologias usadas
* Maven
* Java
* JPA / Hibernate
* iTextPDF
* JavaFX


## Configurar projeto
No arquivo `pom.xml` defina o caminho para o /bin do compilador JDK. 
> Essa ação é necessária caso o JDK não esteja definido em variável de ambiente **%JAVA_HOME%**

```xml
<executable> ${caminho} </executable>
```

No arquivo `persistence.xml` defina as configurações referentes ao banco de dados.
1. Crie um banco de dados no MySql Server 
2. Insira o usuário para a JPA trabalhar no banco de dados
```xml
<property name="javax.persistence.jdbc.user" value="${usuário}"/>>
```
3. Insira a senha do usuário
```xml
<property name="javax.persistence.jdbc.password" value="${senha}"/>
```

4. Insira o endereço do banco de dados
```xml
<property name="javax.persistence.jdbc.url" 
value="jdbc:mysql://localhost:3306/${nome_do_banco}?useSSL=false&amp;serverTimezone=UTC"/>
```
> Verifique se a porta do seu banco corresponde a `3306` e substitua **${nome_do_banco}** pelo nome do banco criado. No meu caso é `mysql_prova`.

## Build do projeto
* A build do projeto é feita através do plugin de JavaFX do Maven. Utilize os comandos `javafx:compile` para compilar e `javafx:run` para executar.
* Assim que clicar em algum dos botões, a JPA carregará o banco com as devidas configurações. A tela irá travar por alguns segundos enquanto as tabelas são criadas.


**Qualquer dúvida entrar em contato.**

