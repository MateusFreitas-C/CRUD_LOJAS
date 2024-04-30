# CRUD Lojas

Este é um projeto que visa fornecer uma solução de uma api que tenha uma função de CRUD para manter os cadastros das lojas.

## Como Executar

Para executar o projeto localmente, você pode usar o Maven Wrapper fornecido com o projeto. Abra um terminal na raiz do projeto e execute o seguinte comando:

```bash
.\mvnw spring-boot:run
```

Caso prefira é possível utilizar o Docker para rodar o projeto. Abrindo na raiz do projeto e executando os seguintes comando:

```bash
docker build -t nome_do_projeto
```

Em seguida:

```bash
docker-compose up
```

Isso iniciará a aplicação Spring Boot e você poderá acessá-la em http://localhost:8080.

## Decisões de Design

### Classe Store

A classe `Store` foi projetada como abstrata para fornecer uma estrutura comum para diferentes tipos de lojas. Isso permite estender a classe `Store` e criar diferentes tipos de lojas, como `PhysicalStore` e `VirtualStore`.

### Loja Virtual e Loja Física

Ao cadastrar uma loja no sistema, o tipo da loja é especificado como parte do body passado no endpoint. O tipo de loja é representado por um enum, que pode ser `VIRTUAL` ou `PHYSICAL`, indicando se a loja é virtual ou física, respectivamente.

A escolha de usar um enum para representar o tipo de loja permite uma maneira clara e eficiente de diferenciar entre os diferentes tipos de lojas no sistema.

### Inheritance e Armazenamento de Dados

Para armazenar as lojas no banco de dados, também foi utilizado o conceito de herança. Todas as lojas, sejam elas virtuais ou físicas, são representadas como subclasses de uma classe abstrata `Store`. Isso permite que todas as informações relevantes sobre as lojas sejam gravadas em uma única tabela no banco de dados, facilitando a consulta e manipulação dos dados.

A classe abstrata `Store` contém os atributos comuns a todas as lojas, enquanto as subclasses `VirtualStore` e `PhysicalStore` adicionam os atributos específicos de cada tipo de loja.

Certifique-se de verificar a seção "Endpoints de Loja" para obter informações sobre como cadastrar novas lojas no sistema e especificar seu tipo.

## Endpoints da Store

A API oferece os seguintes endpoints relacionados à entidade `Store`:

- `GET /api/store/{cnpj}`: Obtém uma loja com base no CNPJ fornecido.
- `POST /api/store`: Cria uma nova loja com base nos dados fornecidos no corpo da solicitação.
- `PUT /api/store/{cnpj}`: Atualiza os detalhes de uma loja existente com base no CNPJ fornecido.
- `DELETE /api/store/{cnpj}`: Exclui uma loja com base no CNPJ fornecido.

Esses endpoints são protegidos por autenticação JWT, o que significa que o usuário deve fornecer um token JWT válido para acessá-los.

Obs: O campo storeType aceita apenas os valores 'VIRTUAL' e 'PHYSICAL'.

## Swagger

A API é documentada usando o Swagger, que fornece uma interface interativa para explorar e testar os endpoints da API. Depois de iniciar o aplicativo localmente. 

Ao acessar o Swagger, você verá uma lista de todos os endpoints disponíveis, juntamente com informações detalhadas sobre como usá-los. Você também pode autenticar-se usando o token JWT fornecido para acessar endpoints protegidos.

Para acessar o swagger basta acessar http://localhost:8080/swagger-ui/index.html

## Testes Unitários

O projeto inclui testes unitários para garantir a qualidade do código e a correção das funcionalidades implementadas. Os testes unitários abrangem a lógica de negócios e a validação dos endpoints.

## Spring Security

Neste projeto, o Spring Security é utilizado para lidar com a autenticação dos usuários. Ele inclui endpoints para registro de novos usuários (`/auth/signup`) e autenticação de usuários existentes (`/auth/signin`). 

### Registro de Usuário (SignUp)

O endpoint `/auth/signup` é usado para registrar novos usuários no sistema. Ele aceita uma solicitação contendo os detalhes do usuário, como nome, email e senha. Após o registro bem-sucedido, o usuário recebe um token JWT que pode ser usado para autenticar-se em endpoints protegidos.

Exemplo de uso:

```bash
POST /auth/signup
Content-Type: application/json

{
"name": "John Doe",
"email": "john@example.com",
"password": "password123"
}
```


### Autenticação de Usuário (SignIn)

O endpoint `/auth/signin` é usado para autenticar usuários existentes no sistema. Ele aceita uma solicitação contendo as credenciais do usuário, como email e senha. Após a autenticação bem-sucedida, o usuário recebe um token JWT que pode ser usado para acessar endpoints protegidos.

Exemplo de uso:
```bash
POST /auth/signin
Content-Type: application/json

{
"email": "john@example.com",
"password": "password123"
}
```

