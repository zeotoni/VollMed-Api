# VollMed-Api
![MIT License](https://img.shields.io/badge/License-MIT-blue.svg)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.6-brightgreen)

Uma api para cadastro e gerenciamento de médicos, pacientes e consultas simulando uma agenda de clínica médica.


## 📃 Visão Geral
- [Descrição](#descrição)
- [Instalação](#instalação)
- [Uso](#uso)
- [Licença](#licença)
- [Contato](#contato)

## 📝 Descrição

Este projeto foi desenvolvido como parte do curso de formação em Spring Boot da Alura. Trata-se de uma aplicação de agenda para clínicas médicas, permitindo o cadastro e gerenciamento de usuários, pacientes e consultas. Construída com o framework Spring Boot, a aplicação inclui as seguintes funcionalidades e tecnologias:

- **Segurança**: Utilização do Spring Security para autenticação e autorização de usuários, com validação através de tokens JWT.
- **Banco de Dados**: Conexão com um banco de dados MySQL, garantindo persistência de dados eficiente.
- **Versionamento do Banco de Dados**: Implementação de controle de versão do banco de dados utilizando Flyway, assegurando migrações consistentes.
- **Documentação**: Documentação da API utilizando Springdoc OpenAPI, facilitando a compreensão e o uso das funcionalidades expostas.

Este projeto visa demonstrar a integração e a aplicação prática dessas tecnologias em um contexto real de desenvolvimento de software para a área médica.


## 🛠️ Instalação e Configuração

Para executar este projeto, siga as etapas abaixo. Você precisará do Java instalado e deverá configurar algumas variáveis de ambiente.

### Pré-Requisitos

- **Java**: Certifique-se de que o Java JDK 11 ou superior está instalado em sua máquina. Você pode baixar o JDK [aqui](https://www.oracle.com/java/technologies/javase-downloads.html) ou [aqui](https://adoptium.net/).
- **Maven**: Embora não seja estritamente necessário para rodar o projeto, é recomendável ter o Maven instalado para gerenciar as dependências. Você pode instalar o Maven [aqui](https://maven.apache.org/install.html).

### Clonagem do Repositório

Clone o repositório usando um dos métodos abaixo:

1. Clone o repositório usando um dos métodos abaixo:

    - **Via HTTPS**:
      ```sh
      git clone https://github.com/zeotoni/VollMed-Api.git
      ```

    - **Via SSH**:
      Certifique-se de que você tenha uma chave SSH configurada e adicionada à sua conta GitHub. Se não tiver, siga as instruções [aqui](https://docs.github.com/en/authentication/connecting-to-github-with-ssh/about-ssh).
      ```sh
      git clone git@github.com:zeotoni/VollMed-Api.git
      ```

2. Navegue até o diretório do projeto:
    ```sh
    cd projeto
    ```

3. Instale as dependências e compile o projeto usando Maven:
    ```sh
    mvn install
    ```

4. Substitua as variáveis de ambiente:
    ```sh
    export DATABASE_URL=jdbc:mysql://localhost:3306/seubancodedados
    export DATABASE_USERNAME=seuusuario
    export DATABASE_PASSWORD=suasenha
    export JWT_SECRET=suasecretjwt
    ```
   
5. Execute o projeto:
    ```sh
    mvn spring-boot:run
    ```
   
### Executando na IDE

Se preferir, você pode executar o projeto diretamente na sua IDE. Basta importar o projeto clonado, fazer o build com o maven, substituir as variáveis de ambiente e rodar o projeto pela IDE. Alguns exemplos são: IntelliJ IDEA e Eclipse.


## 📚 Uso

### Obtendo o Token de Autenticação

Para interagir com a API, você precisa de um token de autenticação JWT. Siga os passos abaixo para obter o token:

1. **Faça uma Requisição de Login**:
   - **Método**: `POST`
   - **URL**: `/login`
   - **Corpo da Requisição**:
     ```json
     {
       "username": "email@voll.med",
       "password": "123456"
     }
     ```
   - **Resposta**:
     ```json
     {
       "token": "seu_token_jwt_aqui"
     }
     ```

2. **Use o Token**:
   - Inclua o token retornado no cabeçalho `Authorization` das suas requisições à API:
     ```
     Authorization: Bearer seu_token_jwt_aqui
     ```

### Testando a API com Swagger

Você pode utilizar a interface Swagger para obter e testar endpoints da API de maneira interativa:

1. **Acesse a Documentação Swagger**:
   - **URL**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

2. **Autentique-se e Obtenha o Token**:
   - No Swagger UI, clique no botão `Authorize` no canto superior direito.
   - Use as seguintes credenciais para login:
      - **Username**: `email@voll.med`
      - **Password**: `123456`
   - Clique em `Authorize`. O Swagger UI solicitará um token JWT e o aplicará automaticamente nas requisições subsequentes.

3. **Teste os Endpoints**:
   - Após autenticar-se, você pode explorar e testar todos os endpoints diretamente na interface Swagger. O token será incluído automaticamente nas requisições protegidas.

### Observações

- **Autenticação**: Utilize as credenciais fornecidas para autenticação e obtenção do token JWT.
- **Formato dos Dados**: Todas as requisições e respostas estão no formato JSON.

Para mais informações ou ajuda adicional, consulte a [documentação Swagger](https://swagger.io/docs/) ou entre em contato com o desenvolvedor.


## 📜 Licença

Este projeto está licenciado sob a [Licença MIT](LICENSE). Veja o arquivo `LICENSE` para mais detalhes.

## 📞 Contato

Se você tiver perguntas ou precisar de assistência adicional, entre em contato com:

- **Nome**: Ezequiel Otoni
- **Email**: ezequiel.zeotoni@gmail.com
- **LinkedIn**: [Ezequiel Otoni](https://www.linkedin.com/in/zeotoni)
