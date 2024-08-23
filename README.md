Versão Utilizada do MongoDB Community Server: 7.0.1(Current) 
Link para download : mongodb.com/try/download/community

Dependencias utilizadas via graddle : 

MongoDB -> org.springframework.boot:spring-boot-starter-data-mongodb-reactive, lib para trabalhar de forma reativa e assincrona.

Criação de um projeto web, reativo e assincrono -> org.springframework.boot:spring-boot-starter-web, org.springframework.boot:spring-boot-starter-webflux

Swagger UI, diferente do Core, contem a famosa interface grafica -> org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0
Obs "pessoal": Caso não seja necessario expor a documentação para uma equipe externa de devs, utilizar um core, acho mais performatico. 

Spring validation, realizar validações baseadas nas anotações Constraints do Jakarta : org.springframework.boot:spring-boot-starter-validation


Documentação api ViaCep : https://viacep.com.br/

Eu utilizei a anotação `@JsonProperty` do pacote `fasterxml.jackson` no Spring para mapear corretamente as variáveis da classe `Address` com os nomes dos campos que vêm na resposta JSON da API ViaCep, que possuem nomenclaturas diferentes. 
Isso permite que o Spring consiga associar os dados recebidos às variáveis da classe, mesmo que os nomes não coincidam.
