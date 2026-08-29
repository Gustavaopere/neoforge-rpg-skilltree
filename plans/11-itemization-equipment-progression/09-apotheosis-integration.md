# 11.09 — Integração com Apotheosis e ecossistema Apothic

## Objetivo

Reutilizar o que o ecossistema Apotheosis já faz bem sem permitir que ele seja uma segunda autoridade de rank, quantidade ou reroll dos equipamentos RPG.

## Escopo de auditoria inicial

Fazer fetch/inspeção fresca das versões realmente presentes no pack e mapear, sem assumir APIs:

- Apotheosis;
- Apothic Attributes;
- Apothic Compat / Apothic Compats;
- Iron's Apothic;
- outros bridges carregados na instância.

## Passo a passo

### A — Matriz de responsabilidades

Congelar explicitamente:

**RPG Itemization é autoridade de:**

- identidade persistente;
- Rank;
- Poder do Item;
- Prefixos/Sufixos/Infixos;
- quantidade 1..5;
- geração e política de não-reroll.

**Apotheosis pode continuar fornecendo, quando compatível:**

- atributos;
- gems/sockets;
- enchanting;
- efeitos/affixes reutilizáveis por adapter;
- salvaging/materials quando integrado no 11.12.

### B — Bridge de affixes

- [ ] inventariar affixes disponíveis nas versões instaladas;
- [ ] classificar cada candidato como Prefixo, Sufixo, Infixo ou `UNSUPPORTED`;
- [ ] reutilizar runtime externo somente quando o contrato for estável e seguro;
- [ ] não converter automaticamente gems/sockets/enchantments em modifiers RPG;
- [ ] registrar proveniência do adapter para diagnóstico.

### C — Bloqueio de reroll

Para item RPG já itemizado:

- [ ] Reforging não altera Rank nem modifiers RPG;
- [ ] qualquer reroll individual equivalente é bloqueado/interceptado;
- [ ] UI deve explicar em pt-BR que a identidade é permanente;
- [ ] não consumir materiais se a ação é recusada.

### D — Gems e sockets

- [ ] preservar funcionamento normal de gems/sockets onde possível;
- [ ] alteração de gem não muda identidade RPG;
- [ ] remoção/substituição de gem não dispara nova geração;
- [ ] atributos de gem e RPG compõem sem stacking duplicado.

### E — Raridade externa

Se Apotheosis exigir rarity própria para funcionalidades internas, definir adapter/projeção explícita sem fazer dela a fonte de verdade do Rank RPG. Não manter duas raridades independentes visíveis ao jogador sem propósito claro.

### F — Ausência do mod

Core do Stage 11 deve iniciar sem Apotheosis. Adapter registra apenas quando dependências estiverem presentes.

## Testes previstos

- matriz com/sem Apotheosis;
- reforge recusado sem consumo;
- gems continuam operando;
- affix adaptado mantém efeito após save/load;
- ausência de double-application;
- nenhuma classe opcional carregada quando ausente.

## Acceptance

Apotheosis funciona como provider/integração, não como segunda autoridade de itemização; itens RPG não podem ser rerrolados e gems/sockets/efeitos aprovados permanecem interoperáveis.
