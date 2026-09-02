# AUDITORIA DE IMPLEMENTAÇÃO CHAT 2 — A0031–A0040

## Registro do lote

- **INÍCIO:** A0031
- **FIM:** A0040
- **Quantidade:** 10 perks consecutivas.
- **Minecraft:** NeoForge 1.21.1.
- **Java:** 21.
- **Design:** fechado previamente pelo Chat 1 na PR #239 e na auditoria `AUDITORIA-RETROATIVA-PROVIDERS-A0031-A0040.md`.
- **Branch Chat 2 atual:** `feat/chat2-a0031-a0040-retro-implementation`.
- **Base:** `main@452e8b23e374179c1f616f9beedce6e3dea66ef5`.
- **Estado do lote:** **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.
- **Exceção explícita:** A0036 possui código/consumer completo, mas permanece **FAIL-CLOSED** porque o provider auditado não publica heavy receipt inequívoco.
- **Chat 2 não executou** unit tests, GameTests, build NeoForge, dedicated-server smoke ou CI final e não declara `IMPLEMENTAÇÃO CONFIRMADA`.

## Seleção retroativa A0001→A0080

Por ordem do usuário, o Chat 2 refez a seleção desde A0001 até o último ponto alcançado anteriormente. O protocolo impede selecionar novamente lotes já marcados como Chat 2 concluído/confirmado:

- A0001–A0020: implementação confirmada em ciclos anteriores;
- A0021–A0030: Chat 2 e Chat 3 já concluíram o ciclo, preservando fail-closed onde receipts continuam ausentes;
- **A0031–A0040:** primeiro lote exato de 10 com design fechado e sem fechamento Chat 2 na `main`; selecionado neste ciclo;
- A0041+ não foi iniciado.

## Anomalia de branch/PR

Foram encontrados vários refs históricos `chat2/a0031-a0040-*` e `chat3/a0031-a0040-*`. Os refs Chat 2 examinados apontavam para o fechamento antigo A0021–A0030 e não constituíam uma branch ativa/usável de implementação A0031–A0040. A branch antiga `chat3/a0031-a0040-final-validation-v3` continha um experimento/validação não mergeado deste lote.

O Chat 2 atual não retomou uma branch divergente antiga. Criou uma branch substituta a partir da `main` fresca e usou a branch histórica de Chat 3 **somente como evidência técnica** para portar três correções de runtime já experimentadas. A comparação com o merge-base mostrou que os commits concorrentes que chegaram à `main` atual não tocaram `A0021A0040CombatPolicy`, `A0021A0040CombatState` nem `A0021A0040EpicFightHooks`, evitando regressão de runtime ao portar esses blobs.

## Estado perk por perk após Chat 2

| Código | Perk | Estado após Chat 2 | Observação principal |
|---|---|---|---|
| A0031 | Treino com Maças I | **CÓDIGO PRESENTE** | MACE provider-native/exato + Mastery finite-discovery |
| A0032 | Treino com Maças II | **CÓDIGO PRESENTE** | cadence existente revalidada sobre classificação MACE segura |
| A0033 | Precisão com Maças | **CÓDIGO PRESENTE** | crítico canônico existente revalidado sobre classificação MACE segura |
| A0034 | Trauma Contundente | **CÓDIGO PRESENTE NO FALLBACK CANÔNICO** | Armor >0; outras proteções não provadas seguem fail-closed |
| A0035 | Armadura Fendida | **CÓDIGO PRESENTE** | reservation→POST commit; Witherstein específico continua sem mapping inventado |
| A0036 | Maestria de Maças — Quebra-Ossos | **CÓDIGO PRESENTE EM FAIL-CLOSED** | consumer/debuff/sequencing presentes; heavy receipt continua ausente |
| A0037 | Treino com Foices I | **CÓDIGO PRESENTE** | SCYTHE somente provider-native + Mastery finite-discovery |
| A0038 | Treino com Foices II | **CÓDIGO PRESENTE** | cadence existente revalidada sobre classificação SCYTHE segura |
| A0039 | Precisão com Foices | **CÓDIGO PRESENTE** | crítico canônico existente revalidado sobre classificação SCYTHE segura |
| A0040 | Marca da Ceifa | **CÓDIGO PRESENTE** | lifecycle bounded por prune periódico |

## Correções implementadas

### A0031/A0037 — família provider-native e Mastery anti-farm

`A0021A0040MasteryPolicy` foi generalizado para HAMMER/MACE/SCYTHE como ledgers de descoberta finita:

- +10 por tipo hostil inédito;
- chave persistida em `DiscoveryProgress` por lane + entity type;
- repeat hit do mesmo tipo = 0;
- MACE: 6 tipos→60, 8 tipos→80;
- SCYTHE: 6 tipos→60.

`A0021A0040MasteryHooks`:

- usa category/capability Epic Fight para HAMMER/MACE/SCYTHE;
- fallback vanilla somente para identidade exata `minecraft:mace`;
- SCYTHE não possui fallback vanilla;
- removeu o caminho por tags paralelas.

Os arquivos `data/rpgskilltree/tags/item/maces.json` e `scythes.json` foram removidos para impedir que o datapack mantenha uma classificação paralela residual.

### A0035 — commit causal

Antes deste Chat 2, A0035 consumia 3 Trauma e marcava `Sundered` no PRE. Agora:

1. PRE verifica `availableTrauma` e cria `PendingSunder` por root;
2. Trauma permanece intacto durante a fase reversível;
3. POST confirmado chama `commitPreparedSunder`;
4. somente o commit consome 3 Trauma e marca `Sundered`;
5. cancelamento, alvo não hostil ou dano zero descarta a preparação;
6. o modifier de Armor só é aplicado se `armorSunderCommitted=true`.

Isso remove o estado fantasma e alinha consumo/efeito ao mesmo root confirmado.

### A0036 — sequencing e consumer fail-closed

O policy agora tira snapshot de `Sundered` antes de preparar A0035. Portanto um root não pode criar Armadura Fendida e imediatamente satisfazer Quebra-Ossos.

Foi implementado o consumer latente de Descompasso:

- −10% movement por modifier transitório;
- −8% dano físico causado por multiplicador restrito a `rpgskilltree:physical`;
- boss half conforme `BeforeResult`;
- 3 s;
- cooldown 12/11/10 por mastery;
- commit do cooldown somente no POST confirmado;
- cleanup por expiry/morte/lifecycle.

**Heavy receipt permanece ausente.** O bridge real continua passando `heavyConfirmed=false`. `shouldChargeWeapon`, combo/animação, dano alto, arma lenta, Impact e charge-time estimado não foram promovidos a receipt. Assim A0036 permanece corretamente inativa/fail-closed até provider/API futura inequívoca.

### A0040 — lifecycle bounded

`A0021A0040CombatState.pruneExpiredReapingMarks(now)` percorre as marcas transitórias e remove entradas expiradas sem depender de o mesmo alvo voltar a ser consultado. O server tick aciona a varredura a cada 1 s. A maturação ≥50→<50, actor→target isolation e dedup por root permanecem inalterados.

## Fail-closed preservado

- Witherstein: documentado como boss, mas sem registry id/membership em boss tag comprovado no material auditado; nenhuma heurística/mapping foi criado.
- A0036 heavy: ausente; perk inativa.
- A0034 guard/posture extras: sem receipt provider-native, somente fallback Armor permanece ativo.
- SCYTHE externa sem category/mapping: inativa.
- MACE externa sem category/mapping: inativa; somente `minecraft:mace` recebe fallback vanilla.
- Black Arcana `ARCANE_BACKLASH`, companions Mobstein, Volcanoes e Enshrouded não recebem autoria MARTIAL nem Mastery por associação temática.

## Inspeção estática do Chat 2

- O canal `rpgskilltree:physical` já existe no datapack e é reutilizado pelo Descompasso; não foi criado segundo classificador físico.
- O `physical.json` atual inclui `player_attack`, `mob_attack`, `mob_attack_no_aggro`, `arrow`, `trident`, `thrown` e `sting`.
- A branch não altera os pipelines de A0021–A0030 além do código compartilhado necessário; as reservations de DAGGER/HAMMER permanecem presentes no state/policy portado.
- Existe teste histórico em `A0021A0040MasteryPolicyTest` que ainda espera MACE +3 XP/hit; após a mudança canônica, essa expectativa está obsoleta e deve ser atualizada pelo Chat 3 durante a bateria de validação. Chat 2 não altera/roda a suíte final.

## Handoff obrigatório para Chat 3

1. Atualizar/criar testes de Mastery MACE/SCYTHE finite-discovery, incluindo persistência/dedup e 6/8 tipos.
2. Testar ausência de classificação por tags/hoe/nome e fallback exato `minecraft:mace`.
3. Testar A0035 PRE→POST, rollback cancel/zero, concorrência de roots e consumo único de 3 Trauma.
4. Testar que A0036 não ativa sem heavy receipt.
5. Exercitar o consumer latente A0036 com receipt controlado: Sunder preexistente, same-root exclusion, dois debuffs, physical-only, boss half, cooldown e cleanup.
6. Testar A0040 com alvo morto, removido/descarregado e marca expirada sem reconsulta.
7. Revalidar A0032/A0033/A0038/A0039 após a mudança de family classifier.
8. Executar unit tests, GameTests pertinentes, build NeoForge, dedicated-server smoke e CI aplicáveis.
9. Corrigir somente falhas técnicas sem redesign; se identity/provider/gate/semântica precisar mudar, devolver ao Chat 1.
10. Somente com evidência declarar `IMPLEMENTAÇÃO CONFIRMADA`, obter CI GREEN, mergear e confirmar `main`.

## Fechamento

**A0031–A0040: CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3.**

A0036 permanece intencionalmente `FAIL-CLOSED` até existir heavy receipt seguro. O Chat 2 para neste lote e não inicia A0041+ neste ciclo.
