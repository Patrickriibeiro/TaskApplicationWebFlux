**MongoDB Community Server:** [7.0.1 (Current)](https://mongodb.com/try/download/community)

## Dependências Utilizadas

### MongoDB Reactive
- **Dependência:** `org.springframework.boot:spring-boot-starter-data-mongodb-reactive`
- **Descrição:** Utilizada para trabalhar com o MongoDB de forma reativa e assíncrona, aproveitando as capacidades de não bloqueio e escalabilidade do Spring WebFlux.

### Projeto Web Reativo
- **Dependências:**
  - `org.springframework.boot:spring-boot-starter-web`
  - `org.springframework.boot:spring-boot-starter-webflux`
- **Descrição:** Essas dependências permitem a criação de um projeto web reativo e assíncrono, utilizando a stack reativa do Spring (WebFlux) para lidar com requisições de forma eficiente e não bloqueante.

### Swagger UI
- **Dependência:** `org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0`
- **Descrição:** Fornece uma interface gráfica para a documentação das APIs. É particularmente útil quando há necessidade de expor a documentação para uma equipe externa de desenvolvedores. **Observação:** Se a documentação da API não precisar ser exposta para fora, considerar o uso do `springdoc-openapi-starter-webmvc-core` para um desempenho melhor.

### Spring Validation
- **Dependência:** `org.springframework.boot:spring-boot-starter-validation`
- **Descrição:** Facilita a validação de dados utilizando as anotações do Jakarta Bean Validation, permitindo a aplicação de constraints nas classes de modelo.

## Integração com API ViaCep

Para integrar a API ViaCep, utilizei a anotação `@JsonProperty` do pacote `fasterxml.jackson` no Spring. Essa anotação é essencial para mapear corretamente as variáveis da classe `Address` com os nomes dos campos recebidos na resposta JSON da API ViaCep, que possuem nomenclaturas diferentes. Com isso, o Spring consegue associar os dados recebidos às variáveis da classe, mesmo que os nomes não coincidam, garantindo uma integração suave e eficiente.

## Documentação

Para mais informações sobre a API ViaCep, acesse a [documentação oficial](https://viacep.com.br/).

### Docker
- **Docker Desktop:** https://www.docker.com/products/docker-desktop/

### Offset Explorer e Kafka 
- **Offset Explorer:** https://kafkatool.com/download.html
- **Dependência:** `org.apache.kafka:kafka-streams`
	                 `org.springframework.cloud:spring-cloud-stream`
	                 `org.springframework.cloud:spring-cloud-stream-binder-kafka`
	                 `org.springframework.cloud:spring-cloud-stream-binder-kafka-streams`
	                 `org.springframework.kafka:spring-kafka`
