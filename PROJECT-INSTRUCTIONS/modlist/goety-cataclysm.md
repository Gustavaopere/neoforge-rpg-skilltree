# Goety Cataclysm — 1.21.1-1.8.2

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db8187aef7dbf6fdad486a  
> Estado no momento da importação: `Instalado — Dossiê completo`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Importado em: 2026-09-10

## Propriedades do registro

- **Mod:** Goety Cataclysm
- **Arquivo JAR:** `goety_cataclysm-1.21.1-1.8.2.jar`
- **Versão 1.21.1:** `1.21.1-1.8.2`
- **Categoria:** Compat; Magia; RPG
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/goety-cataclysm
- **Função:** Bridge de Goety com L_Ender's Cataclysm, adaptando criaturas/servants e spells/abilities do Cataclysm ao ecossistema Goety.
- **Dependências:** Goety 3.1.4 + L_Ender's Cataclysm 3.33, ambos presentes fisicamente. Release do addon: 1.21.1-1.8.2 NeoForge.
- **Compatibilidade/Riscos:** Acoplamento simultâneo a Goety e Cataclysm; riscos de registry/API drift, duplicate damage/attack hooks, servant ownership/lifecycle, config drift e client/server mismatch. Source oficial público acessível é 1.20, não authority da build 1.21.1.
- **Sobreposição:** Complementa Goety e Cataclysm. Goety mantém Soul Energy/servant base; Cataclysm mantém entidades/conteúdo-base; o addon mantém apenas a adaptação entre ambos.
- **Observações:** Release física `goety_cataclysm-1.21.1-1.8.2.jar`. Source 1.21.1 exato não foi localizado; detalhes internos permanecem fail-closed. Não contabilizar conteúdo do addon nos 110 Focuses base do Goety.
- **Procedência:** modlist.txt física atual de 09/09/2026 + release oficial Goety Cataclysm 1.21.1-1.8.2 + documentação/changelogs oficiais da linha; source 1.20 usado apenas como evidência de drift, não de internals 1.21.1.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — Goety Cataclysm 1.21.1-1.8.2 release-pinned; Goety↔Cataclysm authority, servants/spells bridge, lifecycle/multiplayer, version drift, riscos e testes catalogados.
- **Data da última decisão:** 2026-08-26

## Dossiê operacional — padrão Alex's Mobs

> 🔎 **ESCOPO CANÔNICO.** Runtime físico: `goety_cataclysm-1.21.1-1.8.2.jar`, mod id `goety_cataclysm`, versão `1.21.1-1.8.2`. É uma bridge Client & Server entre **Goety 3.1.4** e **L_Ender's Cataclysm 3.33**, ambos presentes fisicamente. A release 1.21.1-1.8.2 é exata; o source oficial público acessível permanece na linha 1.20, portanto internals 1.21.1 são fail-closed.

## 1. Papel e authority
Goety Cataclysm integra conteúdo e habilidades do Cataclysm ao modelo mágico/servant do Goety. Não substitui nenhum provider-base:
- Goety continua authority de Soul Energy, Focus casting e servant lifecycle base;
- L_Ender's Cataclysm continua authority dos mobs/bosses/itens e comportamento-base que fornece;
- Goety Cataclysm é authority apenas das adaptações, servants, focuses/spells e compatibilidades que registra.

## 2. Dependências físicas
Snapshot atual:
- `goety-3.1.4.jar`;
- `L_Ender's Cataclysm 1.21.1-3.33.jar`;
- `goety_cataclysm-1.21.1-1.8.2.jar`.
Essa tríade deve ser testada como unidade antes de qualquer upgrade unilateral.

## 3. Escopo funcional confirmado
A descrição oficial define o mod como compatibilidade entre Goety e L_Ender's Cataclysm e declara acesso a spells/abilities inspirados nos monstros poderosos do Cataclysm. O histórico oficial da linha também mostra servants de mobs Cataclysm, Focuses e adaptações de ataques/efeitos.
Detalhes de registry, classes e lista completa da build 1.21.1-1.8.2 não são enumerados sem source/changelog exato auditável.

## 4. Servants e ownership
Servants adaptados do Cataclysm devem manter owner e lifecycle pelo sistema Goety. Boss AI/original entity semantics não devem ser copiadas integralmente quando o addon fornece uma variante servant própria. Death/despawn, owner disconnect e dimension transfer são boundaries obrigatórios.

## 5. Spells / Focuses
Spells ou Focuses baseados em ataques Cataclysm devem liquidar custo/cooldown/efeito pelo Goety/addon, não por uma segunda execução do ataque original. Animação/projétil visual não pode ser usada como segunda fonte de dano.

## 6. Configuração
O histórico oficial da linha documenta configurações de atributos para Cataclysm Servants e toggles de efeitos em versões anteriores. Sem artefato/config 1.21.1-1.8.2 inspecionado, nomes/defaults atuais não são promovidos a contrato. Qualquer tuning do pack deve partir dos arquivos realmente gerados pelo runtime.

## 7. Effects e compatibilidade cruzada
A linha pública já tratou interações como Wetness/Shock, shield disabling e compatibilidade de ataques/servants. Isso demonstra que effects e combat hooks são superfície de integração real. No stack atual, Epic Fight, shields/equipment mods e outros combat systems tornam regressão nessa área plausível, mas nenhuma integração é presumida sem teste concreto.

## 8. Client / server
A distribuição é Client & Server. Servant state, summons, damage, spell effects e ownership devem ser server-authoritative. Cliente fica com render, particles, sound e apresentação dos Focuses/entidades.

## 9. Lifecycle
Validar boot, registry sync, criação/remoção de servants, owner login/logout, death/respawn, dimension change, chunk unload/reload, server restart e datapack/resource reload. Addon não deve deixar entidade servant órfã nem referência stale ao boss/entity original.

## 10. Multiplayer
Dois jogadores podem invocar servants/usar Focuses simultaneamente. Owner attribution, targeting e drops precisam permanecer isolados. Ataques em área não devem contar duas vezes por handler Goety + handler Cataclysm, e reconnect não deve duplicar servant state.

## 11. Version drift do source
O repositório oficial `Polarice3/Goety_Cataclysm` acessível declara atualmente Minecraft 1.20.1 e versão 1.20-1.9.1; não foi localizado branch 1.21.1. Portanto ele não é usado como authority binária para 1.21.1-1.8.2. A distribuição física e a página oficial da release prevalecem.

## 12. Riscos técnicos
- update unilateral de Goety ou Cataclysm quebrar bridge;
- registry/API drift entre três JARs;
- servant com owner/lifecycle divergente;
- attack/spell effect processado pelos dois providers;
- dano/projétil duplicado;
- config antiga aplicada à 1.21.1 sem validação;
- client/server mismatch;
- AI/navigation de servant falhar em água/terreno/chunk boundary;
- boss loot/flags escaparem para variante servant;
- source 1.20 ser confundido com internals 1.21.1.

## 13. Matriz de testes obrigatória
- [ ] Dedicated server boot com Goety 3.1.4 + Cataclysm 3.33 + addon 1.8.2.
- [ ] Registry sync cliente/servidor sem missing mappings.
- [ ] Invocar cada categoria de servant usada pelo pack e validar owner.
- [ ] Servant segue/teleporta/navega e volta ao owner sem entidade órfã.
- [ ] Death/despawn/reconnect/dimension transfer preservam lifecycle esperado.
- [ ] Focus/spell baseado em Cataclysm cobra custo e aplica efeito uma vez.
- [ ] Ataques em área/projéteis não duplicam dano.
- [ ] Shield/effect interactions não causam crash ou double hook.
- [ ] Server restart preserva apenas state intencional.
- [ ] Upgrade de qualquer um dos três mods é bloqueado até smoke test conjunto.

## 14. Evidências e limites
- **Modlist física:** confirma addon 1.21.1-1.8.2, Goety 3.1.4 e L_Ender's Cataclysm 3.33.
- **Distribuição oficial:** release 1.21.1-1.8.2 NeoForge, Client & Server, publicada 27/07/2026.
- **Descrição/changelogs oficiais da linha:** confirmam a natureza de servants/spells/abilities e superfícies de combat/config.
- **Limite:** source público encontrado é da linha 1.20, não da build física 1.21.1; registries/classes/lista completa permanecem fail-closed.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
