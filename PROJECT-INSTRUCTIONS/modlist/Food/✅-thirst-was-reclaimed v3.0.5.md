# Thirst Was Reclaimed

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Arquivo JAR:** `ThirstWasReclaimed-1.21.1-3.0.5.jar`
- **Versão semântica:** `3.0.5`
- **Metadata runtime/build:** `1.21.1-3.0.5`
- **Minecraft/loader:** 1.21.1 / NeoForge
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Sobrevivência, Food
- **Função:** Sistema de sede e pureza de água, com HUD/efeitos, containers e integrações de sobrevivência; complementa Cold Sweat sem substituir sua authority térmica.
- **Dependências/integrações:** NeoForge 1.21.1. Cold Sweat está fisicamente em 2.4.3.1; Create e diversos containers/compat paths permanecem regression surfaces quando aplicáveis.
- **Sobreposição:** Authority de sede/hidratação. Não deve competir com outro thirst provider nem transformar temperatura corporal em segundo saldo de thirst.
- **Compatibilidade/Riscos:** purity/container NBT/components, direct drinking, loot-table parsing, integração com CreateCyberGoggle, Supplementaries containers, client/server HUD/state e compat Cold Sweat.
- **Observações:** JAR físico `ThirstWasReclaimed-1.21.1-3.0.5.jar`; metadata física `1.21.1-3.0.5`; para filename do catálogo usa-se versão semântica `3.0.5`, preservando a metadata completa no corpo.
- **Procedência:** modlist física de 16/09/2026 + CurseForge oficial Thirst Was Reclaimed 3.0.5. Nenhum teste runtime foi executado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/thirst-was-reclaimed
- **Atualização/Status:** REAUDITADO EM 16/09/2026 — instalado passou de 3.0.4 / metadata `1.21.1-3.0.4` para 3.0.5 / `1.21.1-3.0.5`.
- **Histórico da decisão:** decisão `Manter` preservada.
- **Data da última decisão:**

> 💧 **ESCOPO CANÔNICO.** Runtime físico: `ThirstWasReclaimed-1.21.1-3.0.5.jar`; mod id `thirst`; metadata `1.21.1-3.0.5`; versão semântica `3.0.5`; NeoForge 1.21.1. O mod controla **sede/hidratação e pureza**, não temperatura corporal.

## 1. Authority e papel
Thirst Was Reclaimed mantém o state de thirst/hydration, lógica de beber, pureza e efeitos associados. Cold Sweat continua authority térmica. Integrações entre ambos devem trocar inputs/efeitos explicitamente, sem double settlement.

## 2. Pureza e fontes de água
O dossiê migrado registra pureza como superfície central: diferentes fontes/containers podem carregar pureza distinta e tratamentos/recipes podem alterá-la. A pureza precisa acompanhar o conteúdo real do container e sobreviver a transfer/craft/save/restart conforme contract da build. Não inferir pureza padrão específica sem config/runtime.

## 3. Beber diretamente e containers
Beber diretamente de água é uma ação server-authoritative; cliente não deve aplicar thirst/purity sozinho. Containers precisam preservar quantidade/pureza e não duplicar consumo em interaction retries. A atualização 3.0.5 adiciona cooldown ao **direct drinking water with hand**, tornando spam/click repeat um gate explícito.

## 4. Integração Cold Sweat
O source da linha 1.21.1 inclui Cold Sweat como integration surface. O pack usa Cold Sweat `2.4.3.1`. Essa integração não converte os dois mods em um único provider: thirst e body temperature permanecem estados distintos. Regressar HUD/effects, drinking e mudanças térmicas relacionadas quando houver compat ativa.

## 5. Client/server, HUD e lifecycle
Servidor deve decidir thirst/purity/effects; cliente apresenta HUD/feedback. Validar login/reconnect, death/respawn, dimension change, save/restart, container transfer e config/datapack reload. Dois jogadores não podem compartilhar/corromper thirst state.

## 6. Integrações e boundaries preservados
O dossiê anterior registra compatibilidade/integração com containers e outros mods de survival. Esses paths só são considerados ativos quando o provider correspondente está instalado. Loot tables, recipes e tags precisam falhar de modo seguro se optional integration estiver ausente. UI/Jade-like presentation não é authority do valor real.

## 7. Atualização instalada — 3.0.5
A release oficial `1.21.1-3.0.5`, file ID 8852248, publicada em 10/09/2026 para NeoForge 1.21.1, registra quatro mudanças concretas:
1. correção de incompatibilidade com **CreateCyberGoggle**;
2. cooldown para beber água diretamente com a mão;
3. correção de perda de **purity** em container vindo de **Supplementaries**;
4. correção de erro de parsing ao carregar loot table.

Esses quatro pontos são regression gates da build física atual.

## 8. Upstream posterior
Em 16/09/2026 existe publicação `3.0.6` cujo filename sugere 1.21.1, mas a página recuperada do CurseForge apresenta metadata de game version inconsistente. Por regra fail-closed, ela não é tratada aqui como candidato 1.21.1 validado nem altera a autoridade física `3.0.5`.

## 9. Riscos técnicos
1. double thirst settlement por outro provider;
2. purity perdida/duplicada em container transfer;
3. direct-drink spam ou cooldown client-only;
4. loot table quebrando datapack/load;
5. optional integration causando classloading error;
6. Cold Sweat/thirst state acoplados indevidamente;
7. HUD divergindo do servidor;
8. death/relog resetando ou duplicando state;
9. Supplementaries container perdendo purity — fix 3.0.5;
10. CreateCyberGoggle incompat — fix 3.0.5.

## 10. Matriz de testes
- [ ] Dedicated server boot com Thirst 3.0.5 e Cold Sweat 2.4.3.1.
- [ ] Beber com a mão respeita cooldown server-side.
- [ ] Diferentes fontes/containers mantêm purity esperada.
- [ ] Supplementaries container preserva purity após transfer/use/save.
- [ ] CreateCyberGoggle não causa incompatibilidade na superfície corrigida.
- [ ] Loot tables carregam sem parsing error.
- [ ] HUD reflete state server-side.
- [ ] Death/relog/dimension/save-restart preservam lifecycle correto.
- [ ] Multiplayer mantém state isolado por player.
- [ ] Cold Sweat integration não duplica efeitos/authority.

**Nenhum teste foi executado nesta reauditoria documental.**

## 11. Evidências e limites
- modlist física de 16/09/2026: filename e metadata `1.21.1-3.0.5`;
- CurseForge oficial 3.0.5 e changelog dos quatro fixes;
- conteúdo migrado do Notion sobre authority de thirst/purity, containers, Cold Sweat, sides e lifecycle preservado;
- configs físicas e valores específicos de purity não foram lidos nesta passagem.

## 12. Reauditoria física — 16/09/2026
O runtime instalado mudou de 3.0.4 para 3.0.5. A distinção versão semântica (`3.0.5`) versus metadata física (`1.21.1-3.0.5`) foi preservada. Nenhum teste de drinking, purity, loot, Supplementaries, CreateCyberGoggle ou Cold Sweat foi executado.