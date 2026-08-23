# Auditoria A — Baseline sem Minecraft Skills

Data: **23/08/2026**  
Branch auditada: **`main`**  
Commit auditado: **`31377faa79685565b683923e9d8e2e62db073c92`**

## Status deste material

Este diretório é um **registro histórico de auditoria**, não a especificação canônica atual do projeto.

A auditoria foi produzida antes de as Minecraft Skills especializadas estarem disponíveis na sessão. Ela permanece valiosa como baseline independente e deve ser comparada com a próxima auditoria feita com essas skills realmente carregadas.

Nenhuma recomendação deste diretório deve ser aplicada automaticamente sem:

1. confirmar que o achado ainda existe no código atual;
2. comparar com a auditoria posterior;
3. validar decisões sensíveis à versão especificamente contra Minecraft 1.21.1 + NeoForge 21.1.x;
4. resolver eventuais contradições antes de promover a recomendação para documentação canônica.

## Conteúdo

- [`01-scope-architecture-blockers.md`](./01-scope-architecture-blockers.md)
  - veredito executivo;
  - snapshot;
  - inventário;
  - arquitetura atual;
  - estado da implementação;
  - cinco bloqueadores concretos.

- [`02-technical-audit-and-recommended-architecture.md`](./02-technical-audit-and-recommended-architecture.md)
  - persistência;
  - attachments/components/capabilities;
  - networking;
  - reload/data;
  - registries;
  - atributos;
  - classes/especializações;
  - mastery/moedas;
  - client/server;
  - performance;
  - integrações;
  - datagen;
  - testes/CI;
  - arquitetura recomendada.

- [`03-master-plan.md`](./03-master-plan.md)
  - Fases 0 a 9;
  - objetivos;
  - dependências;
  - riscos;
  - testes;
  - critérios objetivos de conclusão.

- [`04-rules-checklist-decisions-handoff.md`](./04-rules-checklist-decisions-handoff.md)
  - regras permanentes propostas;
  - checklist antes de merge;
  - decisões pendentes;
  - handoff autocontido;
  - ferramentas utilizadas.

## Relação com a futura auditoria consolidada

Quando a auditoria com `minecraft-modding`, `minecraft-mod-dev`, `minecraft-testing`, `minecraft-ci-release` e demais ferramentas relevantes estiver pronta, ela deve ser arquivada separadamente como **Auditoria B**.

Depois será criado um documento de consolidação contendo:

- convergências entre A e B;
- achados exclusivos de cada uma;
- contradições;
- verificações adicionais;
- decisões aceitas/rejeitadas;
- ordem final de execução.

Somente essa consolidação deverá alimentar os documentos canônicos do projeto.
