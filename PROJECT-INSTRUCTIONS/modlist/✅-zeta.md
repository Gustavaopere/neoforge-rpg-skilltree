# Zeta

> **Autoridade física atual — 28/09/2026.** Ordem física **#587**: JAR `Zeta-1.1-40.jar`, mod id `zeta`, runtime `1.1-40`, SHA-1 `72f6efe421cfc0547c774ae8241d727b7d996754`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** Zeta
- **Arquivo JAR:** `Zeta-1.1-40.jar`
- **Versão 1.21.1:** 1.1-40
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca
- **Função:** Biblioteca/core do ecossistema Vazkii usada principalmente pelo Quark em versões modernas.
- **Dependências:** Consumer físico confirmado: Quark 4.1-484. Quark requer Zeta em 1.20.1+ segundo o projeto oficial.
- **Sobreposição:** Infraestrutura específica do ecossistema Zeta/Quark; não é substituível automaticamente por outras libraries e não deve receber attribution de gameplay do consumer.
- **Compatibilidade/Riscos:** Dependência real de Quark. Riscos: consumer/API drift, linkage/classloading, mixin/config interaction e substituição indevida por outra library. Pack físico usa Quark 4.1-483; upstream já publicou Quark 4.1-484 em 10/09/2026, sem mudança correspondente de Zeta. Updates devem validar Zeta↔Quark em conjunto.
- **Observações:** Mod id `zeta`, runtime `1.1-40`. Zeta é load-bearing library/sucessora do AutoRegLib para mods modulares; não contém gameplay próprio relevante.
- **Procedência:** modlist(1).txt física atual de 27/09/2026 + CurseForge oficial Zeta 1.1-40 + Quark 4.1-484 físico. Zeta 1.1-40 permanece o runtime instalado; snapshots 11/09–13/09 com Quark 4.1-483 foram preservados como histórico. Nenhum runtime linkage QA foi executado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/zeta
- **Atualização/Status:** RECONCILIADO EM 28/09/2026 — Zeta 1.1-40 permanece exatamente instalada; consumer físico atual Quark reconciliado de 4.1-483 para 4.1-484. Library authority, version drift, classloading e lifecycle preservados.
- **Histórico da decisão:** 
- **Data da última decisão:** 

# Dossiê operacional — padrão Alex's Mobs

> 🧱 **ESCOPO CANÔNICO.** Runtime físico: `Zeta-1.1-40.jar`, mod id `zeta`, versão `1.1-40`. Zeta é uma **load-bearing library para mods modulares**, sucessora conceitual do sistema modular/AutoRegLib usado pelo ecossistema Quark. Não contém conteúdo de gameplay próprio relevante.
## 1. Função confirmada
O projeto oficial descreve Zeta como uma biblioteca abrangente para mods modulares, construída como retooling do sistema de módulos original do Quark e sucessora do AutoRegLib.
A própria página oficial afirma que não há motivo para instalar Zeta isoladamente se nenhum outro mod exigir a biblioteca.
## 2. Consumer físico atual
A modlist física atual confirma `Quark-4.1-484.jar`, mod id `quark`, runtime `4.1-484`.
A página oficial do Quark declara explicitamente que **Quark requer Zeta em 1.20.1+**. Portanto Zeta não é biblioteca órfã no snapshot atual.
## 3. Authority e ownership
- **Zeta:** modular framework/library primitives, registration/config/infrastructure internas que expõe aos consumers.
- **Quark 4.1-484:** conteúdo, módulos, regras e gameplay do Quark.
Não criar quests/perks “de Zeta”, não usar a presença da library como proxy de um módulo Quark específico e não assumir que outra library genérica possa substituí-la.
## 4. Versão atual
`Zeta-1.1-40.jar` é a Release oficial atual para Minecraft 1.21.1 / NeoForge, publicada em 24/04/2026. O release upstream 1.1-40 é descrito como limpeza/manutenção da library.
Quark 4.1-484 é o consumer físico atual e continua declarando Zeta como requisito para a linha 1.20.1+.
## 5. Client/server e classloading
CurseForge classifica Zeta como Client & Server. Como load-bearing library, failures relevantes são linkage/classloading/config/mixin issues em consumers, não falhas de conteúdo próprio.
Dedicated server deve carregar Zeta + Quark sem `NoSuchMethodError`, `ClassNotFoundException` ou leakage de classes client-only provocado por integração externa.
## 6. Lifecycle
Validar boot client/server, registration, config loading, reloads suportados pelo consumer, world join, reconnect e update conjunto Zeta↔Quark.
A library não deve ser atualizada/removida isoladamente sem validar a faixa/compatibilidade do consumer físico atual.
## 7. Boundary para mods próprios
- Provider-native first: qualquer integração com Quark deve preferir API/evento/state do Quark/Zeta quando documentado.
- Não interceptar internals da Zeta sem contrato comprovado.
- Não duplicar module state/config em mod próprio.
- Se uma integração depender de internals sem API estável, usar fail-closed em vez de inferir comportamento.
## 8. Riscos
1. **Consumer/API drift:** Quark e Zeta avançam em ritmos diferentes.
2. **Library substitution:** tratar outra library como drop-in replacement.
3. **Classloading/linkage:** consumer compilado contra surface diferente.
4. **Mixin/config interaction:** alteração de infraestrutura afetando múltiplos módulos Quark.
5. **False gameplay attribution:** evento de Quark creditado à library Zeta.
## 9. Matriz de testes
- [ ] Dedicated server inicia com Zeta 1.1-40 + Quark 4.1-484.
- [ ] Cliente conecta sem protocol/linkage errors.
- [ ] Quark registra/carrega módulos e configs normalmente.
- [ ] Nenhum `NoSuchMethodError`/`ClassNotFoundException` ligado à Zeta.
- [ ] Reload/reconnect não duplica module/config state.
- [ ] Update futuro de Quark é smoke-tested contra a Zeta instalada antes de merge no pack.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 10. Evidências
- Modlist física atual: `Zeta-1.1-40.jar`, mod id `zeta`; `Quark-4.1-484.jar`, mod id `quark`.
- CurseForge oficial Zeta: load-bearing library, sucessora do AutoRegLib/sistema modular, sem conteúdo próprio; Release 1.1-40 NeoForge 1.21.1.
- CurseForge oficial Quark: Quark requer Zeta desde 1.20.1; build física atual 4.1-484.
- GitHub oficial Zeta: release `release-1.1-40+1.21.1`.
## 11. Limitação
A superfície interna da API 1.1-40 não foi inventariada/decompilada nesta etapa. Integrações próprias devem verificar contracts/classes reais antes de depender de internals da library.
## 12. Revalidação física — 11/09/2026
A modlist física mantém exatamente `Zeta-1.1-40.jar`, mod id `zeta`, versão `1.1-40`. CurseForge continua apontando 1.1-40 como latest release NeoForge 1.21.1 de 24/04/2026; o GitHub oficial identifica a tag `release-1.1-40+1.21.1` como manutenção/cleanup da library.
O consumer físico confirmado continua sendo `Quark-4.1-483.jar`. A página oficial do Quark mantém Zeta como requisito para 1.20.1+ e já publicou **Quark 4.1-484 em 10/09/2026**. Essa atualização pertence ao consumer, não à Zeta; portanto não altera a versão física desta página e a página do Quark não foi modificada neste lote. A decisão **Sem decisão** e o estado **Instalado — Dossiê completo** foram preservados. Nenhum dedicated-server boot, linkage, module/config loading ou update-combination test foi executado nesta recatalogação.
## 13. Revalidação física e upstream — 13/09/2026
O runtime físico permanece `Zeta-1.1-40.jar`, versão `1.1-40`, e continua a latest release NeoForge 1.21.1 localizada. Quark `4.1-483` permanece o consumer físico confirmado nesta modlist; a referência já registrada a Quark `4.1-484` é consumer-side e não constitui update de Zeta. O boundary permanece Zeta = load-bearing modular library, Quark = gameplay/content. Nenhum dedicated-server boot, linkage, module/config loading ou update-combination test foi executado nesta revalidação.
## Reconciliação física — 28/09/2026
A autoridade física atual mantém `Zeta-1.1-40.jar`, mod id `zeta`, runtime `1.1-40`, e confirma `Quark-4.1-484.jar`, mod id `quark`, runtime `4.1-484` como consumer top-level atual. As referências de 11/09–13/09 a Quark `4.1-483` permanecem preservadas como snapshots históricos. O boundary operacional permanece Zeta = biblioteca modular/load-bearing; Quark = gameplay/conteúdo. Nenhum dedicated-server boot, linkage, module/config loading ou update-combination test foi executado nesta reconciliação.
