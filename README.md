# Ac2_Parte3_Mobile

# Atividade 5 – AC2
Sobre a atividade
Crie um aplicativo que cadastre alunos de uma instituição. Para esse cadastro você
utilizará o mockapi.io e criará o repositório de “aluno”. A classe Aluno terá a seguinte
composição:

Aluno
- ra : int
- nome : string
- cep : string
- logradouro : string
- complemento : string
- bairro : string
- cidade : string
- uf : string
+ Aluno()
+ Aluno(ra : int, nome : string, cep : string, logradouro : string, complemento : string,
bairro : string, cidade : string, uf : string)
+ gets..
+ sets...
  
Na tela de cadastro de aluno, ao informar o CEP, os demais dados como: logradouro,
complemento, bairro, cidade e uf deverão ser carregados automaticamente, para isso
você deve usar a API do viacep (https://viacep.com.br/) para fazer a requisição GET
enviando o CEP e obter os dados do endereço.
No final seu aplicativo terá duas telas:

• Uma tela de cadastro carregando os dados de endereço mediante o CEP
informado e salvando os dados no Mockapi.
• Uma tela que listará todos os alunos inseridos
 
