# Better ModList

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d569db9f0db8171bd54e590916e68e5
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Better ModList
- **Arquivo JAR:** `better_modlist-21.1.1.jar`
- **Versão 1.21.1:** 21.1.1
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** QoL
- **Função:** Melhora a tela de mods do NeoForge com visual inspirado em Mod Menu, ocultação/organização de entradas, badges e acesso mais claro a informações/configuração dos mods.
- **Dependências:** NeoForge 1.21.1. A linha 21.1.1 é o port NeoForge do conceito Mod Menu; não foi confirmada hard dependency externa obrigatória para esta build física.
- **Sobreposição:** Pode sobrepor apenas a apresentação/navegação da tela de mods e outras UI mods; não substitui bibliotecas/config backends dos mods listados.
- **Compatibilidade/Riscos:** QoL de interface. Riscos principais: mod ocultado dificultar diagnóstico, config-screen provider ausente/incompatível, UI conflitante com outras alterações de Mods screen e confusão entre display name e mod id. Não altera gameplay authority.
- **Observações:** JAR físico `better_modlist-21.1.1.jar`, mod id físico `mod_menu`, nome runtime Better ModList, versão 21.1.1. É um port não oficial de Mod Menu para NeoForge; não confundir o mod id `mod_menu` com o loader Fabric.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial Better ModList 21.1.1 para NeoForge 1.21.1 e fontes já auditadas. Reconciliação final: JAR/runtime permanecem exatamente `better_modlist-21.1.1.jar` / `21.1.1`; mod id `mod_menu` continua documentado sem confusão com o Mod Menu original/Fabric.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/better-modlist-neoforge
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — reconciliação final física #70: `better_modlist-21.1.1.jar` / `21.1.1` conferidos contra a modlist atual; mod id físico `mod_menu`, UI authority, hiding/filtering, badges e config-screen boundary preservados.
- **Histórico da decisão:** Sem decisão formal. Em 09/09/2026, a auditoria confirmou `better_modlist-21.1.1.jar`, mod id físico `mod_menu`, runtime Better ModList 21.1.1 e o papel exclusivamente UI/QoL do port NeoForge. A presença física não foi convertida automaticamente em decisão de manter/remover.
- **Data da última decisão:**

> 🧭 **ESCOPO CANÔNICO.** Runtime físico: `better_modlist-21.1.1.jar`, mod id `mod_menu`, nome runtime `Better ModList`, versão `21.1.1`. É uma camada de **UI/QoL para a lista de mods do NeoForge**; não adiciona conteúdo, progressão ou state de gameplay.

## 1. Função confirmada
O projeto é um port não oficial da experiência de Mod Menu para NeoForge. A linha 21.1.1 melhora a apresentação da Mods screen e oferece recursos como ocultar/organizar entradas e exibir badges/informações sobre o papel dos mods.

## 2. Identidade e mod id
O metadata físico usa mod id `mod_menu`. Isso não significa que a instância esteja rodando Fabric nem que o mod seja o Mod Menu original. Integrações e diagnósticos devem distinguir filename, runtime name e mod id.

## 3. Authority
Better ModList é authority apenas da própria apresentação/navegação da lista. Nome, versão, config e metadata exibidos continuam pertencendo aos mods/providers correspondentes.
Não usar presença/ausência visual de uma entrada como prova de que o mod foi carregado ou removido: mods podem ser ocultados pela própria UI/config.

## 4. Config screens
A UI pode facilitar acesso à configuração de outros mods, mas não substitui o backend/config API de cada consumer. Salvar uma opção deve seguir o mecanismo do mod-alvo; Better ModList não se torna owner do valor.
A exigência de Forge Config Screen documentada para linhas antigas não foi promovida como hard dependency desta build 21.1.1 sem metadata física correspondente.

## 5. Client/server boundary
A função é predominantemente client-facing. O projeto é distribuído para NeoForge e a ficha não atribui efeitos server-authoritative à UI. Dedicated server deve permanecer funcional sem qualquer codepath gráfico indevido provocado por integração externa.

## 6. Riscos
1. **Hidden-mod diagnostics:** ocultar library/mod dificulta troubleshooting sem significar ausência runtime.
2. **UI overlap:** outra alteração da Mods screen pode disputar layout/click handlers.
3. **Config routing:** botão de config aponta para provider incompatível ou inexistente.
4. **Identity confusion:** `mod_menu` interpretado como Fabric Mod Menu original.
5. **Metadata presentation:** label/badge desatualizado tratado como verdade sobre gameplay.

## 7. Boundary para quests/perks
Abrir a Mods screen, visualizar badges ou acessar configuração não é evento de gameplay e não concede Mastery/quest progress. A UI nunca deve ser usada como source of truth para presença funcional de um sistema.

## 8. Matriz de testes
- [ ] Cliente abre a Mods screen com Better ModList 21.1.1 sem crash.
- [ ] Busca/ocultação/badges não removem mods do runtime.
- [ ] Config buttons abrem apenas screens realmente disponíveis.
- [ ] Reinício preserva preferências de UI quando previsto.
- [ ] Resource reload e mudança de idioma não quebram layout.
- [ ] Dedicated server não carrega classes client-only por integração própria.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 9. Evidências e limitação
- Modlist física: `better_modlist-21.1.1.jar`, mod id `mod_menu`, runtime Better ModList 21.1.1.
- CurseForge oficial: Release 21.1.1 para NeoForge 1.21.1 e objetivo de melhorar a mod list como port de Mod Menu.
- Config interna e lista exata de badges/toggles da build não foram decompiladas; a ficha não inventa opções além das publicamente documentadas.
