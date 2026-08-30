# Auditoria de Implementação — Chat 2 — A0001–A0010

Data do ciclo: **2026-08-30 (America/Sao_Paulo)**.

## Escopo

- **INÍCIO:** A0001.
- **FIM:** A0010.
- **Quantidade:** 10 perks consecutivas.
- **Responsabilidade:** validar e fechar tecnicamente a implementação do design já aprovado pelo Chat 1, sem redesenhar perks.
- **Provider principal:** Epic Fight `21.17.3.1` em Minecraft 1.21.1 / NeoForge.

Fontes operacionais: protocolo consolidado do Chat 2; critérios obrigatórios consolidados; `STATUS.md`; os dez dossiês A0001–A0010; auditoria de design existente; código/runtime e testes do repositório.

## Resultado por perk

| Código | Contrato implementado | Provider/fallback | Estado pré-merge |
|---|---|---|---|
| A0001 | +3% dano/rank com espada em hit direto elegível | família Epic Fight; desconhecida = fail-closed | IMPLEMENTAÇÃO VALIDADA EM CI |
| A0002 | +2% cadência/rank via `ModifyAttackSpeedEvent` | provider-native; sem hook seguro = inativa | IMPLEMENTAÇÃO VALIDADA EM CI |
| A0003 | +3% crítico/rank no serviço crítico canônico | uma resolução crítica por root action | IMPLEMENTAÇÃO VALIDADA EM CI |
| A0004 | Ímpeto: hit/defesa/miss/stagger/decay/cleanup | receipts causais; stagger apenas LONG/KNOCKDOWN/NEUTRALIZE hostis | IMPLEMENTAÇÃO VALIDADA EM CI |
| A0005 | abertura de guarda, custo/cooldown e penetração/impacto | guarda nativa; fallback estrito de penetração-only com defesa física comprovável | IMPLEMENTAÇÃO VALIDADA EM CI |
| A0006 | Riposta: defesa técnica, janela, consumo, crítico/impacto, cooldown | `ON_DODGE` comprovado; demais defesas ficam fail-closed sem receipt | IMPLEMENTAÇÃO VALIDADA EM CI |
| A0007 | +3% dano/rank com machado | família Epic Fight; desconhecida = fail-closed | IMPLEMENTAÇÃO VALIDADA EM CI |
| A0008 | +2% cadência/rank via `ModifyAttackSpeedEvent` | provider-native; sem hook seguro = inativa | IMPLEMENTAÇÃO VALIDADA EM CI |
| A0009 | +3% crítico/rank no serviço crítico canônico | uma resolução crítica por root action | IMPLEMENTAÇÃO VALIDADA EM CI |
| A0010 | Fúria por hit direto/hostil/efetivo de machado, com target-switch | receipt server-authoritative; demais adapters = fail-closed | IMPLEMENTAÇÃO VALIDADA EM CI |

## Checklist técnico consolidado

- [x] Provider-native first preservado.
- [x] Epic Fight `21.17.3.1` é a versão contratada pelo adapter.
- [x] Fallback/fail-closed não altera identidade das perks.
- [x] Classificação de arma não usa nome, material, aparência ou tags fictícias paralelas.
- [x] Crítico de A0003/A0009 usa um único pipeline canônico.
- [x] Deduplicação por ação/receipt preservada.
- [x] A0004/A0006/A0010 dependem de fatos causais e server-authoritative, não de animação/tentativa/proximidade.
- [x] Nenhuma perk cria stamina, mana, vida, energia, output ou outro recurso paralelo/gratuito.
- [x] Nenhuma geração de Mastery repetitiva foi introduzida por este lote.
- [x] Conteúdo player-facing já permanece em PT-BR; este ciclo não redesenhou texto de gameplay.
- [x] NeoVitae não foi reintroduzido.

## Regressões específicas adicionadas pela PR #221

### A0006 — Riposta Perfeita

`A0001A0010ImplementationContractJUnitTest` comprova que uma defesa técnica confirmada com 5 Ímpeto arma a janela; o hit consumidor crítico recebe +20% de dano e +20% de impacto quando disponível, consome atomicamente os 5 pontos, suprime ganho de Ímpeto no mesmo hit e não reaplica o efeito em callback duplicado da mesma root action.

### A0010 — Pressão do Carrasco

O mesmo teste comprova fail-closed para dano zero, indireto, alvo não hostil ou família errada; confirma ganho de Fúria uma única vez por root action; e valida a ordem base → rank → multiplicador de troca de alvo.

## Evidência de CI antes do closeout documental

No HEAD de runtime/testes `b99ba35671dc92477c6b767ec4e4c5c22f0c71d0`, todos os 9 workflows associados à PR #221 concluíram com `success`, inclusive **RPG Skill Tree CI #1996**. Esse pipeline validou JUnit, NeoForge GameTests, build, verificação do JAR e dedicated-server smoke.

As alterações posteriores deste arquivo, dos dossiês e de `STATUS.md` são closeout documental. A confirmação definitiva exige um novo CI verde no HEAD final da PR e merge na `main`.

## Fallbacks/fail-closed legítimos remanescentes

- **A0001/A0007:** item sem família inequívoca do Epic Fight não recebe a perk.
- **A0005:** se guarda/postura não for observável, somente defesa física server-side comprovável autoriza penetração; impacto/pressão é omitido.
- **A0006:** `ON_DODGE` é o receipt comprovado. Aparo/guarda perfeita só entram futuramente com receipt público, causal e versionado.
- **A0010:** somente a rota comprovada do Epic Fight concede Fúria; adapters futuros precisam provar `direct + hostile + actualDamage + autoria + família machado` e compartilhar deduplicação.

## Pendências

**Nenhuma pendência técnica bloqueante para A0001–A0010 no provider/versionamento atual.**

## Gate final

O estado das dez perks deve mudar de `IMPLEMENTAÇÃO VALIDADA EM CI` para **`IMPLEMENTAÇÃO CONFIRMADA`** somente quando:

1. o HEAD final documental da PR #221 estiver completamente verde;
2. não houver review real pendente;
3. a PR #221 for mergeada;
4. a `main` pós-merge for buscada e o SHA final confirmado.

Após esse merge, o Chat 2 deve **PARAR** e não iniciar A0011–A0020 automaticamente.
