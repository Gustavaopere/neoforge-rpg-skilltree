# Zeta

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db817db2f2cce5c7ac7924
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `Zeta-1.1-40.jar`, mod id `zeta`, runtime `1.1-40`; consumer `Quark-4.1-483.jar` confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026” e registra Quark 4.1-484 apenas como atualização upstream disponível. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**, onde Zeta 1.1-40 e Quark 4.1-483 estão presentes. O corpo-fonte abaixo é preservado.

## Propriedades do banco

- **Mod:** Zeta
- **Arquivo JAR:** `Zeta-1.1-40.jar`
- **Versão 1.21.1:** 1.1-40
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca
- **Função:** Biblioteca/core do ecossistema Vazkii usada principalmente pelo Quark em versões modernas.
- **Dependências:** Consumer físico confirmado: Quark 4.1-483. Quark requer Zeta em 1.20.1+ segundo o projeto oficial.
- **Sobreposição:** Infraestrutura específica do ecossistema Zeta/Quark; não é substituível automaticamente por outras libraries e não deve receber attribution de gameplay do consumer.
- **Compatibilidade/Riscos:** Dependência real de Quark. Riscos: consumer/API drift, linkage/classloading, mixin/config interaction e substituição indevida por outra library. Pack físico usa Quark 4.1-483; upstream já publicou Quark 4.1-484 em 10/09/2026, sem mudança correspondente de Zeta. Updates devem validar Zeta↔Quark em conjunto.
- **Observações:** Mod id `zeta`, runtime `1.1-40`. Zeta é load-bearing library/sucessora do AutoRegLib para mods modulares; não contém gameplay próprio relevante.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge/GitHub oficiais Zeta 1.1-40, latest release NeoForge 1.21.1 de 24/04/2026 + Quark 4.1-483 físico. CurseForge registra Quark 4.1-484 em 10/09/2026 como atualização disponível do consumer; isso não constitui update de Zeta e a página do Quark não foi alterada neste lote. Estado `Instalado — Dossiê completo` e decisão `Sem decisão` preservados; runtime linkage QA não executado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/zeta
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — Zeta 1.1-40 permanece exatamente instalada e continua a latest release NeoForge 1.21.1; consumer Quark, library authority, version drift, classloading e lifecycle preservados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `Zeta-1.1-40.jar`, mod id `zeta`, versão `1.1-40`. Zeta é uma **load-bearing library para mods modulares**, sucessora conceitual do sistema modular/AutoRegLib usado pelo ecossistema Quark. Não contém conteúdo de gameplay próprio relevante.

## 1. Função confirmada
O projeto oficial descreve Zeta como uma biblioteca abrangente para mods modulares, construída como retooling do sistema de módulos original do Quark e sucessora do AutoRegLib.

A própria página oficial afirma que não há motivo para instalar Zeta isoladamente se nenhum outro mod exigir a biblioteca.

## 2. Consumer físico atual
A modlist física confirma `Quark-4.1-483.jar`, mod id `quark`, runtime `4.1-483`.

A página oficial do Quark declara explicitamente que **Quark requer Zeta em 1.20.1+**. Portanto Zeta não é biblioteca órfã no snapshot atual.

## 3. Authority e ownership
- **Zeta:** modular framework/library primitives, registration/config/infrastructure internas que expõe aos consumers.
- **Quark 4.1-483:** conteúdo, módulos, regras e gameplay do Quark.

Não criar quests/perks “de Zeta”, não usar a presença da library como proxy de um módulo Quark específico e não assumir que outra library genérica possa substituí-la.

## 4. Versão atual
`Zeta-1.1-40.jar` é a Release oficial atual para Minecraft 1.21.1 / NeoForge, publicada em 24/04/2026. O release upstream 1.1-40 é descrito como limpeza/manutenção da library.

Quark 4.1-483 é mais recente, publicado em 08/09/2026, mas continua declarando Zeta como requisito para a linha 1.20.1+.

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
- [ ] Dedicated server inicia com Zeta 1.1-40 + Quark 4.1-483.
- [ ] Cliente conecta sem protocol/linkage errors.
- [ ] Quark registra/carrega módulos e configs normalmente.
- [ ] Nenhum `NoSuchMethodError`/`ClassNotFoundException` ligado à Zeta.
- [ ] Reload/reconnect não duplica module/config state.
- [ ] Update futuro de Quark é smoke-tested contra a Zeta instalada antes de merge no pack.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 10. Evidências
- Modlist física atual: `Zeta-1.1-40.jar`, mod id `zeta`; `Quark-4.1-483.jar`, mod id `quark`.
- CurseForge oficial Zeta: load-bearing library, sucessora do AutoRegLib/sistema modular, sem conteúdo próprio; Release 1.1-40 NeoForge 1.21.1.
- CurseForge oficial Quark: Quark requer Zeta desde 1.20.1; build física atual 4.1-483.
- GitHub oficial Zeta: release `release-1.1-40+1.21.1`.

## 11. Limitação
A superfície interna da API 1.1-40 não foi inventariada/decompilada nesta etapa. Integrações próprias devem verificar contracts/classes reais antes de depender de internals da library.

## 12. Revalidação física — 11/09/2026
A modlist física mantém exatamente `Zeta-1.1-40.jar`, mod id `zeta`, versão `1.1-40`. CurseForge continua apontando 1.1-40 como latest release NeoForge 1.21.1 de 24/04/2026; o GitHub oficial identifica a tag `release-1.1-40+1.21.1` como manutenção/cleanup da library.

O consumer físico confirmado continua sendo `Quark-4.1-483.jar`. A página oficial do Quark mantém Zeta como requisito para 1.20.1+ e já publicou **Quark 4.1-484 em 10/09/2026**. Essa atualização pertence ao consumer, não à Zeta; portanto não altera a versão física desta página e a página do Quark não foi modificada neste lote. A decisão **Sem decisão** e o estado **Instalado — Dossiê completo** foram preservados. Nenhum dedicated-server boot, linkage, module/config loading ou update-combination test foi executado nesta recatalogação.
