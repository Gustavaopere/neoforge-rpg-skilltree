# 11.12 — Salvaging universal e economia de materiais

## Objetivo

Permitir desmontar qualquer equipamento RPG elegível em uma bancada/fluxo integrado, preferencialmente reaproveitando a infraestrutura de salvaging do Apotheosis quando ela estiver disponível e for estável.

## Passo a passo

### A — Contrato de salvaging

- [ ] entrada precisa possuir identidade RPG válida;
- [ ] operação consome exatamente o item aceito;
- [ ] retorno depende de Rank e, quando desejado, Poder do Item/origem dentro de caps explícitos;
- [ ] quantidade de modifiers não deve produzir crescimento econômico explosivo sem policy deliberada;
- [ ] gems/sockets/encantamentos têm política própria documentada e não são duplicados.

### B — Materiais

Decidir após auditoria da versão instalada:

1. reutilizar materiais de rarity/salvaging do Apotheosis;
2. adicionar materiais próprios somente onde necessário;
3. mapear Rank RPG -> material/valor de forma data-driven.

Nomes próprios exibidos pelo RPG precisam de `pt_br`.

### C — Equipamento universal

A receita/handler não deve manter whitelist manual por mod. Critério principal é `RPG_ITEMIZED` + elegibilidade/policy de salvaging.

Isso inclui Vanilla, Iron's, Ars, Create, Curios e itens modded desconhecidos que tenham sido itemizados.

### D — Anti-duplication

- [ ] nenhuma rota de craft/smithing/salvaging gera lucro infinito trivial;
- [ ] prevenir chamadas duplas por menu/evento;
- [ ] retornar resultado uma única vez por consumo confirmado;
- [ ] validar interação com repairs, damaged items e containers especiais;
- [ ] impedir salvaging de cópia fantasma/preview.

### E — UI e recusas

Toda recusa própria deve explicar em pt-BR o motivo: item não itemizado, item bloqueado, integração indisponível, dado legado inválido etc.

### F — Fallback sem Apotheosis

O Core não depende da bancada do Apotheosis. Se o mod estiver ausente, manter API/economia do domínio desacoplada para futura UI própria ou desativação explícita sem crash.

## Testes previstos

- um item consumido -> um conjunto de outputs;
- mapping de todos os ranks;
- item de outro mod aceito sem receita manual;
- ausência do Apotheosis;
- gems/encantamentos sem duplicação;
- preview/menu não consome nem recompensa;
- propriedade de conservação econômica em recipes críticos.

## Acceptance

Qualquer equipamento RPG suportado pode ser desmontado por uma rota consistente, data-driven e sem duplicação, usando Apotheosis como integração opcional e não como requisito estrutural do Core.
