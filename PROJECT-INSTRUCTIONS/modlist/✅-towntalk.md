# TownTalk

> **Autoridade física atual — 27/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física **#546**: JAR `towntalk-1.2.0.jar`, mod id `towntalk`, runtime `1.2.0`, SHA-1 `6866d898938bdde1a17fadee16983c0c6071eac6`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** TownTalk
- **Arquivo JAR:** `towntalk-1.2.0.jar`
- **Versão 1.21.1:** 1.2.0
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Visual, QoL
- **Função:** Addon de ambientação sonora para MineColonies que adiciona mais voice lines aos colonists; não adiciona AI conversacional, quests ou novas regras de colony confirmadas.
- **Dependências:** MineColonies. O pack instala MineColonies 1.1.1381 para 1.21.1 em snapshot; validar compatibilidade por possível drift entre a snapshot atual e TownTalk 1.2.0 de 2024.
- **Sobreposição:** Ambientação vocal de colonists. Pode coexistir com outros addons MineColonies; não substitui tweaks, compatibilidade, jobs ou progressão.
- **Compatibilidade/Riscos:** Principal risco é drift da snapshot MineColonies, além de missing sound resources, conflitos de resource pack e excesso de áudio em colônias densas. Gatilhos/roster individual de vozes não foram publicados de forma suficiente para catalogação exata.
- **Observações:** mod id `towntalk`; runtime 1.2.0. Release NeoForge 1.21/1.21.1 de 22/08/2024; changelog oficial: first 1.21 release. Removida numeração de lote histórica incorreta.
- **Procedência:** modlist.txt física atual consultada em 13/09/2026 + CurseForge oficial TownTalk 1.2.0 revalidado em 13/09/2026 + MineColonies físico 1.1.1381 snapshot. Dossiê de 11/09 preservado; roster/gatilhos individuais de vozes permanecem não publicados em detalhe suficiente e runtime de áudio não foi testado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/towntalk/files/5653504
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 13/09/2026 — TownTalk 1.2.0 permanece exatamente instalado e continua a release NeoForge 1.21.1 aplicável localizada; voice-line scope, MineColonies ownership, resource lifecycle, snapshot drift, riscos e testes preservados.
- **Histórico da decisão:** 
- **Data da última decisão:** 2026-09-09

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `towntalk-1.2.0.jar`, mod id `towntalk`, versão `1.2.0`. O upstream define TownTalk de forma estreita como **“MineColonies Addon for more Voice Lines”**. Esta ficha mantém esse limite: ambientação sonora de colonists, sem inventar diálogo procedural, IA conversacional, quests ou mecânicas sociais não documentadas.
## 1. Identidade e versão
- **Mod:** TownTalk.
- **JAR:** `towntalk-1.2.0.jar`.
- **Mod id:** `towntalk`.
- **Versão:** `1.2.0`.
- **Minecraft/loader:** NeoForge 1.21.1.
- **Publicação:** release oficial de 22/08/2024; changelog da build: primeira release 1.21.
- **Papel:** ampliar as voice lines do ecossistema MineColonies.
## 2. Authority e ownership
MineColonies continua authority de colonists, jobs, colony state, AI, necessidades e eventos. TownTalk é uma **camada de ambientação/voz**.
Uma fala reproduzida não deve ser tratada como nova regra de gameplay. Ausência, repetição ou falha de áudio também não prova falha da AI do colonist.
## 3. Conteúdo confirmado
O que o upstream sustenta para a 1.2.0:
- addon de MineColonies;
- adiciona mais voice lines;
- build NeoForge publicada para 1.21/1.21.1.
O arquivo tem porte significativamente maior que muitos addons puramente lógicos, consistente com conteúdo de áudio, mas tamanho do JAR **não é usado como contagem de linhas/vozes**.
Não há catálogo oficial localizado nesta auditoria que permita enumerar vozes, profissões, idiomas ou gatilhos individuais.
## 4. Integração com MineColonies
O pack possui MineColonies `1.1.1381-1.21.1-snapshot`. TownTalk depende funcionalmente do contexto de colonists; portanto a maior superfície de compatibilidade é **drift da snapshot do MineColonies** em relação à antiga release TownTalk 1.2.0.
Validar que colonists atuais continuam produzindo as vozes esperadas sem crash, missing sound event ou spam de log.
## 5. Client / server
A reprodução de som é percebida no cliente, mas a ficha não afirma que TownTalk seja estritamente client-only: ele é distribuído como mod NeoForge e opera sobre entidades MineColonies.
Operacionalmente:
- dedicated server deve carregar a combinação sem erro;
- clients devem resolver corretamente os assets/sound events;
- distância/volume/categoria sonora devem respeitar o comportamento efetivo do runtime.
## 6. Resource lifecycle
Voice assets são superfície de resources. Validar:
- client boot;
- resource reload;
- troca de resource pack;
- reconnect;
- colonist spawn/despawn e chunk reload;
- múltiplos colonists falando próximos sem áudio corrompido ou volume anormal.
## 7. Integrações e sobreposição no pack
- **MineColonies 1.1.1381 snapshot:** provider principal dos colonists.
- Outros mods de diálogo/NPC podem adicionar interfaces ou conversas próprias; TownTalk não os substitui porque seu papel confirmado é voice-line ambience.
- Resource packs podem substituir assets sonoros, mas não assumem ownership dos gatilhos do addon.
## 8. Riscos técnicos
1. **API/event drift:** TownTalk 1.2.0 é de 2024 e MineColonies atual é snapshot 2026.
2. **Missing sound resources:** ids/paths alterados podem gerar silêncio ou warnings.
3. **Audio spam:** muitos colonists em área densa podem produzir excesso de vozes.
4. **Client resource conflicts:** resource packs podem substituir ou omitir sons.
5. **Misclassification:** não atribuir a TownTalk AI conversacional ou diálogo contextual específico sem evidência.
## 9. Matriz de testes
- [ ] Dedicated server inicia com TownTalk 1.2.0 + MineColonies snapshot atual.
- [ ] Cliente entra sem missing registry/sound errors impeditivos.
- [ ] Colonists reproduzem voice lines audíveis no runtime atual.
- [ ] Resource reload não quebra os sons.
- [ ] Chunk unload/reload de colônia não elimina permanentemente a ambientação.
- [ ] Densidade alta de colonists não gera spam de áudio/log crítico.
- [ ] Resource packs do pack não produzem missing sound references.
- [ ] Remover TownTalk em cópia de teste remove apenas ambientação, sem afetar colony state.
Nenhum teste foi marcado como aprovado nesta auditoria.
## 10. Evidências
- Modlist física canônica 08/09/2026: JAR/mod id/versão e MineColonies atual.
- CurseForge oficial TownTalk: “Minecolonies Addon for more Voice Lines”; release `towntalk-1.2.0.jar` para NeoForge 1.21/1.21.1; changelog “first 1.21 release”.
- Detalhes não publicados sobre gatilhos/roster de vozes permanecem desconhecidos e não foram preenchidos por inferência.
## 11. Revalidação física — 11/09/2026
O snapshot físico continua contendo exatamente `towntalk-1.2.0.jar`, mod id `towntalk`, versão `1.2.0`, com MineColonies `1.1.1381-1.21.1-snapshot` como provider dos colonists. A release pública 1.2.0 permanece a build NeoForge 1.21/1.21.1 pertinente.
O risco principal continua sendo drift entre a release TownTalk de 2024 e o MineColonies snapshot atual. Nenhum gatilho individual de voz, resource reload, missing sound event ou stress de colônia densa foi testado nesta recatalogação.
## 12. Revalidação física e upstream — 13/09/2026
O runtime físico permanece `towntalk-1.2.0.jar`, versão `1.2.0`; a release NeoForge 1.21/1.21.1 localizada continua 1.2.0. MineColonies `1.1.1381-1.21.1-snapshot` permanece o provider dos colonists, mantendo como principal risco o drift entre a antiga release TownTalk e a snapshot atual. Nenhum resource reload, gatilho individual de voz, missing sound event ou stress de colônia densa foi executado nesta revalidação.
