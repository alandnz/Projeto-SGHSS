# SGHSS - Sistema de Gestão Hospitalar e de Serviços de Saúde

Este projeto visou o desenvolvimento de um sistema back-end para gestão hospitalar e serviços de saúde.
Ele faz parte da disciplina Projeto: Desenvolvimento Back-end, do curso de Análise e Desenvolvimento de Sistemas da UNINTER.
Desenvolvido por: Alan Diniz Salazar
RU: 4499264

## 📌 Funcionalidades Principais

- Cadastro e gerenciamento de pacientes, profissionais de saúde e usuários.
- Agendamento de procedimentos (consultas, exames, etc.).
- Controle de acesso por perfis (ADMIN, MÉDICO, ENFERMEIRO, RECEPCIONISTA, PACIENTE).
- Autenticação com JWT.

## Tecnologias utilizadas

- Java 17
- Spring Boot
- MySQL
- JWT
- Eclipse IDE

## 🔒 Perfis de Acesso

- **ADMIN**: tem acesso total ao sistema.
- **RECEPCIONISTA**: pode realizar todas as operações, exceto excluir usuários e profissionais de saúde.
- **MÉDICO**: pode cadastrar, visualizar e atualizar dados, mas não pode excluir nada.
- **ENFERMEIRO**: possui as mesmas permissões do médico.
- **PACIENTE**: pode apenas visualizar profissionais de saúde e os procedimentos vinculados ao seu cadastro.
